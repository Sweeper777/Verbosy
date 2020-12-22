package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.gt.GotoInstruction;
import io.github.sweeper777.verbosy.instructions.primitive.Instruction;
import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class VerbosyProgram implements Serializable {
    private Instruction[] instructions;
    private int currentInstructionIndex;
    private long serialVersionUID = 1L;

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
