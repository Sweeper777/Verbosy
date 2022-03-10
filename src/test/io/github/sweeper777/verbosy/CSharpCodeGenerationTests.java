package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.cs.CSharpDictCodeProvider;
import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeGenerator;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static io.github.sweeper777.verbosy.TestUtils.runCode;
import static org.junit.Assert.assertEquals;

public class CSharpCodeGenerationTests {

  private static final CSharpCodeProviderSupplier codeProviderSupplier =
      (memorySize, readSpaceAsZero, readInts) -> new CSharpDictCodeProvider(readSpaceAsZero, readInts);

  @Test
  public void testIO() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
          codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("h", runCode("i o", "hello", compiler));
    assertEquals("hello", runCode(":a: i o >a", "hello", compiler));
  }

  @Test
  public void testHalt() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("", runCode("x ~x o", "", compiler));
    assertEquals("x", runCode(":a: ~x o x >a", "", compiler));
  }

  @Test
  public void testReadSpaceAs0() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, true, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("i0 i0 i0 i", runCode(":a: i o >a", "i i i i", compiler));
  }

  @Test
  public void testReadInts() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, true)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("-123 ", runCode("i o", "-123", compiler));
  }

  @Test
  public void testSet() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("hello", runCode("~h o ~e o ~l o ~l o ~o o", "", compiler));
    assertEquals(" ", runCode("~\\20 o", null, compiler));
  }

  @Test
  public void testAdd() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("11 ", runCode("~1 /0 ~10 +0 o", "", compiler));
    assertEquals("11 ", runCode("~20 /0 ~10 /20 ~1 +0* o", null, compiler));
    assertEquals("-2147483648 ", runCode("~2147483647 /0 ~1 +0 o", null, compiler));
  }


  @Test
  public void testSub() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("9 ", runCode("~1 /0 ~10 -0 o", "", compiler));
    assertEquals("-9 ", runCode("~20 /0 ~10 /20 ~1 -0* o", null, compiler));
    assertEquals("2147483647 ", runCode("~1 /0 ~-2147483648 -0 o", null, compiler));
  }

  @Test
  public void testInc() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("2 2 ", runCode("~1 /0 ^0 o ~0 \\0 o", "", compiler));
    assertEquals("bb", runCode("~a /0 ^0 o ~0 \\0 o", "", compiler));
  }

  @Test
  public void testDec() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("0 0 ", runCode("~1 /0 v0 o ~0 \\0 o", "", compiler));
    assertEquals("bb", runCode("~c /0 v0 o ~0 \\0 o", "", compiler));
  }

  @Test
  public void testGoto() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("b", runCode(">a ~a o :a: ~b o", "", compiler));
  }

  @Test
  public void testGotoIfZero() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("bde", runCode("~0 >0a ~a o :a: ~b o ~\\0 >0b ~c o :b: ~d o >0c ~e o :c:", "", compiler));
  }


  @Test
  public void testGotoIfNeg() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("bcde", runCode("~-1 >-a ~a o :a: ~b o ~0 >-b ~c o :b: ~d o >-c ~e o :c:", "", compiler));
  }

  @Test
  public void testNullMemory() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, false, false)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("", runCode("o \\0 /0 +0 -0 ^0 v0 \\0* /0* +0* -0* ^0* v0*", "", compiler));
  }

  @Test
  public void testSamplePrograms() throws IOException, InterruptedException {
    var stream1 = CharStreams.fromStream(Objects
        .requireNonNull(SampleProgramParserTests.class.getResourceAsStream("/tests/reverse.vp")));
    var stream2 = CharStreams.fromStream(Objects
        .requireNonNull(SampleProgramParserTests.class.getResourceAsStream("/tests/splitdigits.vp")));
    var compiler = new VerbosyCompiler(1024,
        new CSharpCodeGenerator(
            codeProviderSupplier.get(1024, true, true)
        )
    );
    compiler.setGenerateWarnings(false);
    assertEquals("olleh", runCode(stream1, "hello ", compiler));
    assertEquals("1 2 3 ", runCode(stream2, "123", compiler));
  }
}
