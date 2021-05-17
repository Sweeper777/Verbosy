package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.instructions.InstructionsFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

  public void compile(CharStream stream, String outputFileName) throws IOException {
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
      File temp = File.createTempFile("verbosyOutput", ".cs");
      try (var file = new FileOutputStream(temp)) {
        var codeGen = new CodeGenerator(instructions, provider, file);
        codeGen.generateCode();
      }
      Runtime runtime = Runtime.getRuntime();
      Process p = runtime.exec("csc " + temp.getAbsolutePath() + " -warn:0 -out:" + outputFileName);
      try {
        p.waitFor();
      } catch (InterruptedException ex) {
        System.err.println("Interrupted when waiting for code to compile!");
      } finally {
        temp.delete();
      }
    } else {
      errors.forEach(System.err::println);
    }
  }
}
