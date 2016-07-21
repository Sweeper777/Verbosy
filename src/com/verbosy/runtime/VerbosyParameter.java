package com.verbosy.runtime;

public class VerbosyParameter {
    private VerbosyValue value;
    private boolean pointer;

    public VerbosyValue getValue() {
        return value;
    }

    public boolean isPointer() {
        return pointer;
    }

    public VerbosyParameter(int x, boolean isPointer) {
        value = new VerbosyValue(x);
        pointer = isPointer;
    }

    public VerbosyParameter(char c, boolean isPointer) {
        value = new VerbosyValue(c);
        pointer = isPointer;
    }
}
