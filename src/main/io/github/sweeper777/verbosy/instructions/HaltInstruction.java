package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.compiler.CodeProvider;

public class HaltInstruction extends ParameterlessInstruction {

  public HaltInstruction(int lineNo, int columnNo) {
    super(lineNo, columnNo);
  }

  @Override
  public String toString() {
    return "HALT";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getGoto("_end_");
  }
}
