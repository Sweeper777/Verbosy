package io.github.sweeper777.verbosy;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) {
        verbosyLexer lexer = new verbosyLexer(
            CharStreams.fromString("~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c")
        );
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        verbosyParser parser = new verbosyParser(tokenStream);
        InstructionsFactory listener = new InstructionsFactory();
        ParseTreeWalker.DEFAULT.walk(listener, parser.compilation_unit());
    }
}
