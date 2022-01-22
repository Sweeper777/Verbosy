package io.github.sweeper777.verbosy.syntax;

public final class VerbosyValue {
  private final int value;
  private final boolean isChar;

  public VerbosyValue(int value, boolean isChar) {
    this.value = value;
    this.isChar = isChar;
  }

  public int getValue() {
    return value;
  }

  public boolean isChar() {
    return isChar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    VerbosyValue that = (VerbosyValue) o;

    if (value != that.value) {
      return false;
    }
    return isChar == that.isChar;
  }

  @Override
  public int hashCode() {
    int result = value;
    result = 31 * result + (isChar ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    if (isChar()) {
      return String.format("'\\u%x'", value);
    } else {
      return Integer.toString(value);
    }
  }
}
