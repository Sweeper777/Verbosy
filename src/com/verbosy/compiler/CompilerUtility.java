package com.verbosy.compiler;

import com.verbosy.instructions.*;
import com.verbosy.instructions.gt.*;
import com.verbosy.instructions.primitive.Instruction;

import java.util.HashMap;

public final class CompilerUtility {
    private CompilerUtility () { }

    private static HashMap<String, Class<? extends Instruction>> instructionMap;

    static {
        instructionMap = new HashMap<>();
        instructionMap.put("i", InputInstruction.class);
        instructionMap.put("o", OutputInstruction.class);
        instructionMap.put("\\", TakeInstruction.class);
        instructionMap.put("/", PutInstruction.class);
        instructionMap.put("+", AddInstruction.class);
        instructionMap.put("-", SubInstruction.class);
        instructionMap.put("^", IncInstruction.class);
        instructionMap.put("v", DecInstruction.class);
        instructionMap.put(">", GotoInstruction.class);
        instructionMap.put(">0", GotoIf0Instruction.class);
        instructionMap.put(">-", GotoIfNegInstruction.class);
        instructionMap.put("~", SetInstruction.class);
    }

    public HashMap<String, Class<? extends Instruction>> getInstructionMao() {
        return new HashMap<>(instructionMap);
    }
}
