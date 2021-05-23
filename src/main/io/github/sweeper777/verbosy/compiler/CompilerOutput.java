package io.github.sweeper777.verbosy.compiler;

public final class CompilerOutput {
  private final int lineNo;
  private final int columnNo;
  private final String message;

  public CompilerOutput(int lineNo, int columnNo, String message) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CompilerOutput that = (CompilerOutput) o;

    if (lineNo != that.lineNo) {
      return false;
    }
    if (columnNo != that.columnNo) {
      return false;
    }
    return message.equals(that.message);
  }

  @Override
  public int hashCode() {
    int result = lineNo;
    result = 31 * result + columnNo;
    result = 31 * result + message.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("(%d:%d) %s", lineNo, columnNo, message);
  }
}
