package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CodeProvider;

public class TakeInstruction extends ParameterPointerInstructionBase {

  public TakeInstruction(int lineNo, int columnNo, int parameter, boolean pointer) {
    super(lineNo, columnNo, parameter, pointer);
  }

  @Override
  public String getName() {
    return "TAKE";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getTakeInstruction(getParameter(), isPointer());
  }
}
