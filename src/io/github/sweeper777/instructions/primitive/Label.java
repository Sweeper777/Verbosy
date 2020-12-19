package io.github.sweeper777.instructions.primitive;

import io.github.sweeper777.runtime.VerbosyRuntime;

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

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Label)) {
            return false;
        }

        return getName().equals(((Label)obj).getName());
    }
}
