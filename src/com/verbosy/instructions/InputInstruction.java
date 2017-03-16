package com.verbosy.instructions;

import com.verbosy.instructions.primitive.Instruction;
import com.verbosy.runtime.VerbosyRuntime;
import com.verbosy.runtime.VerbosyValue;

import java.io.Serializable;

public class InputInstruction implements Instruction, Serializable {
    private long serialVersionUID = 12L;

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
