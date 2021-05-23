package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.compiler.CodeProvider;

public class LabelInstruction implements Instruction {
  private final int lineNo;
  private final int columnNo;
  private final String labelName;

  public int getLineNo() {
    return lineNo;
  }

  public int getColumnNo() {
    return columnNo;
  }

  public String getLabelName() {
    return labelName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LabelInstruction that = (LabelInstruction) o;

    return labelName.equals(that.labelName);
  }

  @Override
  public int hashCode() {
    return labelName.hashCode();
  }

  public LabelInstruction(int lineNo, int columnNo, String labelName) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
    this.labelName = labelName;
  }

  @Override
  public String toString() {
    return "LABEL " + getLabelName();
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getLabel(labelName);
  }
}
