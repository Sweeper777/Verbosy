package io.github.sweeper777.instructions;

import io.github.sweeper777.runtime.VerbosyRuntime;
import io.github.sweeper777.runtime.VerbosyValue;
import io.github.sweeper777.instructions.primitive.Instruction;

import java.io.Serializable;

public class OutputInstruction implements Instruction, Serializable {
    private long serialVersionUID = 3L;
    @Override
    public void execute(VerbosyRuntime runtime) {
        VerbosyValue current = runtime.getCurrent();
        if (current != null) {
            runtime.getOutput().print(current);
        }
    }
}
