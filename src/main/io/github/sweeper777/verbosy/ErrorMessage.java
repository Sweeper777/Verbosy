package io.github.sweeper777.verbosy;

public final class ErrorMessage {
  private final int lineNo;
  private final int columnNo;
  private final String message;

  public ErrorMessage(int lineNo, int columnNo, String message) {
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

    ErrorMessage that = (ErrorMessage) o;

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
