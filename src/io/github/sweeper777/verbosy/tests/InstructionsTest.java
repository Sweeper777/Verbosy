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
}
