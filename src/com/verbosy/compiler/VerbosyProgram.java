package com.verbosy.compiler;

import com.verbosy.instructions.primitive.Instruction;

import java.util.Arrays;

public final class VerbosyProgram {
    private Instruction[] instructions;
    private int currentInstructionIndex;

    protected VerbosyProgram(Instruction[] instructions) {
        this.instructions = instructions;
    }

    public Instruction[] getInstructions() {
        return Arrays.copyOf(instructions, instructions.length);
    }

    public void run(VerbosyRuntime runtime) {
        // TODO program execution
    }
}
