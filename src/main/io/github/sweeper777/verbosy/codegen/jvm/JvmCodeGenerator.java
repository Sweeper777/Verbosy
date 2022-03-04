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

        try {
            var mainClass = new ClassFile(false, "VerbosyProgram", null);
            var constPool = mainClass.getConstPool();
            var mainMethodCode = new Bytecode(constPool);

            for (var instruction: instructions) {
                generateCodeForInstruction(mainMethodCode, instruction);
            }
            int label = mainMethodCode.currentPc();
            mainMethodCode.addGetstatic(ClassPool.getDefault().get("java.lang.System"), "out", "Ljava/io/PrintStream;");
            mainMethodCode.addLdc("Hello World!");
            mainMethodCode.addStore(1, ClassPool.getDefault().get("java.lang.String"));
            mainMethodCode.addLoad(1, ClassPool.getDefault().get("java.lang.String"));
            mainMethodCode.addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
            mainMethodCode.addOpcode(Opcode.GOTO);
            var offset = label - mainMethodCode.currentPc() + 1;
            mainMethodCode.addIndex(offset);
            var stackMap = new StackMapTable.Writer(0);
            stackMap.sameFrame(0);

            mainMethodCode.setMaxLocals(2);
            var mainMethodInfo = new MethodInfo(constPool, "main", "([Ljava/lang/String;)V");
            var codeAttr = mainMethodCode.toCodeAttribute();
            codeAttr.setAttribute(stackMap.toStackMapTable(constPool));
            mainMethodInfo.setCodeAttribute(codeAttr);
            mainClass.addMethod(mainMethodInfo);
            mainClass.setAccessFlags(AccessFlag.PUBLIC);
            mainMethodInfo.setAccessFlags(AccessFlag.PUBLIC | AccessFlag.STATIC);
        } catch (CannotCompileException | NotFoundException e) {
            throw new IOException(e);
        }
    }

}