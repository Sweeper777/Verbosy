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

    }
}
