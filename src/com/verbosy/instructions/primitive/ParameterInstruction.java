package com.verbosy.instructions.primitive;

import com.verbosy.compiler.VerbosyParameter;
import com.verbosy.instructions.primitive.Instruction;

public interface ParameterInstruction extends Instruction {
    VerbosyParameter getParameter();
}
