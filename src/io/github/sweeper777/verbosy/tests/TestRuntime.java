package io.github.sweeper777.verbosy.tests;

import io.github.sweeper777.verbosy.runtime.StandardRuntime;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestRuntime extends StandardRuntime {

    private PrintStream stream;
    private ByteArrayOutputStream byteStream;

    public TestRuntime(String input, int memorySize) {
        super(input, memorySize);
        byteStream = new ByteArrayOutputStream();
        stream = new PrintStream(byteStream);
    }

    @Override
    public PrintStream getOutput() {
        return stream;
    }

    public String getOutputString() {
        return byteStream.toString();
    }
}
