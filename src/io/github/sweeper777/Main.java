package io.github.sweeper777;

import io.github.sweeper777.compiler.CompilerErrorException;
import io.github.sweeper777.compiler.VerbosyCompiler;
import io.github.sweeper777.compiler.VerbosyProgram;
import io.github.sweeper777.runtime.StandardRuntime;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        String code = "~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c";
//        String code = "~H o ~e o ~l o ~l o ~o o ~W o ~o o ~r o ~l o ~d o";
//        String code = "~0 /0 ~10 /1 :a: ^0 o \\0 -1 >-a";
//        String code = "~-10 o";
//        String code = "~` /3 ^3 o";
        test1();
    }

    private static void test1() {
        try {
            VerbosyProgram prog = VerbosyProgram.readFromFile("/Users/mulangsu/Desktop/", "program.vp");
            StandardRuntime runtime = new StandardRuntime("", 10);
            prog.run(runtime);
        } catch (IOException |  ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        String code = "~H o ~e o ~l o ~l o ~o o ~W o ~o o ~r o ~l o ~d o";
        VerbosyCompiler compiler = new VerbosyCompiler();
        VerbosyProgram prog = null;
        try {
            prog = compiler.compile(code);
            prog.saveToFile("/Users/mulangsu/Desktop/", "program.vp");
        } catch (CompilerErrorException | IOException e) {
            e.printStackTrace();
        }
    }
}
