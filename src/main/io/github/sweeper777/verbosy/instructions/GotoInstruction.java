package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.CodeProvider;

public class GotoInstruction extends GotoInstructionBase {

  public GotoInstruction(int lineNo, int columnNo, String labelName) {
    super(lineNo, columnNo, labelName);
  }

  @Override
  public String getName() {
    return "GOTO";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getGoto(getLabelName());
  }
}
