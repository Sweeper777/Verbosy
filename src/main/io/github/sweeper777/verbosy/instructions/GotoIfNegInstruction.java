package io.github.sweeper777.verbosy.instructions;

public class GotoIfNegInstruction extends GotoInstructionBase {

  public GotoIfNegInstruction(int lineNo, int columnNo, String labelName) {
    super(lineNo, columnNo, labelName);
  }

  @Override
  public String getName() {
    return "GOTO_NEG";
  }
}
