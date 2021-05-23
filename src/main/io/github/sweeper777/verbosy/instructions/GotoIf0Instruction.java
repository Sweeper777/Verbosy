package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.compiler.CodeProvider;

public class GotoIf0Instruction extends GotoInstructionBase {

  public GotoIf0Instruction(int lineNo, int columnNo, String labelName) {
    super(lineNo, columnNo, labelName);
  }

  @Override
  public String getName() {
    return "GOTO0";
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getGotoIf0(getLabelName());
  }
}
