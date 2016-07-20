package com.verbosy;

import com.verbosy.compiler.CompilerUtility;
import com.verbosy.instructions.primitive.Instruction;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String[] arr = "~0 /14 :a: ^14 i >0b: /14* >a: :b: v14 :c: \\14* o v14 >0a: >c:".split(" ");
        ArrayList<String> instructionNames = new ArrayList<>();
	    for (String instruction : arr) {
            for (Pattern p : CompilerUtility.getInstructionMap().keySet()) {
                if (p.matcher(instruction).matches()) {
                    Class<? extends Instruction> instructionType = CompilerUtility.getInstructionMap().get(p);
                    instructionNames.add(instructionType.getSimpleName());
                    break;
                }
            }
        }

        instructionNames.forEach(System.out::println);
    }
}
