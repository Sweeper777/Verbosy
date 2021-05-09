package io.github.sweeper777.verbosy.instructions;

public class SubInstruction extends ParameterPointerInstructionBase {

  public SubInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "SUB";
  }
}
