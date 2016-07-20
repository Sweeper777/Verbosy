package com.verbosy.compiler;

import com.verbosy.instructions.gt.GotoInstruction;
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
        runtime.setStopped(false);

        while (!runtime.isStopped()) {
            stepOver(runtime);
        }
    }

    public int getCurrentInstructionIndex() {
        return currentInstructionIndex;
    }

    public Instruction getCurrentInstruction() {
        return instructions[getCurrentInstructionIndex()];
    }

    public void stepOver(VerbosyRuntime runtime) {
        if (runtime.isStopped()) {
            return;
        }

        try {
            getCurrentInstruction().execute(runtime);

            if (getCurrentInstruction() instanceof GotoInstruction) {
                GotoInstruction gotoInstruction = (GotoInstruction)getCurrentInstruction();
                if (gotoInstruction.getGoToCondition().test(runtime)) {
                    currentInstructionIndex = gotoInstruction.getGoToLabel().getInstructionIndex();
                    return;
                }
            }

            currentInstructionIndex++;
        } catch (IndexOutOfBoundsException e) {
            runtime.setStopped(true);
        }
    }
}
