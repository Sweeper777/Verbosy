package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.instructions.primitive.Instruction;

public class InputInstruction implements Instruction {

    @Override
    public void execute(VerbosyRuntime runtime) {
        runtime.setCurrent(runtime.getNextInput());
    }
}
