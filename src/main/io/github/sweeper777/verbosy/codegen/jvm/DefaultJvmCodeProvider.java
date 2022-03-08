package io.github.sweeper777.verbosy.codegen.jvm;

import io.github.sweeper777.verbosy.syntax.Instruction;
import io.github.sweeper777.verbosy.syntax.instructions.AddInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.DecInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoIf0Instruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoIfNegInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.syntax.instructions.HaltInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.IncInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.InputInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.LabelInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.OutputInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.ParameterPointerInstructionBase;
import io.github.sweeper777.verbosy.syntax.instructions.PutInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.SetInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.SubInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.TakeInstruction;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class DefaultJvmCodeProvider implements JvmCodeProvider {
    private static final String UTIL_CLASS = "Util";
    private static final String UTIL_CURRENT = "current";
    private static final String UTIL_MEMORY = "memory";
    private static final String VERBOSY_VALUE_CLASS = "VerbosyValue";

    private final Map<String, Label> labelMap = new HashMap<>();
    private final Label lastLabel = new Label();

    private final boolean readSpaceAsZero;
    private final boolean readInts;

    public DefaultJvmCodeProvider(boolean readSpaceAsZero, boolean readInts) {
        this.readSpaceAsZero = readSpaceAsZero;
        this.readInts = readInts;
    }

    @Override
    public void provideCodeFor(Instruction instruction, MethodVisitor mv) {
        if (instruction instanceof ParameterPointerInstructionBase) {
            var paramInstr = (ParameterPointerInstructionBase)instruction;
            generateNullCheckForParameter(paramInstr.getParameter(), paramInstr.isPointer(), mv);
            if (instruction instanceof AddInstruction) {
                generateNullCheckForMemory(mv);
                generateNullCheckForCurrent(mv);
                generateGetCurrent(mv);
                mv.visitInsn(SWAP);
                mv.visitMethodInsn(INVOKEVIRTUAL,
                    VERBOSY_VALUE_CLASS,
                    "add",
                    Type.getMethodDescriptor(Type.getObjectType(VERBOSY_VALUE_CLASS), Type.getObjectType(VERBOSY_VALUE_CLASS)),
                    false
                );
                mv.visitFieldInsn(PUTSTATIC, UTIL_CLASS, UTIL_CURRENT, Type.getObjectType(VERBOSY_VALUE_CLASS).getDescriptor());
            }
            else if (instruction instanceof SubInstruction) {
                generateNullCheckForMemory(mv);
                generateNullCheckForCurrent(mv);
                generateGetCurrent(mv);
                mv.visitInsn(SWAP);
                mv.visitMethodInsn(INVOKEVIRTUAL,
                    VERBOSY_VALUE_CLASS,
                    "sub",
                    Type.getMethodDescriptor(Type.getObjectType(VERBOSY_VALUE_CLASS), Type.getObjectType(VERBOSY_VALUE_CLASS)),
                    false
                );
                mv.visitFieldInsn(PUTSTATIC, UTIL_CLASS, UTIL_CURRENT, Type.getObjectType(VERBOSY_VALUE_CLASS).getDescriptor());
            }
                generateNullCheckForMemory(mv);
                mv.visitFieldInsn(GETSTATIC, UTIL_CLASS, UTIL_MEMORY, Type.getDescriptor(HashMap.class));
                mv.visitInsn(SWAP);
                mv.visitMethodInsn(INVOKEVIRTUAL,
                    VERBOSY_VALUE_CLASS,
                    "inc",
                    Type.getMethodDescriptor(Type.getObjectType(VERBOSY_VALUE_CLASS)),
                    false
                );
                mv.visitInsn(DUP);
                mv.visitFieldInsn(PUTSTATIC, UTIL_CLASS, UTIL_CURRENT, Type.getObjectType(VERBOSY_VALUE_CLASS).getDescriptor());
                generateParameter(paramInstr.getParameter(), paramInstr.isPointer(), mv);
                mv.visitInsn(SWAP);
                mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    Type.getInternalName(HashMap.class),
                    "put",
                    Type.getMethodDescriptor(Type.getType(Object.class), Type.getType(Object.class), Type.getType(Object.class)),
                    false
                );
                mv.visitInsn(POP);
            }
            else if (instruction instanceof DecInstruction) {
                generateNullCheckForMemory(mv);
                mv.visitFieldInsn(GETSTATIC, UTIL_CLASS, UTIL_MEMORY, Type.getDescriptor(HashMap.class));
                mv.visitInsn(SWAP);
                mv.visitMethodInsn(INVOKEVIRTUAL,
                    VERBOSY_VALUE_CLASS,
                    "dec",
                    Type.getMethodDescriptor(Type.getObjectType(VERBOSY_VALUE_CLASS)),
                    false
                );
                mv.visitInsn(DUP);
                mv.visitFieldInsn(PUTSTATIC, UTIL_CLASS, UTIL_CURRENT, Type.getObjectType(VERBOSY_VALUE_CLASS).getDescriptor());
                generateParameter(paramInstr.getParameter(), paramInstr.isPointer(), mv);
                mv.visitInsn(SWAP);
                mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    Type.getInternalName(HashMap.class),
                    "put",
                    Type.getMethodDescriptor(Type.getType(Object.class), Type.getType(Object.class), Type.getType(Object.class)),
                    false
                );
                mv.visitInsn(POP);
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private void generatePushConst(int constant, MethodVisitor mv) {
        if (constant >= -1 && constant <= 5) {
            mv.visitInsn(ICONST_0 + constant);
        } else if (constant >= Byte.MIN_VALUE && constant <= Byte.MAX_VALUE) {
            mv.visitIntInsn(BIPUSH, constant);
        } else if (constant >= Short.MIN_VALUE && constant <= Short.MAX_VALUE) {
            mv.visitIntInsn(SIPUSH, constant);
        } else {
            mv.visitLdcInsn(constant);
        }
    }

    private void generateParameter(int param, boolean isPointer, MethodVisitor mv) {
        generatePushConst(param, mv);
        mv.visitInsn(isPointer ? ICONST_1 : ICONST_0);
        mv.visitMethodInsn(INVOKESTATIC,
            UTIL_CLASS,
            "resolveParameter",
            Type.getMethodDescriptor(Type.getType(Integer.class), Type.getType(int.class), Type.getType(boolean.class)),
            false
        );
    }

    private void generateNullCheckForParameter(int param, boolean isPointer, MethodVisitor mv) {
        generateParameter(param, isPointer, mv);
        mv.visitInsn(DUP);
        mv.visitJumpInsn(IFNULL, lastLabel);
    }

    private void generateNullCheckForMemory(MethodVisitor mv) {
        mv.visitFieldInsn(GETSTATIC, UTIL_CLASS, UTIL_MEMORY, Type.getDescriptor(HashMap.class));
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEVIRTUAL,
            Type.getInternalName(HashMap.class),
            "get",
            Type.getMethodDescriptor(Type.getType(Object.class), Type.getType(Object.class)),
            false
        );
        mv.visitInsn(DUP);
        mv.visitJumpInsn(IFNULL, lastLabel);
        mv.visitTypeInsn(CHECKCAST, VERBOSY_VALUE_CLASS);
    }

    private void generateGetCurrent(MethodVisitor mv) {
        mv.visitFieldInsn(GETSTATIC, UTIL_CLASS, UTIL_CURRENT, Type.getObjectType(VERBOSY_VALUE_CLASS).getDescriptor());
    }

    private void generateNullCheckForCurrent(MethodVisitor mv) {
        mv.visitFieldInsn(GETSTATIC, UTIL_CLASS, UTIL_CURRENT, Type.getObjectType(VERBOSY_VALUE_CLASS).getDescriptor());
        mv.visitJumpInsn(IFNULL, lastLabel);
    }

}
