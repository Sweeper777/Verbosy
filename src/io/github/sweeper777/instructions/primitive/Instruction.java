package io.github.sweeper777.instructions.primitive;

import io.github.sweeper777.runtime.VerbosyRuntime;

public interface Instruction {
    void execute(VerbosyRuntime runtime);
}
