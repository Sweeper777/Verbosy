package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;

public class Label implements Instruction {
    private int instructionIndex;
    private String name;

    public Label(int instructionIndex, String name) {
        this.instructionIndex = instructionIndex;
        this.name = name;
    }

    public int getInstructionIndex() {
        return instructionIndex;
    }

    public String getName() {
        return name;
    }

    @Override
    public void execute(VerbosyRuntime runtime) {
        // do nothing
    }
}
