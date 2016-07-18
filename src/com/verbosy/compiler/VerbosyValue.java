package com.verbosy.compiler;

public class VerbosyValue {
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

    @Override
    public String toString() {
        if (getIsChar()) {
            return Character.toString(getCharValue());
        } else {
            return Integer.toString(getIntValue()) + " ";
        }
    }
}
