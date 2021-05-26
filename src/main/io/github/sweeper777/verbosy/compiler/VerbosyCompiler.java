package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.VerbosyLexer;
import io.github.sweeper777.verbosy.VerbosyParser;
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
  private final boolean generateWarnings;
  private final List<CompilerOutput> compilerOutputs = new ArrayList<>();

  public VerbosyCompiler(int memorySize, CodeProvider provider, boolean generateWarnings) {
    this.memorySize = memorySize;
    this.provider = provider;
    this.generateWarnings = generateWarnings;
  }

  public void compile(CharStream stream, String outputFileName, String outputSourceFile) throws IOException {
    VerbosyLexer lexer = new VerbosyLexer(stream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    VerbosyParser parser = new VerbosyParser(tokenStream);
    parser.getErrorListeners().clear();
    parser.addErrorListener(new ListErrorListener(compilerOutputs));
    InstructionsFactory factory = new InstructionsFactory(compilerOutputs);
    ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
    var instructions = factory.getParsedInstructions();
    var semanticAnalyser = new SemanticAnalyer(instructions, compilerOutputs, memorySize, generateWarnings);
    semanticAnalyser.analyseSemantics();
    if (compilerOutputs.isEmpty()) {
      File sourceFile;
      if (outputSourceFile == null) {
        sourceFile = File.createTempFile("verbosyOutput", ".cs");
      } else {
        sourceFile = new File(outputSourceFile);
      }
      try (var file = new FileOutputStream(sourceFile)) {
        var codeGen = new CodeGenerator(instructions, provider, file);
        codeGen.generateCode();
      }
      Runtime runtime = Runtime.getRuntime();
      Process p = runtime.exec("csc " + sourceFile.getAbsolutePath() + " -warn:0 -out:" + outputFileName);
      try {
        p.waitFor();
        if (p.exitValue() != 0) {
          System.out.println("Something wrong happened while generating IL!");
        }
      } catch (InterruptedException ex) {
        System.err.println("Interrupted when waiting for code to compile!");
      } finally {
        if (outputSourceFile == null) {
          sourceFile.delete();
        }
      }
    } else {
      compilerOutputs.forEach(System.err::println);
    }
  }

  public List<CompilerOutput> getCompilerOutputs() {
    return compilerOutputs;
  }

  public void compile(CharStream stream, String outputFileName) throws IOException {
    compile(stream, outputFileName, null);
  }
}
