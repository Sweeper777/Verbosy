package io.github.sweeper777.verbosy.syntax;


import io.github.sweeper777.verbosy.Diagnostic;
import io.github.sweeper777.verbosy.Diagnostic.Type;
import io.github.sweeper777.verbosy.VerbosyBaseListener;
import io.github.sweeper777.verbosy.VerbosyParser;
import io.github.sweeper777.verbosy.syntax.instructions.AddInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.DecInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoIf0Instruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoIfNegInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.HaltInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.IncInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.InputInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.LabelInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.OutputInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.ParameterPointerInstructionSupplier;
import io.github.sweeper777.verbosy.syntax.instructions.PutInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.SetInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.SubInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.TakeInstruction;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

import static io.github.sweeper777.verbosy.VerbosyParser.*;

public class InstructionsFactory extends VerbosyBaseListener {

  private final List<Instruction> parsedInstructions = new ArrayList<>();
  private final List<Diagnostic> diagnostics;
  private static final String INT_OUT_OF_RANGE_MSG = "Integer literal '%s' out of range";
  private static final String HEX_CHARACTER_LITERAL_OUT_OF_RANGE_MSG = "Hex character literal '%s' out of range";

  public InstructionsFactory(List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }

  public List<Instruction> getParsedInstructions() {
    return parsedInstructions;
  }

  @Override
  public void exitInputInstruction(InputInstructionContext ctx) {
    parsedInstructions.add(new InputInstruction(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
  }

  @Override
  public void exitOutputInstruction(OutputInstructionContext ctx) {
    parsedInstructions.add(new OutputInstruction(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
  }

  @Override
  public void exitHaltInstruction(HaltInstructionContext ctx) {
    parsedInstructions.add(new HaltInstruction(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
  }

  @Override
  public void exitSetInstruction(SetInstructionContext ctx) {
    var charParameter = ctx.character();
    var intParameter = ctx.signedInt();
    if (ctx.getText().length() <= 1) {
      return;
    }
    if (charParameter != null) {
      if (charParameter.hexEscape() == null) {
        parsedInstructions.add(new SetInstruction(
            ctx.getStart().getLine(),
            ctx.getStart().getCharPositionInLine(),
            new VerbosyValue(charParameter.getText().charAt(0), true)
        ));
      } else if (charParameter.hexEscape() != null) {
        try {
          String hex = charParameter.getText().substring(1);
          int c = Integer.parseInt(hex, 16);
          if (c > 65535) {
            throw new NumberFormatException();
          }
          parsedInstructions.add(new SetInstruction(
              ctx.getStart().getLine(),
              ctx.getStart().getCharPositionInLine(),
              new VerbosyValue(c, true)
          ));
        } catch (NumberFormatException ex) {
          diagnostics.add(new Diagnostic(
              charParameter.getStart().getLine(),
              charParameter.getStart().getCharPositionInLine(),
              String.format(HEX_CHARACTER_LITERAL_OUT_OF_RANGE_MSG, charParameter.getText()),
              Type.ERROR));
        }
      }
    } else if (intParameter != null) {
      try {
        int parameter = Integer.parseInt(intParameter.getText());
        parsedInstructions.add(new SetInstruction(
            ctx.getStart().getLine(),
            ctx.getStart().getCharPositionInLine(),
            new VerbosyValue(parameter, false)
        ));
      } catch (NumberFormatException ex) {
        diagnostics.add(new Diagnostic(
            intParameter.getStart().getLine(),
            intParameter.getStart().getCharPositionInLine(),
            String.format(INT_OUT_OF_RANGE_MSG, intParameter.getText()),
            Type.ERROR));
      }
    }
  }

  public void exitParameterPointerInstruction(
      ParserRuleContext parentContext,
      VerbosyParser.InstructionArgumentContext argContext,
      VerbosyParser.InstructionSuffixContext suffixContext,
      ParameterPointerInstructionSupplier<?> supplier) {
    if (argContext.unsignedInt().DIGIT().isEmpty()) {
      return;
    }
    try {
      int parameter = Integer.parseInt(argContext.getText());
      boolean pointer = suffixContext != null;
      parsedInstructions.add(supplier.createInstruction(
          parentContext.getStart().getLine(),
          parentContext.getStart().getCharPositionInLine(),
          parameter,
          pointer
      ));
    } catch (NumberFormatException ex) {
      diagnostics.add(new Diagnostic(
          argContext.getStart().getLine(),
          argContext.getStart().getCharPositionInLine(),
          String.format(INT_OUT_OF_RANGE_MSG, argContext.getText()),
          Type.ERROR));
    }
  }

  @Override
  public void exitAddInstruction(AddInstructionContext ctx) {
    exitParameterPointerInstruction(ctx, ctx.instructionArgument(), ctx.instructionSuffix(), AddInstruction::new);
  }

  @Override
  public void exitSubInstruction(SubInstructionContext ctx) {
    exitParameterPointerInstruction(ctx, ctx.instructionArgument(), ctx.instructionSuffix(), SubInstruction::new);
  }

  @Override
  public void exitIncInstruction(IncInstructionContext ctx) {
    exitParameterPointerInstruction(ctx, ctx.instructionArgument(), ctx.instructionSuffix(), IncInstruction::new);
  }

  @Override
  public void exitDecInstruction(DecInstructionContext ctx) {
    exitParameterPointerInstruction(ctx, ctx.instructionArgument(), ctx.instructionSuffix(), DecInstruction::new);
  }

  @Override
  public void exitPutInstruction(PutInstructionContext ctx) {
    exitParameterPointerInstruction(ctx, ctx.instructionArgument(), ctx.instructionSuffix(), PutInstruction::new);
  }

  @Override
  public void exitTakeInstruction(TakeInstructionContext ctx) {
    exitParameterPointerInstruction(ctx, ctx.instructionArgument(), ctx.instructionSuffix(), TakeInstruction::new);
  }

  @Override
  public void exitLabelInstruction(LabelInstructionContext ctx) {
    parsedInstructions.add(new LabelInstruction(
        ctx.getStart().getLine(),
        ctx.getStart().getCharPositionInLine(),
        ctx.label().labelName().getText()
    ));
  }

  @Override
  public void exitGotoInstruction(GotoInstructionContext ctx) {
    parsedInstructions.add(new GotoInstruction(
        ctx.getStart().getLine(),
        ctx.getStart().getCharPositionInLine(),
        ctx.labelName().getText()
    ));
  }

  @Override
  public void exitGotoIf_0Instruction(GotoIf_0InstructionContext ctx) {
    parsedInstructions.add(new GotoIf0Instruction(
        ctx.getStart().getLine(),
        ctx.getStart().getCharPositionInLine(),
        ctx.labelName().getText()
    ));
  }

  @Override
  public void exitGotoIf_negInstruction(GotoIf_negInstructionContext ctx) {
    parsedInstructions.add(new GotoIfNegInstruction(
        ctx.getStart().getLine(),
        ctx.getStart().getCharPositionInLine(),
        ctx.labelName().getText()
    ));
  }
}
