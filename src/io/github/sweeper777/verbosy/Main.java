package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.compiler.CompilerErrorException;
import io.github.sweeper777.verbosy.compiler.VerbosyProgram;
import io.github.sweeper777.verbosy.runtime.StandardRuntime;
import org.apache.commons.cli.*;

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
        CommandLineParser parser = new DefaultParser();
        try {
            Options cliOptions = getCommandLineOptions();
            CommandLine cl = parser.parse(cliOptions, args, true);
            if (cl.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("verbosy", cliOptions, true);
            } else if (cl.hasOption('c')) {

            } else {
                runVerbosySourceFile(cl);
            }

        } catch (ParseException e) {
            System.err.println("Exception occurred while parsing command line arguments.");
            System.err.println(e.getMessage());
        } catch (IOException | ClassNotFoundException | CompilerErrorException e) {
            e.printStackTrace();
        }
    }

    private static void runVerbosySourceFile(CommandLine cl) throws CompilerErrorException, IOException, ClassNotFoundException {
        VerbosyProgram prog = VerbosyProgram.fromSourceFile(cl.getArgs()[0]);
        StandardRuntime runtime = new StandardRuntime(System.in, 10);
        prog.run(runtime);
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

        Option spaceAsZero = Option.builder("z")
                .longOpt("space-as-zero")
                .desc("Reads spaces in the input as 0s. Makes reading words easier!")
                .build();

        Option compile = Option.builder("c")
                .longOpt("compile")
                .desc("compile a source file to binary file, ignores all other options")
                .build();

        Option runCompiled = Option.builder("r")
                .longOpt("run-compiled")
                .desc("run a binary verbosy program")
                .build();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Show help, ignores all other options")
                .build();

        Options options = new Options();
        OptionGroup group = new OptionGroup();
        group.addOption(compile).addOption(runCompiled).setRequired(false);
        options.addOption(input)
                .addOption(memorySize)
                .addOption(spaceAsZero)
                .addOptionGroup(group)
                .addOption(help);
        return options;
    }
}
