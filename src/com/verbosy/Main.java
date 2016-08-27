package com.verbosy;

import com.verbosy.compiler.CompilerErrorException;
import com.verbosy.compiler.VerbosyCompiler;
import com.verbosy.compiler.VerbosyProgram;
import com.verbosy.runtime.StandardRuntime;
import com.verbosy.runtime.VerbosyRuntime;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        String code = "~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c";
//        String code = "~H o ~e o ~l o ~l o ~o o ~W o ~o o ~r o ~l o ~d o";
//        String code = "~0 /0 ~10 /1 :a: ^0 o \\0 -1 >-a";
//        String code = "~-10 o";
        String code = "~` /0 ^0 \\0 o";
        VerbosyCompiler compiler = new VerbosyCompiler();
        try {
            VerbosyProgram prog = compiler.compile(code);
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            VerbosyRuntime runtime = new StandardRuntime(input, 20);
            prog.run(runtime);
        } catch (CompilerErrorException e) {
            System.out.println(e.getMessage());
        }
    }
}
