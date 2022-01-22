package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.CodeProvider;

public class OutputInstruction extends ParameterlessInstruction {

  public OutputInstruction(int lineNo, int columnNo) {
    super(lineNo, columnNo);
  }

  @Override
  public String toString() {
    return "OUTPUT";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getOutputInstruction();
  }
}
