package io.github.sweeper777.instructions.primitive;

import io.github.sweeper777.runtime.VerbosyParameter;

public interface ParameterInstruction extends Instruction {
    VerbosyParameter getParameter();
}
