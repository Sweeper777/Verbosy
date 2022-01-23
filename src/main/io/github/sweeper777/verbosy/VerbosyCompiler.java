package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.CodeGenerator;
import io.github.sweeper777.verbosy.codegen.CodeProvider;
import io.github.sweeper777.verbosy.semantics.SemanticAnalyer;
import io.github.sweeper777.verbosy.syntax.InstructionsFactory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerbosyCompiler {
  private final int memorySize;
  private final CodeProvider provider;
  private final boolean generateWarnings;
  private final List<Diagnostic> diagnostics = new ArrayList<>();

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
    parser.addErrorListener(new ListErrorListener(diagnostics));
    InstructionsFactory factory = new InstructionsFactory(diagnostics);
    ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
    var instructions = factory.getParsedInstructions();
    var semanticAnalyser = new SemanticAnalyer(instructions, diagnostics, memorySize, generateWarnings);
    semanticAnalyser.analyseSemantics();
    if (diagnostics.isEmpty()) {
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
          System.err.write(p.getInputStream().readAllBytes());
        }
      } catch (InterruptedException ex) {
        System.err.println("Interrupted when waiting for code to compile!");
      } finally {
        if (outputSourceFile == null) {
          sourceFile.delete();
        }
      }
    } else {
      diagnostics.forEach(System.err::println);
    }
  }

  public List<Diagnostic> getDiagnostics() {
    return diagnostics;
  }

  public void compile(CharStream stream, String outputFileName) throws IOException {
    compile(stream, outputFileName, null);
  }
}
