package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;

public class HaltInstruction extends ParameterlessInstruction {

  public HaltInstruction(int lineNo, int columnNo) {
    super(lineNo, columnNo);
  }

  @Override
  public String toString() {
    return "HALT";
  }

  @Override
  public String getCode(CSharpCodeProvider provider) {
    return provider.getGoto("_end_");
  }
}
