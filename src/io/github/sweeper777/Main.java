package io.github.sweeper777;

import io.github.sweeper777.compiler.CompilerErrorException;
import io.github.sweeper777.compiler.VerbosyProgram;
import io.github.sweeper777.runtime.StandardRuntime;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            VerbosyProgram prog = VerbosyProgram.fromSourceFile(args[0]);
            StandardRuntime runtime = new StandardRuntime(System.in, 10);
            prog.run(runtime);
        } catch (IOException | ClassNotFoundException | CompilerErrorException e) {
            e.printStackTrace();
        }
    }
}
