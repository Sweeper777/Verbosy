package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.CodeProvider;

public interface Instruction {
  int getLineNo();
  int getColumnNo();

  String getCode(CodeProvider provider);
}
