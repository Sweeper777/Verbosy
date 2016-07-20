package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.instructions.primitive.Instruction;

public class InputInstruction implements Instruction {

    @Override
    public void execute(VerbosyRuntime runtime) {
        if (runtime.getNextInput() == null) {
            runtime.setStopped(true);
            return;
        }

        runtime.setCurrent(runtime.getNextInput());
    }
}
