package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.csharp.CSharpCodeProvider;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {

    public static void main(String[] args) throws IOException {
        var compiler = new VerbosyCompiler(20, new CSharpCodeProvider(100, true, false));
        compiler.compile(
            CharStreams.fromString("~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c"),
            "output.exe",
            "source.cs"
        );
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
            .addOption(help);
        return options;
    }
}
