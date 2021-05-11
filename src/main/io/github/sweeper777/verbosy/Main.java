package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.instructions.InstructionsFactory;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) {
        List<ErrorMessage> errors = new ArrayList<>();
        VerbosyLexer lexer = new VerbosyLexer(
            CharStreams.fromString("~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c")
        );
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        VerbosyParser parser = new VerbosyParser(tokenStream);
        parser.getErrorListeners().clear();
        parser.addErrorListener(new ListErrorListener(errors));
        InstructionsFactory factory = new InstructionsFactory(errors);
        ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
        errors.forEach(System.err::println);
        if (errors.isEmpty()) {
            factory.getParsedInstructions().forEach(System.out::println);
        }
    }
}
