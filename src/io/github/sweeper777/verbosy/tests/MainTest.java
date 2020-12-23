package io.github.sweeper777.verbosy.tests;

import io.github.sweeper777.verbosy.compiler.CompilerErrorException;
import io.github.sweeper777.verbosy.compiler.VerbosyProgram;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void helloWorld() throws IOException, CompilerErrorException {
        VerbosyProgram program = VerbosyProgram.fromSourceFile("src/io/github/sweeper777/verbosy/tests/helloworld.vp");
        TestRuntime runtime = new TestRuntime("", 10);
        program.run(runtime);
        assertEquals("HelloWorld", runtime.getOutputString());
    }

    @Test
    public void inputEcho() throws IOException, CompilerErrorException {
        VerbosyProgram program = VerbosyProgram.fromSourceFile("src/io/github/sweeper777/verbosy/tests/inputecho.vp");
        TestRuntime runtime = new TestRuntime("Echo This", 10);
        program.run(runtime);
        assertEquals("Echo This", runtime.getOutputString());
    }

    @Test
    public void addition() throws IOException, CompilerErrorException {
        VerbosyProgram program = VerbosyProgram.fromSourceFile("src/io/github/sweeper777/verbosy/tests/addition.vp");
        TestRuntime runtime = new TestRuntime("12345 67890", 10);
        program.run(runtime);
        assertEquals("80235 ", runtime.getOutputString());
    }
}