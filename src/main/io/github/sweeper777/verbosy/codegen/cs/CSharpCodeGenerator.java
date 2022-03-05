package io.github.sweeper777.verbosy.codegen.cs;

import io.github.sweeper777.verbosy.codegen.CompilerBackend;
import io.github.sweeper777.verbosy.syntax.Instruction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSharpCodeGenerator implements CompilerBackend {
  private final CSharpCodeProvider codeProvider;
  private String outputSourceFile;

  public CSharpCodeGenerator(CSharpCodeProvider codeProvider) {
    this.codeProvider = codeProvider;
  }

  @Override
  public void generateCode(List<Instruction> instructions, String outputFileName) throws IOException {
    File sourceFile;
    if (outputSourceFile == null) {
      sourceFile = File.createTempFile("verbosyOutput", ".cs");
    } else {
      sourceFile = new File(outputSourceFile);
    }
    try (var fileStream = new FileOutputStream(sourceFile);
         var outputFile = new BufferedWriter(new OutputStreamWriter(fileStream))) {
      outputFile.write(codeProvider.getHeader());
      for (var instruction : instructions) {
        outputFile.write(instruction.getCode(codeProvider));
      }
      outputFile.write(codeProvider.getLabel("_end_"));
      outputFile.write(codeProvider.getFooter());
      outputFile.flush();
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

  }

  public String getOutputSourceFile() {
    return outputSourceFile;
  }

  public void setOutputSourceFile(String outputSourceFile) {
    this.outputSourceFile = outputSourceFile;
  }
}
