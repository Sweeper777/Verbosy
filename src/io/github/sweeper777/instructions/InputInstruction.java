package io.github.sweeper777.instructions;

import io.github.sweeper777.instructions.primitive.Instruction;
import io.github.sweeper777.runtime.VerbosyRuntime;
import io.github.sweeper777.runtime.VerbosyValue;

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
