package io.github.sweeper777.verbosy.instructions.primitive;

import io.github.sweeper777.verbosy.runtime.VerbosyParameter;

public interface ParameterInstruction extends Instruction {
    VerbosyParameter getParameter();
}
