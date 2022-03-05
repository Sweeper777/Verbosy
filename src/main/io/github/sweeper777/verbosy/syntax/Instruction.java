package io.github.sweeper777.verbosy.syntax;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;

public interface Instruction {
  int getLineNo();
  int getColumnNo();

  String getCode(CSharpCodeProvider provider);
}
