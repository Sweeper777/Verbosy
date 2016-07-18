package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;

public class InputInstruction implements Instruction {

    @Override
    public void execute(VerbosyRuntime runtime) {
        runtime.setCurrent(runtime.getNextInput());
    }
}
