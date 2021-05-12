package io.github.sweeper777.verbosy;

import static io.github.sweeper777.verbosy.TestUtils.*;
import static org.junit.Assert.assertEquals;

import io.github.sweeper777.verbosy.instructions.Instruction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Before;
import org.junit.Test;

public class SampleProgramParserTests {
  private final List<ErrorMessage> errors = new ArrayList<>();
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
}
