package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.CompilerBackend;
import io.github.sweeper777.verbosy.semantics.SemanticAnalyer;
import io.github.sweeper777.verbosy.syntax.InstructionsFactory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerbosyCompiler {
  private int memorySize;
  private final CompilerBackend backend;
  private boolean generateWarnings = true;
  private boolean eliminateDeadCode = true;
  private final List<Diagnostic> diagnostics = new ArrayList<>();

  public VerbosyCompiler(int memorySize, CompilerBackend backend) {
    this.memorySize = memorySize;
    this.backend = backend;
  }

  public void compile(CharStream stream, String outputFileName) throws IOException {
    VerbosyLexer lexer = new VerbosyLexer(stream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    VerbosyParser parser = new VerbosyParser(tokenStream);
    parser.getErrorListeners().clear();
    parser.addErrorListener(new ListErrorListener(diagnostics));
    InstructionsFactory factory = new InstructionsFactory(diagnostics);
    ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
    var instructions = factory.getParsedInstructions();
    var semanticAnalyser = new SemanticAnalyer(instructions, diagnostics, getMemorySize(), doesGenerateWarnings());
    semanticAnalyser.analyseSemantics();
    if (doesEliminateDeadCode()) {
      semanticAnalyser.eliminateDeadCode();
    }
    diagnostics.forEach(System.err::println);
    if (diagnostics.stream().noneMatch(x -> x.getType() == Diagnostic.Type.ERROR)) {
      backend.generateCode(instructions, outputFileName);
    }
  }

  public List<Diagnostic> getDiagnostics() {
    return diagnostics;
  }

  public int getMemorySize() {
    return memorySize;
  }

  public void setMemorySize(int memorySize) {
    this.memorySize = memorySize;
  }

  public boolean doesGenerateWarnings() {
    return generateWarnings;
  }

  public void setGenerateWarnings(boolean generateWarnings) {
    this.generateWarnings = generateWarnings;
  }

  public boolean doesEliminateDeadCode() {
    return eliminateDeadCode;
  }

  public void setEliminateCode(boolean eliminateDeadCode) {
    this.eliminateDeadCode = eliminateDeadCode;
  }
}
