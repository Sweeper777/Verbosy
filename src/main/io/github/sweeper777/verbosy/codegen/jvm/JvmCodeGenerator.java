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
}