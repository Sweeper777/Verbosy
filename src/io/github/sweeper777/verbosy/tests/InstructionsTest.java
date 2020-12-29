package io.github.sweeper777.verbosy.tests;

import io.github.sweeper777.verbosy.compiler.CompilerErrorException;
import io.github.sweeper777.verbosy.compiler.VerbosyCompiler;
import io.github.sweeper777.verbosy.compiler.VerbosyProgram;
import io.github.sweeper777.verbosy.runtime.VerbosyValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstructionsTest {
    @Test
    public void incInstruction() throws CompilerErrorException {
        VerbosyProgram program = new VerbosyCompiler().compile("~0 /0 ^0");
        TestRuntime runtime = new TestRuntime("", 10);
        program.run(runtime);
        assertEquals(new VerbosyValue(1), runtime.getCurrent());
        assertEquals(new VerbosyValue(1), runtime.getMemory()[0]);
    }
    @Test
    public void decInstruction() throws CompilerErrorException {
        VerbosyProgram program = new VerbosyCompiler().compile("~0 /0 v0");
        TestRuntime runtime = new TestRuntime("", 10);
        program.run(runtime);
        assertEquals(new VerbosyValue(-1), runtime.getCurrent());
        assertEquals(new VerbosyValue(-1), runtime.getMemory()[0]);
    }

    @Test
    public void gotoIf0Test() throws CompilerErrorException {
        VerbosyProgram program = new VerbosyCompiler().compile("i >0a >b :a: o >end :b: ~1 o :end:");
        TestRuntime runtime1 = new TestRuntime("0", 10);
        program.run(runtime1);
        assertEquals("0 ", runtime1.getOutputString());
        TestRuntime runtime2 = new TestRuntime("1", 10);
        program.run(runtime2);
        assertEquals("1 ", runtime2.getOutputString());
    }
}
