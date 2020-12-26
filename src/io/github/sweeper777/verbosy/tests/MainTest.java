package io.github.sweeper777.verbosy.tests;

import io.github.sweeper777.verbosy.Main;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MainTest {

    private ByteArrayOutputStream byteStream;

    @Test
    public void runningSourceFile() {
        Main.main(new String[] { "src/io/github/sweeper777/verbosy/tests/helloworld.vp" });
        assertEquals("HelloWorld", byteStream.toString());
    }

    }

    @Before
    public void setUp() {
        byteStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(byteStream);
        System.setOut(stream);
        InputStream input = new ByteArrayInputStream("hello world".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);
    }
}