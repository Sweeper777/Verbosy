package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;

public class DecInstruction extends ParameterPointerInstructionBase {

  public DecInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "DEC";
  }

  @Override
  public String getCode(CSharpCodeProvider provider) {
    return provider.getDecInstruction(getParameter(), isPointer());
  }
}
