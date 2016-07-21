package com.verbosy;

import com.verbosy.compiler.CompilerErrorException;
import com.verbosy.compiler.VerbosyCompiler;

public class Main {

    public static void main(String[] args) {
        String code = "~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \\14* o v14 >0a >c";
        VerbosyCompiler compiler = new VerbosyCompiler();
        try {
            compiler.compile(code);
        } catch (CompilerErrorException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("YAY!");
    }
}
