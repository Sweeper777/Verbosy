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
    private final Map<String, Label> labelMap = new HashMap<>();
    private final Label lastLabel = new Label();

    private final boolean readSpaceAsZero;
    private final boolean readInts;

    public DefaultJvmCodeProvider(boolean readSpaceAsZero, boolean readInts) {
        this.readSpaceAsZero = readSpaceAsZero;
        this.readInts = readInts;
    }

}
