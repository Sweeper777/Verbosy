package io.github.sweeper777.verbosy.instructions;

public class IncInstruction extends ParameterPointerInstructionBase {

  public IncInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "INC";
  }
}
