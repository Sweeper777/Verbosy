package io.github.sweeper777.verbosy.runtime;

import java.io.PrintStream;

public interface VerbosyRuntime {
    VerbosyValue getCurrent();

    void setCurrent(VerbosyValue value);

    VerbosyValue[] getMemory();

    PrintStream getOutput();

    VerbosyValue getNextInput();

    boolean isStopped();

    void setStopped(boolean value);
}
