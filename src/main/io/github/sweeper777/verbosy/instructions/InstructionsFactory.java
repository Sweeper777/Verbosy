package io.github.sweeper777.verbosy.instructions;


import io.github.sweeper777.verbosy.ErrorMessage;
import io.github.sweeper777.verbosy.VerbosyBaseListener;
import io.github.sweeper777.verbosy.VerbosyParser;
import io.github.sweeper777.verbosy.VerbosyParser.AddInstructionContext;
import io.github.sweeper777.verbosy.VerbosyParser.DecInstructionContext;
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
  public void exitInstruction(InstructionContext ctx) {

  }
}
