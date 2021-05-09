package io.github.sweeper777.verbosy.instructions;

public class TakeInstruction extends ParameterPointerInstructionBase {

  public TakeInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "TAKE";
  }
}
