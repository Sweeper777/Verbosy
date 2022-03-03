package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CodeProvider;

public class GotoIfNegInstruction extends GotoInstructionBase {

  public GotoIfNegInstruction(int lineNo, int columnNo, String labelName) {
    super(lineNo, columnNo, labelName);
  }

  @Override
  public String getName() {
    return "GOTO_NEG";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getGotoIfNeg(getLabelName());
  }
}
