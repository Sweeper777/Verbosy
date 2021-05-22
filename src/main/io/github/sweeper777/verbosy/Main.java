package io.github.sweeper777.verbosy;

import java.io.IOException;
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
            System.err.println("Exception occurred while parsing command line arguments.");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compileWithArguments(CommandLine cl) throws IOException {
        
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

        Options options = new Options();
        options.addOption(memorySize)
            .addOption(spaceAsZero)
            .addOption(readInts)
            .addOption(output)
            .addOption(help)
            .addOption(outputSource);
        return options;
    }
}
