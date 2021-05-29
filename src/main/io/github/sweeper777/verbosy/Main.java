package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.compiler.VerbosyCompiler;
import io.github.sweeper777.verbosy.csharp.CSharpArrayCodeProvider;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {

            Options cliOptions = getCommandLineOptions();
            CommandLine cl = parser.parse(cliOptions, args, true);
            if (cl.hasOption('h')) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("verbosy", cliOptions, true);
            } else if (cl.getArgs().length > 0){
                compileWithArguments(cl);
            } else {
                throw new ParseException("No Input File Specified");
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
        }
    }

    private static void compileWithArguments(CommandLine cl) throws IOException, ParseException {
        String inputFilePath = cl.getArgs()[0];
        int memorySize = 1024;
        if (cl.hasOption('s')) {
            try {
                memorySize = Integer.parseUnsignedInt(cl.getOptionValue('s'));
            } catch (NumberFormatException ex) {
                throw new ParseException("Invalid number specified for -s.");
            }
        }
        var codeProvider = new CSharpArrayCodeProvider(
            memorySize, cl.hasOption('z'), cl.hasOption('i')
        );
        var compiler = new VerbosyCompiler(memorySize, codeProvider, !cl.hasOption('n'));
        String outputFile = "a.out";
        if (cl.hasOption('o')) {
            outputFile = cl.getOptionValue('o');
        }
        String outputSourceFile = null;
        if (cl.hasOption('S')) {
            outputSourceFile = cl.getOptionValue('S');
        }
        compiler.compile(CharStreams.fromFileName(inputFilePath), outputFile, outputSourceFile);
    }

    private static Options getCommandLineOptions() {
        Option memorySize = Option.builder("s")
            .argName("size")
            .hasArg()
            .desc("number of elements in the memory array, default to 1024")
            .longOpt("memory-size")
            .type(Integer.class)
            .build();

        Option spaceAsZero = Option.builder("z")
            .longOpt("space-as-zero")
            .desc("reads spaces in the input as 0s. Makes reading words easier!")
            .build();

        Option readInts = Option.builder("i")
            .longOpt("read-ints")
            .desc("parses integers on the input stream automatically")
            .build();

        Option output = Option.builder("o")
            .longOpt("output")
            .desc("output file path")
            .hasArg()
            .argName("output-file")
            .build();

        Option outputSource = Option.builder("S")
            .longOpt("output")
            .desc("file path for output C# source file")
            .hasArg()
            .argName("output-source-file")
            .build();

        Option help = Option.builder("h")
            .longOpt("help")
            .desc("Show help, ignores all other options")
            .build();

        Option noWarnings = Option.builder("n")
            .longOpt("nowarn")
            .desc("disable warnings")
            .build();

        Options options = new Options();
        options.addOption(memorySize)
            .addOption(spaceAsZero)
            .addOption(readInts)
            .addOption(output)
            .addOption(help)
            .addOption(outputSource)
            .addOption(noWarnings);
        return options;
    }
}
