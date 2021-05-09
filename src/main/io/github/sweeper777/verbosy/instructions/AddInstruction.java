package io.github.sweeper777.verbosy.instructions;

public class AddInstruction extends ParameterPointerInstructionBase {

  public AddInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "ADD";
  }
}
