package io.github.sweeper777.verbosy.tests;

import io.github.sweeper777.verbosy.runtime.StandardRuntime;
import io.github.sweeper777.verbosy.runtime.VerbosyValue;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class StandardRuntimeTest {

    @Test
    public void read2Numbers() {
        StandardRuntime runtime = new StandardRuntime("12345 67890", 10);
        VerbosyValue input = runtime.getNextInput();
        assertEquals(new VerbosyValue(12345), input);
        input = runtime.getNextInput();
        assertEquals(new VerbosyValue(67890), input);
    }

    @Test
    public void readChars() {
        StandardRuntime runtime = new StandardRuntime("abc", 10);
        VerbosyValue input = runtime.getNextInput();
        assertEquals(new VerbosyValue('a'), input);
        input = runtime.getNextInput();
        assertEquals(new VerbosyValue('b'), input);
        input = runtime.getNextInput();
        assertEquals(new VerbosyValue('c'), input);
    }

}