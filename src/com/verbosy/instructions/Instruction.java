package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;

public interface Instruction {
    void execute(VerbosyRuntime runtime);
}
