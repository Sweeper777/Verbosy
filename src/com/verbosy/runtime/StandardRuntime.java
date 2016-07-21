package com.verbosy.runtime;

import java.io.PrintStream;
import java.util.Scanner;

public class StandardRuntime implements VerbosyRuntime {
    private VerbosyValue current;
    private VerbosyValue[] memory;
    private boolean stopped;
    private Scanner scanner;

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

        if (scanner.hasNext()) {
            char character = scanner.findWithinHorizon(".", 0).charAt(0);

            if (character == ' ') {
                return new VerbosyValue(0);
            }

            return new VerbosyValue(character);
        }

        return null;
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
    }
}
