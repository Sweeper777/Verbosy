package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.compiler.CodeProvider;

public class OutputInstruction implements Instruction {

  private final int lineNo;
  private final int columnNo;

  public int getLineNo() {
    return lineNo;
  }

  public int getColumnNo() {
    return columnNo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public OutputInstruction(int lineNo, int columnNo) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
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
