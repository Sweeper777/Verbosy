package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;

public class SubInstruction extends ParameterPointerInstructionBase {

  public SubInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "SUB";
  }

  @Override
  public String getCode(CSharpCodeProvider provider) {
    return provider.getSubInstruction(getParameter(), isPointer());
  }
}
