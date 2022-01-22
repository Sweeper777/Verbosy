package io.github.sweeper777.verbosy.codegen;

import io.github.sweeper777.verbosy.syntax.Instruction;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class CodeGenerator {
  private final List<Instruction> instructions;
  private final CodeProvider codeProvider;
  private final BufferedWriter outputFile;

  public CodeGenerator(
      List<Instruction> instructions, CodeProvider codeProvider, OutputStream output) {
    this.instructions = instructions;
    this.codeProvider = codeProvider;
    this.outputFile = new BufferedWriter(new OutputStreamWriter(output));
  }

  public void generateCode() throws IOException {
    outputFile.write(codeProvider.getHeader());
    for (var instruction : instructions) {
      outputFile.write(instruction.getCode(codeProvider));
    }
    outputFile.write(codeProvider.getLabel("_end_"));
    outputFile.write(codeProvider.getFooter());
    outputFile.flush();
  }
}
