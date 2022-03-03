package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CodeProvider;

public class InputInstruction extends ParameterlessInstruction {


  public InputInstruction(int lineNo, int columnNo) {
    super(lineNo, columnNo);
  }

  @Override
  public String toString() {
    return "INPUT";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getInputInstruction();
  }
}
