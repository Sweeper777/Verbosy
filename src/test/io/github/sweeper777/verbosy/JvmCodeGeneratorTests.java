package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.jvm.JvmCodeGenerator;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static io.github.sweeper777.verbosy.TestUtils.runCodeJvm;
import static org.junit.Assert.assertEquals;

public class JvmCodeGeneratorTests {

    private static final JvmCodeGeneratorSupplier codeGenerator = JvmCodeGenerator::new;

    @Test
    public void testIO() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("h", runCodeJvm("i o", "hello", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("hello", runCodeJvm(":a: i o >a", "hello", compiler));
    }

    @Test
    public void testHalt() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("", runCodeJvm("x ~x o", "", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("x", runCodeJvm(":a: ~x o x >a", "", compiler));
    }

    @Test
    public void testReadSpaceAs0() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(true, false));
        compiler.setGenerateWarnings(false);
        assertEquals("i0 i0 i0 i", runCodeJvm(":a: i o >a", "i i i i", compiler));
    }

    @Test
    public void testReadInts() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, true));
        compiler.setGenerateWarnings(false);
        assertEquals("-123 ", runCodeJvm("i o", "-123", compiler));
    }

    @Test
    public void testSet() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("hello", runCodeJvm("~h o ~e o ~l o ~l o ~o o", "", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals(" ", runCodeJvm("~\\20 o", null, compiler));
    }

    @Test
    public void testAdd() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("11 ", runCodeJvm("~1 /0 ~10 +0 o", "", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("11 ", runCodeJvm("~20 /0 ~10 /20 ~1 +0* o", "", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("-2147483648 ", runCodeJvm("~2147483647 /0 ~1 +0 o", "", compiler));
    }


    @Test
    public void testSub() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("9 ", runCodeJvm("~1 /0 ~10 -0 o", "", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("-9 ", runCodeJvm("~20 /0 ~10 /20 ~1 -0* o", null, compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("2147483647 ", runCodeJvm("~1 /0 ~-2147483648 -0 o", null, compiler));
    }

    @Test
    public void testInc() throws IOException, InterruptedException {
        var compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("2 2 ", runCodeJvm("~1 /0 ^0 o ~0 \\0 o", "", compiler));
        compiler = new VerbosyCompiler(1024, codeGenerator.get(false, false));
        compiler.setGenerateWarnings(false);
        assertEquals("bb", runCodeJvm("~a /0 ^0 o ~0 \\0 o", "", compiler));
    }


}
