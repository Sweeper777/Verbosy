package com.verbosy.compiler;

import java.io.PrintStream;

public interface VerbosyRuntime {
    VerbosyValue getCurrent();

    VerbosyValue[] getMemory();

    PrintStream getOutput();

    VerbosyValue getNextInput();

    boolean isStopped();

    void showErrorMessage(String message);
}
