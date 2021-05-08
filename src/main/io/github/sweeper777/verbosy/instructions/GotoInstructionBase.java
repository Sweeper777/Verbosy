package io.github.sweeper777.verbosy.instructions;

public abstract class GotoInstructionBase implements Instruction {
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

    GotoInstructionBase that = (GotoInstructionBase) o;

    return labelName.equals(that.labelName);
  }

  @Override
  public int hashCode() {
    return labelName.hashCode();
  }

  public GotoInstructionBase(int lineNo, int columnNo, String labelName) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
    this.labelName = labelName;
  }

  public abstract String getName();

  @Override
  public String toString() {
    return getName() + " " + getLabelName();
  }
}
