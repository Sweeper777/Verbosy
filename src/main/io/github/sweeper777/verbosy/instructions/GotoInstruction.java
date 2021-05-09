package io.github.sweeper777.verbosy.instructions;

public class GotoInstruction extends GotoInstructionBase {

  public GotoInstruction(int lineNo, int columnNo, String labelName) {
    super(lineNo, columnNo, labelName);
  }

  @Override
  public String getName() {
    return "GOTO";
  }
}
