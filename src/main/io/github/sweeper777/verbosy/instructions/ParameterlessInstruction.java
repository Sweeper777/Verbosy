package io.github.sweeper777.verbosy.instructions;

public abstract class ParameterlessInstruction implements Instruction {

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

  public ParameterlessInstruction(int lineNo, int columnNo) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
  }
}
