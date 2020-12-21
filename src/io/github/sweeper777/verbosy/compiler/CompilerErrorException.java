package io.github.sweeper777.verbosy.compiler;

public class CompilerErrorException extends Exception {
    private int instructionNumber;

    public int getInstructionNumber() {
        return instructionNumber;
    }

    public CompilerErrorException(String message, int instructionNumber) {
        super("Error at instruction#" + instructionNumber + ": " + message);
        this.instructionNumber = instructionNumber;
    }
}
