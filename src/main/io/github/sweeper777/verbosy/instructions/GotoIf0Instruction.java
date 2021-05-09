package io.github.sweeper777.verbosy.instructions;

public class GotoIf0Instruction extends GotoInstructionBase {

  public GotoIf0Instruction(int lineNo, int columnNo, String labelName) {
    super(lineNo, columnNo, labelName);
  }

  @Override
  public String getName() {
    return "GOTO0";
  }
}
