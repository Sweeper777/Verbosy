package io.github.sweeper777.verbosy.instructions;

public class DecInstruction extends ParameterPointerInstructionBase {

  public DecInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "DEC";
  }
}
