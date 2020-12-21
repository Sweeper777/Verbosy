package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.gt.GotoInstruction;
import io.github.sweeper777.verbosy.instructions.primitive.Instruction;
import io.github.sweeper777.verbosy.instructions.primitive.Label;
import io.github.sweeper777.verbosy.instructions.primitive.ParameterInstruction;
import io.github.sweeper777.verbosy.runtime.VerbosyParameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Pattern;

public class VerbosyCompiler {
    public VerbosyProgram compile(String code) throws CompilerErrorException {
        // initialization
        String[] instructionStrings = code.split("\\p{Space}");
        List<String> instructionsList = new ArrayList<>(Arrays.asList(instructionStrings));
        instructionsList.removeAll(Collections.singleton(""));
        instructionStrings = instructionsList.toArray(new String[instructionsList.size()]);
        Instruction[] instructionObjects = new Instruction[instructionStrings.length];
        HashMap<String, Label> labelMap = new HashMap<>();

        // find all labels
        for (int instructionIndex = 0 ; instructionIndex < instructionStrings.length ; instructionIndex++) {
            String instructionString = instructionStrings[instructionIndex];
            if (CompilerUtility.getLabelPattern().matcher(instructionString).matches()) {
                String labelName = instructionString.substring(1, instructionString.length() - 1);

                if (labelMap.containsKey(labelName)) {
                    throw new CompilerErrorException("Duplicate label", instructionIndex);
                }

                Label lblObject = new Label(instructionIndex, labelName);
                instructionObjects[instructionIndex] = lblObject;
                labelMap.put(labelName, lblObject);
            }
        }

        // parse other instructions
        for (int instructionIndex = 0 ; instructionIndex < instructionStrings.length ; instructionIndex++) {
            if (instructionObjects[instructionIndex] != null) {
                continue;
            }

            String instructionString = instructionStrings[instructionIndex];

            Class<? extends Instruction> instructionType = null;
            for (Pattern p : CompilerUtility.getInstructionMap().keySet()) {
                if (p.matcher(instructionString).matches()) {
                    instructionType = CompilerUtility.getInstructionMap().get(p);
                    break;
                }
            }

            if (instructionType == null) {
                throw new CompilerErrorException("Unknown instruction", instructionIndex);
            }

            if (ParameterInstruction.class.isAssignableFrom(instructionType)) {
                String parameterString = instructionString.substring(CompilerUtility
                        .getInstructionSubstringStrategy().get(instructionType));

                // decide whether it's pointer
                boolean isPointer = parameterString.endsWith("*");

                if (isPointer) {
                    parameterString = parameterString.substring(0, parameterString.length() - 1);
                }

                try {
                    // parse integer parameter
                    int parameterInt = Integer.parseInt(parameterString);
                    VerbosyParameter param = new VerbosyParameter(parameterInt, isPointer);
                    Constructor ctor = instructionType.getConstructor(VerbosyParameter.class);
                    instructionObjects[instructionIndex] = (Instruction)ctor.newInstance(param);
                } catch (NumberFormatException e) {
                    if (parameterString.length() != 1) {
                        throw new CompilerErrorException("Invalid parameter", instructionIndex);
                    }

                    // parse character parameter
                    char parameterChar = parameterString.charAt(0);
                    VerbosyParameter param = new VerbosyParameter(parameterChar, isPointer);
                    Constructor ctor;
                    try {
                        ctor = instructionType.getConstructor(VerbosyParameter.class);
                        instructionObjects[instructionIndex] = (Instruction)ctor.newInstance(param);
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
                        throw new CompilerErrorException("Invalid parameter", instructionIndex);
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new CompilerErrorException("Invalid parameter", instructionIndex);
                }
            } else if (GotoInstruction.class.isAssignableFrom(instructionType)) {
                //parse goto instructions
                String labelName = instructionString.substring(CompilerUtility.getInstructionSubstringStrategy().get(instructionType));
                if (!labelMap.containsKey(labelName)) {
                    throw new CompilerErrorException("Unknown label", instructionIndex);
                }

                try {
                    Constructor ctor = instructionType.getConstructor(Label.class);
                    instructionObjects[instructionIndex] = (Instruction)ctor.newInstance(labelMap.get(labelName));
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new CompilerErrorException("Invalid parameter", instructionIndex);
                }
            } else {
                try {
                    instructionObjects[instructionIndex] = instructionType.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new CompilerErrorException(
                            "Instruction class for " + instructionString +
                                    " must have a parameterless constructor", instructionIndex);
                }
            }
        }

        return new VerbosyProgram(instructionObjects);
    }
}
