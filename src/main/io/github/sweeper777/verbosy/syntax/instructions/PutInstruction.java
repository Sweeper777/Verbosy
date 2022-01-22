package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.CodeProvider;

public class PutInstruction extends ParameterPointerInstructionBase{

  public PutInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "PUT";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getPutInstruction(getParameter(), isPointer());
  }
}
