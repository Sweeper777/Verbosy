package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.compiler.CompilerErrorException;
import io.github.sweeper777.verbosy.compiler.VerbosyProgram;
import io.github.sweeper777.verbosy.runtime.StandardRuntime;

import java.io.IOException;

public class Main {

    /*
    Command Line options:
    --input, -i: input, use stdin if empty
    --memory-size, -s: number of elements in the memory array
    --space-as-zero, -z: read space characters as 0s
    --compile, -c: compile to binary file
    --run-compiled, -r: run a binary verbosy program
     */
    public static void main(String[] args) {
        try {
            VerbosyProgram prog = VerbosyProgram.fromSourceFile(args[0]);
            StandardRuntime runtime = new StandardRuntime(System.in, 10);
            prog.run(runtime);
        } catch (IOException | ClassNotFoundException | CompilerErrorException e) {
            e.printStackTrace();
        }
    }
    private static Options getCommandLineOptions() {
        Option input = Option.builder("i")
                .argName("input")
                .hasArg()
                .desc("Input for the verbosy program, uses stdin if not specified")
                .longOpt("input")
                .build();
        Option memorySize = Option.builder("s")
                .argName("size")
                .hasArg()
                .desc("number of elements in the memory array")
                .longOpt("memory-size")
                .type(int.class)
                .build();

    }
}
