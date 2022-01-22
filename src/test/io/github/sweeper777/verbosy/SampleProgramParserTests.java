package io.github.sweeper777.verbosy;

import static io.github.sweeper777.verbosy.TestUtils.*;
import static org.junit.Assert.assertEquals;

import io.github.sweeper777.verbosy.syntax.Instruction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Before;
import org.junit.Test;

public class SampleProgramParserTests {
  private final List<CompilerOutput> errors = new ArrayList<>();
  private final List<Instruction> instructions = new ArrayList<>();

  @Before
  public void init() {
    errors.clear();
    instructions.clear();
  }

  @Test
  public void testAddition() throws IOException {
    var stream = CharStreams.fromStream(Objects
        .requireNonNull(SampleProgramParserTests.class.getResourceAsStream("/tests/addition.vp")));
    parseCharStream(stream, instructions, errors);
    assertEquals(List.of(
        input(), put(0, false), input(), add(0, false), output()
    ), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testDuplicateLabel() throws IOException {
    var stream = CharStreams.fromStream(Objects
        .requireNonNull(SampleProgramParserTests.class.getResourceAsStream("/tests/duplicatelabel.vp")));
    parseCharStream(stream, instructions, errors);
    assertEquals(List.of(
        label("a"), goTo("b"), label("a"), label("b")
    ), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testLongerProgramSeparatedBySpaces() throws IOException {
    var stream = CharStreams.fromStream(Objects
        .requireNonNull(SampleProgramParserTests.class.getResourceAsStream("/tests/reverse.vp")));
    parseCharStream(stream, instructions, errors);
    assertEquals(List.of(
        set(0, false),
        put(14, false),
        label("a"),
        inc(14, false),
        input(),
        goToIf0("b"),
        put(14, true),
        goTo("a"),
        label("b"),
        dec(14, false),
        label("c"),
        take(14, true),
        output(),
        dec(14, false),
        goToIf0("a"),
        goTo("c")
    ), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testComments() throws IOException {
    var stream = CharStreams.fromStream(Objects
        .requireNonNull(SampleProgramParserTests.class.getResourceAsStream("/tests/additionwithcomments.vp")));
    parseCharStream(stream, instructions, errors);
    assertEquals(List.of(
        input(), put(0, false), input(), add(0, false), output()
    ), instructions);
    assertEquals(List.of(), errors);
  }
}
