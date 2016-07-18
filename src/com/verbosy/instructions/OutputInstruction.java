package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.compiler.VerbosyValue;
import com.verbosy.instructions.primitive.Instruction;

public class OutputInstruction implements Instruction {
    @Override
    public void execute(VerbosyRuntime runtime) {
        VerbosyValue current = runtime.getCurrent();
        if (current != null) {
            runtime.getOutput().print(current);
        }
    }
}
