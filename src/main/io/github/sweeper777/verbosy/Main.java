package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.csharp.CSharpCodeProvider;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;

public class Main {

    public static void main(String[] args) throws IOException {
        var compiler = new VerbosyCompiler(20, new CSharpCodeProvider(20));
        compiler.compile(
            CharStreams.fromString("~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c"),
            "output.exe"
        );
    }
}
