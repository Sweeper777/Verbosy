package com.verbosy.instructions.primitive;

import com.verbosy.compiler.VerbosyRuntime;

public interface Instruction {
    void execute(VerbosyRuntime runtime);
}
