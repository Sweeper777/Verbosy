package io.github.sweeper777.verbosy.runtime;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StandardRuntime implements VerbosyRuntime {
    private VerbosyValue current;
    private VerbosyValue[] memory;
    private boolean stopped;
    private Scanner scanner;
    private boolean readSpaceAsZero;

    @Override
    public VerbosyValue getCurrent() {
        return current;
    }

    @Override
    public void setCurrent(VerbosyValue value) {
        current = value;
    }

    @Override
    public VerbosyValue[] getMemory() {
        return memory;
    }

    @Override
    public PrintStream getOutput() {
        return System.out;
    }

    @Override
    public VerbosyValue getNextInput() {
        if (scanner.hasNextInt()) {
            return new VerbosyValue(scanner.nextInt());
        }

        scanner.useDelimiter("");
        String token = scanner.findWithinHorizon(".", 0);
        scanner.useDelimiter("\\s+");

        if (token == null) {
            return null;
        } else if (token.charAt(0) == ' ' && readSpaceAsZero()) {
            return new VerbosyValue(0);
        } else {
            return new VerbosyValue(token.charAt(0));
        }
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void setStopped(boolean value) {
        stopped = value;

        if (value) {
            scanner.close();
        }
    }

    public StandardRuntime(String input, int memorySize) {
        memory = new VerbosyValue[memorySize];
        scanner = new Scanner(input);
        scanner.useDelimiter("\\s+");
    }

    public StandardRuntime(InputStream is, int memorySize) {
        memory = new VerbosyValue[memorySize];
        scanner = new Scanner(is);
        scanner.useDelimiter("\\s+");
    }

    public boolean readSpaceAsZero() {
        return readSpaceAsZero;
    }

    public void setReadSpaceAsZero(boolean readSpaceAsZero) {
        this.readSpaceAsZero = readSpaceAsZero;
    }
}
