package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;

public class AddInstruction extends ParameterPointerInstructionBase {

  public AddInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "ADD";
  }

  @Override
  public String getCode(CSharpCodeProvider provider) {
    return provider.getAddInstruction(getParameter(), isPointer());
  }
}
