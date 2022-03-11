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


}
