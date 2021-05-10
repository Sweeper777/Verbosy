package io.github.sweeper777.verbosy.instructions;


import io.github.sweeper777.verbosy.ErrorMessage;
import io.github.sweeper777.verbosy.VerbosyBaseListener;
import io.github.sweeper777.verbosy.VerbosyParser;
import io.github.sweeper777.verbosy.VerbosyParser.AddInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.DecInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.GotoIf_0InstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.GotoIf_negInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.GotoInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.IncInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.InputInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.LabelInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.OutputInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.PutInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.SetInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.SubInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.TakeInstructionContext;
import io.github.sweeper777.verbosy.VerbosyValue;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class InstructionsFactory extends VerbosyBaseListener {

  private final List<Instruction> parsedInstructions = new ArrayList<>();
  private final List<ErrorMessage> errorMessages;
  private static final String INT_OUT_OF_RANGE_MSG = "Integer literal out of range";

  public InstructionsFactory(List<ErrorMessage> errorMessages) {
    this.errorMessages = errorMessages;
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
          errorMessages.add(new ErrorMessage(
              charParameter.getStart().getLine(),
              charParameter.getStart().getCharPositionInLine(),
              "Hex character literal out of range"
          ));
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
        errorMessages.add(new ErrorMessage(
            intParameter.getStart().getLine(),
            intParameter.getStart().getCharPositionInLine(),
            INT_OUT_OF_RANGE_MSG
        ));
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
      errorMessages.add(new ErrorMessage(
          argContext.getStart().getLine(),
          argContext.getStart().getCharPositionInLine(),
          INT_OUT_OF_RANGE_MSG
      ));
    }
  }

  }
}
