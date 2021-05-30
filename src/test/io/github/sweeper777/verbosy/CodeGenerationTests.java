package io.github.sweeper777.verbosy;

import static io.github.sweeper777.verbosy.TestUtils.add;
import static io.github.sweeper777.verbosy.TestUtils.dec;
import static io.github.sweeper777.verbosy.TestUtils.inc;
import static io.github.sweeper777.verbosy.TestUtils.input;
import static io.github.sweeper777.verbosy.TestUtils.output;
import static io.github.sweeper777.verbosy.TestUtils.put;
import static io.github.sweeper777.verbosy.TestUtils.runCode;
import static io.github.sweeper777.verbosy.TestUtils.set;
import static io.github.sweeper777.verbosy.TestUtils.sub;
import static io.github.sweeper777.verbosy.TestUtils.take;
import static org.junit.Assert.assertEquals;

import io.github.sweeper777.verbosy.compiler.CodeGenerator;
import io.github.sweeper777.verbosy.compiler.CodeProvider;
import io.github.sweeper777.verbosy.compiler.VerbosyCompiler;
import io.github.sweeper777.verbosy.csharp.CSharpDictCodeProvider;
import io.github.sweeper777.verbosy.instructions.Instruction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

public class CodeGenerationTests {

  private static final CodeProviderSupplier codeProviderSupplier =
      (memorySize, readSpaceAsZero, readInts) -> new CSharpDictCodeProvider(readSpaceAsZero, readInts);

  @Test
  public void testIO() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("h", runCode("i o", "hello", compiler));
    assertEquals("hello", runCode(":a: i o >a", "hello", compiler));
  }

  @Test
  public void testReadSpaceAs0() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, true, false), false);
    assertEquals("i0 i0 i0 i", runCode(":a: i o >a", "i i i i", compiler));
  }

  @Test
  public void testReadInts() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, true), false);
    assertEquals("-123 ", runCode("i o", "-123", compiler));
  }

  @Test
  public void testSet() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("hello", runCode("~h o ~e o ~l o ~l o ~o o", "", compiler));
    assertEquals(" ", runCode("~\\20 o", null, compiler));
  }

  @Test
  public void testAdd() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("11 ", runCode("~1 /0 ~10 +0 o", "", compiler));
    assertEquals("11 ", runCode("~20 /0 ~10 /20 ~1 +0* o", null, compiler));
    assertEquals("-2147483648 ", runCode("~2147483647 /0 ~1 +0 o", null, compiler));
  }


  @Test
  public void testSub() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("9 ", runCode("~1 /0 ~10 -0 o", "", compiler));
    assertEquals("-9 ", runCode("~20 /0 ~10 /20 ~1 -0* o", null, compiler));
    assertEquals("2147483647 ", runCode("~1 /0 ~-2147483648 -0 o", null, compiler));
  }

  @Test
  public void testInc() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("2 2 ", runCode("~1 /0 ^0 o ~0 \\0 o", "", compiler));
    assertEquals("bb", runCode("~a /0 ^0 o ~0 \\0 o", "", compiler));
  }

  @Test
  public void testDec() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("0 0 ", runCode("~1 /0 v0 o ~0 \\0 o", "", compiler));
    assertEquals("bb", runCode("~c /0 v0 o ~0 \\0 o", "", compiler));
  }

  @Test
  public void testGoto() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("b", runCode(">a ~a o :a: ~b o", "", compiler));
  }

  @Test
  public void testGotoIfZero() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("bde", runCode("~0 >0a ~a o :a: ~b o ~\\0 >0b ~c o :b: ~d o >0c ~e o :c:", "", compiler));
  }


  @Test
  public void testGotoIfNeg() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("bcde", runCode("~-1 >-a ~a o :a: ~b o ~0 >-b ~c o :b: ~d o >-c ~e o :c:", "", compiler));
  }

  @Test
  public void testNullMemory() throws IOException, InterruptedException {
    var compiler = new VerbosyCompiler(1024,
        codeProviderSupplier.get(1024, false, false), false);
    assertEquals("", runCode("o \\0 /0 +0 -0 ^0 v0 \\0* /0* +0* -0* ^0* v0*", "", compiler));
  }

  @Test
  public void randomTest() throws IOException, InterruptedException {
    Random r = new Random();
    for (int i = 0 ; i < 10 ; i++) {
      CodeProvider provider = codeProviderSupplier.get(1024, false, false);

      var instructions = Stream.generate(() -> randomInstruction(r)).limit(100)
          .collect(Collectors.toList());

      File output = File.createTempFile("verbosyOutput", ".cs");
      try (var stream = new FileOutputStream(output)) {
        var codeGen = new CodeGenerator(instructions, provider, stream);
        codeGen.generateCode();
      }
      Runtime runtime = Runtime.getRuntime();
      Process p = runtime.exec("csc " + output.getAbsolutePath() + " -warn:0 -out:out.exe");
      assertEquals(0, p.waitFor());
      if (p.exitValue() != 0) {
        System.err.write(p.getInputStream().readAllBytes());
      }
    }
  }

  private Instruction randomInstruction(Random r) {
    return switch (r.nextInt(9)) {
      case 0 -> input();
      case 1 -> output();
      case 2 -> set(r.nextInt(), r.nextBoolean());
      case 3 -> add(r.nextInt(), r.nextBoolean());
      case 4 -> sub(r.nextInt(), r.nextBoolean());
      case 5 -> inc(r.nextInt(), r.nextBoolean());
      case 6 -> dec(r.nextInt(), r.nextBoolean());
      case 7 -> take(r.nextInt(), r.nextBoolean());
      case 8 -> put(r.nextInt(), r.nextBoolean());
      default -> throw new AssertionError("This shouldn't happen!");
    };
  }

}
