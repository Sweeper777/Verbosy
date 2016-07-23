package com.verbosy.compiler;

import com.verbosy.instructions.*;
import com.verbosy.instructions.gt.*;
import com.verbosy.instructions.primitive.Instruction;
import com.verbosy.instructions.primitive.Label;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class CompilerUtility {
    private CompilerUtility () { }

    private static HashMap<Pattern, Class<? extends Instruction>> instructionMap;
    private static HashMap<Class<? extends Instruction>, Integer> instructionSubstringStrategy;

    static {
        instructionMap = new HashMap<>();
        instructionMap.put(Pattern.compile("i"), InputInstruction.class);
        instructionMap.put(Pattern.compile("o"), OutputInstruction.class);
        instructionMap.put(Pattern.compile("\\\\\\d+[\\*]?"), TakeInstruction.class);
        instructionMap.put(Pattern.compile("/\\d+[\\*]?"), PutInstruction.class);
        instructionMap.put(Pattern.compile("\\+\\d+[\\*]?"), AddInstruction.class);
        instructionMap.put(Pattern.compile("-\\d+[\\*]?"), SubInstruction.class);
        instructionMap.put(Pattern.compile("\\^\\d+[\\*]?"), IncInstruction.class);
        instructionMap.put(Pattern.compile("v\\d+[\\*]?"), DecInstruction.class);
        instructionMap.put(Pattern.compile(">[a-zA-Z]+"), GotoInstruction.class);
        instructionMap.put(Pattern.compile(">0[a-zA-Z]+"), GotoIf0Instruction.class);
        instructionMap.put(Pattern.compile(">-[a-zA-Z]+"), GotoIfNegInstruction.class);
        instructionMap.put(Pattern.compile("~[a-zA-Z]"), SetInstruction.class);
        instructionMap.put(Pattern.compile("~-?\\d+"), SetInstruction.class);
        instructionMap.put(Pattern.compile(":[a-zA-Z]+:"), Label.class);

        instructionSubstringStrategy = new HashMap<>();
        instructionSubstringStrategy.put(TakeInstruction.class, 1);
        instructionSubstringStrategy.put(PutInstruction.class, 1);
        instructionSubstringStrategy.put(AddInstruction.class, 1);
        instructionSubstringStrategy.put(SubInstruction.class, 1);
        instructionSubstringStrategy.put(IncInstruction.class, 1);
        instructionSubstringStrategy.put(DecInstruction.class, 1);
        instructionSubstringStrategy.put(SetInstruction.class, 1);
        instructionSubstringStrategy.put(GotoInstruction.class, 1);
        instructionSubstringStrategy.put(GotoIf0Instruction.class, 2);
        instructionSubstringStrategy.put(GotoIfNegInstruction.class, 2);
    }

    public static Map<Pattern, Class<? extends Instruction>> getInstructionMap() {
        return Collections.unmodifiableMap(instructionMap);
    }

    public static Pattern getLabelPattern() {
        return Pattern.compile(":[a-zA-Z]+:");
    }

    public static HashMap<Class<? extends Instruction>, Integer> getInstructionSubstringStrategy() {
        return instructionSubstringStrategy;
    }
}
