package io.github.sweeper777.verbosy.runtime;

import java.io.Serializable;

public class VerbosyParameter implements Serializable {
    private long serialVersionUID = 4L;
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
