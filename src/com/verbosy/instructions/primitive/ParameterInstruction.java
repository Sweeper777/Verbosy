package com.verbosy.instructions.primitive;

import com.verbosy.runtime.VerbosyParameter;

public interface ParameterInstruction extends Instruction {
    VerbosyParameter getParameter();
}
