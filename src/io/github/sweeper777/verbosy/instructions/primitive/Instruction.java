package io.github.sweeper777.verbosy.instructions.primitive;

import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;

public interface Instruction {
    void execute(VerbosyRuntime runtime);
}
