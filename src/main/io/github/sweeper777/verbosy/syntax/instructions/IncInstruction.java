package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;

public class IncInstruction extends ParameterPointerInstructionBase {

  public IncInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "INC";
  }

  @Override
  public String getCode(CSharpCodeProvider provider) {
    return provider.getIncInstruction(getParameter(), isPointer());
  }
}
