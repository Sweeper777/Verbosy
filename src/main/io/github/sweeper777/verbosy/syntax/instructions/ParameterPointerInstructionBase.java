package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.syntax.Instruction;

public abstract class ParameterPointerInstructionBase implements Instruction {
  private final int lineNo;
  private final int columnNo;
  private final int parameter;
  private final boolean pointer;

  public int getLineNo() {
    return lineNo;
  }

  public int getColumnNo() {
    return columnNo;
  }

  public int getParameter() {
    return parameter;
  }

  public boolean isPointer() {
    return pointer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ParameterPointerInstructionBase that = (ParameterPointerInstructionBase) o;

    if (parameter != that.parameter) {
      return false;
    }
    return pointer == that.pointer;
  }

  @Override
  public int hashCode() {
    int result = parameter;
    result = 31 * result + (pointer ? 1 : 0);
    return result;
  }

  public ParameterPointerInstructionBase(int lineNo, int columnNo,
      int parameter, boolean pointer) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
    this.parameter = parameter;
    this.pointer = pointer;
  }

  public abstract String getName();

  @Override
  public String toString() {
    return getName() + " " + getParameter() + (isPointer() ? " *" : "");
  }
}
