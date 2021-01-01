package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.gt.GotoInstruction;
import io.github.sweeper777.verbosy.instructions.primitive.Instruction;
import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class VerbosyProgram implements Serializable {
    private final Instruction[] instructions;
    private final long serialVersionUID = 1L;

    protected VerbosyProgram(Instruction[] instructions) {
        this.instructions = instructions;
    }

    public Instruction[] getInstructions() {
        return Arrays.copyOf(instructions, instructions.length);
    }

    public void run(VerbosyRuntime runtime) {
        runtime.setStopped(false);
        int currentInstructionIndex = 0;

        while (!runtime.isStopped()) {
            currentInstructionIndex = stepOver(runtime, currentInstructionIndex);
        }
    }

    private int stepOver(VerbosyRuntime runtime, int currentInstructionIndex) {
        if (runtime.isStopped()) {
            return 0;
        }

        try {
            instructions[currentInstructionIndex].execute(runtime);

            if (instructions[currentInstructionIndex] instanceof GotoInstruction) {
                GotoInstruction gotoInstruction = (GotoInstruction)instructions[currentInstructionIndex];
                if (gotoInstruction.getGoToCondition().test(runtime)) {
                    return gotoInstruction.getGoToLabel().getInstructionIndex();
                }
            }

            return currentInstructionIndex + 1;
        } catch (IndexOutOfBoundsException e) {
            runtime.setStopped(true);
            return 0;
        }
    }

    public void saveAsBinary(String path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path, false));
        out.writeObject(this);
        out.close();
    }

    public static VerbosyProgram fromBinaryFile(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        VerbosyProgram obj = (VerbosyProgram) in.readObject();
        in.close();
        return obj;
    }

    public static VerbosyProgram fromSourceFile(String path) throws IOException, CompilerErrorException {
        String sourceCode = Files.readString(Path.of(path));
        VerbosyCompiler compiler = new VerbosyCompiler();
        return compiler.compile(sourceCode);
    }
}
