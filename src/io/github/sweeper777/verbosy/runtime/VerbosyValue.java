package io.github.sweeper777.verbosy.runtime;

import java.io.Serializable;

public class VerbosyValue implements Serializable {
    private long serialVersionUID = 5L;
    private int innerValue;
    private boolean isChar;

    public int getIntValue() {
        return innerValue;
    }

    public char getCharValue() {
        return (char)innerValue;
    }

    public boolean getIsChar() {
        return isChar;
    }

    public VerbosyValue(char c) {
        isChar = true;
        innerValue = c;
    }

    public VerbosyValue(int x) {
        isChar = false;
        innerValue = x;
    }

    public VerbosyValue increment (int value) {
        if (this.getIsChar()) {
            char newValue = (char)(this.getCharValue() + value);
            return new VerbosyValue(newValue);
        } else {
            int newValue = this.getIntValue() + value;
            return new VerbosyValue(newValue);
        }
    }

    @Override
    public String toString() {
        if (getIsChar()) {
            return Character.toString(getCharValue());
        } else {
            return Integer.toString(getIntValue()) + " ";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerbosyValue that = (VerbosyValue) o;

        if (innerValue != that.innerValue) return false;
        return isChar == that.isChar;
    }

    @Override
    public int hashCode() {
        int result = innerValue;
        result = 31 * result + (isChar ? 1 : 0);
        return result;
    }
}
