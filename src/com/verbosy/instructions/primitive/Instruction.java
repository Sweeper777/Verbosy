package com.verbosy.instructions.primitive;

import com.verbosy.runtime.VerbosyRuntime;

public interface Instruction {
    void execute(VerbosyRuntime runtime);
}
