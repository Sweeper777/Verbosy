package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyParameter;

public interface ParameterInstruction extends Instruction {
    VerbosyParameter getParameter();
}
