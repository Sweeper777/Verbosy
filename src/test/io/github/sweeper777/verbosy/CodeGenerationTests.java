package io.github.sweeper777.verbosy;

import static io.github.sweeper777.verbosy.TestUtils.runCode;
import static org.junit.Assert.assertEquals;

import io.github.sweeper777.verbosy.compiler.VerbosyCompiler;
import io.github.sweeper777.verbosy.csharp.CSharpCodeProvider;
import java.io.IOException;
import org.junit.Test;

public class CodeGenerationTests {

  @Test
  public void testIO() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeProvider(1024, false, false), false);
    assertEquals("h", runCode("i o", "hello", compiler));
    assertEquals("hello", runCode(":a: i o >a", "hello", compiler));
  }

  @Test
  public void testReadSpaceAs0() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeProvider(1024, true, false), false);
    assertEquals("i0 i0 i0 i", runCode(":a: i o >a", "i i i i", compiler));
  }

  @Test
  public void testReadInts() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeProvider(1024, false, true), false);
    assertEquals("-123 ", runCode("i o", "-123", compiler));
  }

  @Test
  public void testSet() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeProvider(1024, false, false), false);
    assertEquals("hello", runCode("~h o ~e o ~l o ~l o ~o o", "", compiler));
    assertEquals(" ", runCode("~\\20 o", null, compiler));
  }

}
