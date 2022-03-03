package io.github.sweeper777.verbosy.codegen;

import io.github.sweeper777.verbosy.syntax.Instruction;

import java.io.IOException;
import java.util.List;

public interface CompilerBackend {
    void generateCode(List<Instruction> instructions,String outputFileName) throws IOException;
}
