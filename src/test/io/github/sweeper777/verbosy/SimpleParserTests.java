package io.github.sweeper777.verbosy;

import static io.github.sweeper777.verbosy.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.github.sweeper777.verbosy.syntax.Instruction;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Before;
import org.junit.Test;

public class SimpleParserTests {
  private final List<Diagnostic> errors = new ArrayList<>();
  private final List<Instruction> instructions = new ArrayList<>();

  @Before
  public void init() {
    errors.clear();
    instructions.clear();
  }

  @Test
  public void testParseInputInstruction() {
    parseCharStream(CharStreams.fromString("i"), instructions, errors);
    assertEquals(List.of(input()), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseOutputInstruction() {
    parseCharStream(CharStreams.fromString("o"), instructions, errors);
    assertEquals(List.of(output()), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseHaltInstruction() {
    parseCharStream(CharStreams.fromString("x"), instructions, errors);
    assertEquals(List.of(halt()), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseSetPositiveNumberInstruction() {
    parseCharStream(CharStreams.fromString("~1"), instructions, errors);
    assertEquals(List.of(set(1, false)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseSetNegativeNumberInstruction() {
    parseCharStream(CharStreams.fromString("~-1"), instructions, errors);
    assertEquals(List.of(set(-1, false)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseSetNumberOutOfRangeInstruction() {
    parseCharStream(CharStreams.fromString("~123456789123456789"), instructions, errors);
    assertNotEquals(0, errors.size());
  }

  @Test
  public void testParseSetCharacterInstruction() {
    parseCharStream(CharStreams.fromString("~a"), instructions, errors);
    assertEquals(List.of(set('a', true)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseSetInstructionCharacterInstruction() {
    parseCharStream(CharStreams.fromString("~\\"), instructions, errors);
    assertEquals(List.of(set('\\', true)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseSetHexEscapeInstruction() {
    parseCharStream(CharStreams.fromString("~\\123"), instructions, errors);
    assertEquals(List.of(set(0x123, true)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseSetInvalidHexEscapeInstruction() {
    parseCharStream(CharStreams.fromString("~\\abcdefabcdefabcdef"), instructions, errors);
    assertNotEquals(0, errors.size());
  }

  @Test
  public void testParseLoneSetInstruction() {
    parseCharStream(CharStreams.fromString("~"), instructions, errors);
    assertNotEquals(0, errors.size());
  }

  @Test
  public void testParseAddInstruction() {
    parseCharStream(CharStreams.fromString("+1"), instructions, errors);
    assertEquals(List.of(add(1, false)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseAddWithPointerInstruction() {
    parseCharStream(CharStreams.fromString("+1*"), instructions, errors);
    assertEquals(List.of(add(1, true)), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseAddWithNegativeParamInstruction() {
    parseCharStream(CharStreams.fromString("+-1"), instructions, errors);
    assertNotEquals(0, errors.size());
  }

  @Test
  public void testParseLoneAddInstruction() {
    parseCharStream(CharStreams.fromString("+"), instructions, errors);
    assertNotEquals(0, errors.size());
  }

  @Test
  public void testParseSingleLineComments() {
    parseCharStream(CharStreams.fromString("+1* //1 hello +1"
        + "\n-1"), instructions, errors);
    assertEquals(List.of(
        add(1, true),
        sub(1, false)
    ), instructions);
    assertEquals(List.of(), errors);
  }

  @Test
  public void testParseMultiLineComments() {
    parseCharStream(CharStreams.fromString("/1 /* +1 */ \\1"), instructions, errors);
    assertEquals(List.of(
        put(1, false),
        take(1, false)
    ), instructions);
    assertEquals(List.of(), errors);
  }


  @Test
  public void testParseNestedComments() {
    parseCharStream(CharStreams.fromString("/1 /* +1 /* -1 */ */ \\1"), instructions, errors);
    assertEquals(List.of(
        put(1, false),
        take(1, false)
    ), instructions);
    assertEquals(List.of(), errors);
  }
}