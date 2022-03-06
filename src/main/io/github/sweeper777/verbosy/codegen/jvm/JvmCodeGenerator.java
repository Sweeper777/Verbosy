package io.github.sweeper777.verbosy.codegen.jvm;

import io.github.sweeper777.verbosy.codegen.CompilerBackend;
import io.github.sweeper777.verbosy.syntax.Instruction;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class JvmCodeGenerator implements CompilerBackend {
    public JvmCodeProvider getCodeProvider() {
        return codeProvider;
    }

    private final JvmCodeProvider codeProvider;

    public JvmCodeGenerator(boolean readSpaceAsZero, boolean readInts) {
        codeProvider = new DefaultJvmCodeProvider(readSpaceAsZero, readInts);
    }

    @Override
    public void generateCode(List<Instruction> instructions, String outputFileName) throws IOException {

    }
}