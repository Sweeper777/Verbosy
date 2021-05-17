package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.instructions.InstructionsFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class VerbosyCompiler {
  private final int memorySize;
  private final CodeProvider provider;

  public VerbosyCompiler(int memorySize, CodeProvider provider) {
    this.memorySize = memorySize;
    this.provider = provider;
  }

  public void compile(CharStream stream, OutputStream output) throws IOException {
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
      var codeGen = new CodeGenerator(instructions, provider, output);
      codeGen.generateCode();
    } else {
      errors.forEach(System.err::println);
    }
    output.close();
  }
}
