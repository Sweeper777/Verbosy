package io.github.sweeper777.verbosy.syntax;

import io.github.sweeper777.verbosy.codegen.CodeProvider;

public interface Instruction {
  int getLineNo();
  int getColumnNo();

  String getCode(CodeProvider provider);
}
