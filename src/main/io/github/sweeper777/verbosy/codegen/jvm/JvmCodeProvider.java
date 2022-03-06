package io.github.sweeper777.verbosy.codegen.jvm;

import io.github.sweeper777.verbosy.syntax.Instruction;
import org.objectweb.asm.MethodVisitor;

public interface JvmCodeProvider {
    void provideCodeFor(Instruction instruction, MethodVisitor mv);

    void provideFooter(MethodVisitor mv);
}
