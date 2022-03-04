package io.github.sweeper777.verbosy.codegen.jvm;

import io.github.sweeper777.verbosy.codegen.CompilerBackend;
import io.github.sweeper777.verbosy.syntax.Instruction;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;
import javassist.bytecode.StackMapTable;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class JvmCodeGenerator implements CompilerBackend {
    @Override
    public void generateCode(List<Instruction> instructions, String outputFileName) throws IOException {
        var outputPath = Paths.get(outputFileName).toAbsolutePath();
        CtClass verbosyValueClass;
        CtClass utilsClass;
        verbosyValueClass = ClassPool.getDefault().makeClass(JvmCodeGenerator.class.getResourceAsStream("/VerbosyValue.class"));
        utilsClass = ClassPool.getDefault().makeClass(JvmCodeGenerator.class.getResourceAsStream("/Util.class"));
        verbosyValueClass.setName("VerbosyValue");
        utilsClass.setName("Util");

    }

}