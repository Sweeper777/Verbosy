package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.instructions.InstructionsFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class VerbosyCompiler {
  private final int memorySize;

  public VerbosyCompiler(int memorySize) {
    this.memorySize = memorySize;
  }

  public void compile(CharStream stream) throws IOException {
    List<ErrorMessage> errors = new ArrayList<>();
    VerbosyLexer lexer = new VerbosyLexer(stream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    VerbosyParser parser = new VerbosyParser(tokenStream);
    parser.getErrorListeners().clear();
    parser.addErrorListener(new ListErrorListener(errors));
    InstructionsFactory factory = new InstructionsFactory(errors);
    ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
    var instructions = factory.getParsedInstructions();
    var semanticAnalyser = new SemanticAnalyer(instructions, errors, memorySize);
    semanticAnalyser.analyseSemantics();
    if (errors.isEmpty()) {
      var codeGen = new CodeGenerator(instructions, null, null);
      codeGen.generateCode();
    } else {
      errors.forEach(System.out::println);
    }
  }
}
