package com.verbosy.instructions;

import com.verbosy.runtime.VerbosyRuntime;
import com.verbosy.instructions.primitive.Instruction;
import com.verbosy.runtime.VerbosyValue;

public class InputInstruction implements Instruction {

    @Override
    public void execute(VerbosyRuntime runtime) {
        VerbosyValue inputValue = runtime.getNextInput();

        if (inputValue == null) {
            runtime.setStopped(true);
            return;
        }

        runtime.setCurrent(inputValue);
    }
}
