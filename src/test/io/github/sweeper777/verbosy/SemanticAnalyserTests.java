package io.github.sweeper777.verbosy;

import static io.github.sweeper777.verbosy.TestUtils.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SemanticAnalyserTests {

  @Test
  public void testSemanticallyCorrectProgram() {
    var errors = new ArrayList<ErrorMessage>();
    var analyser = new SemanticAnalyer(
        List.of(
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
        ),
        errors,
        20
    );
    analyser.analyseSemantics();
    assertEquals(List.of(), errors);
  }

  @Test
  public void testDuplicateLabels() {
    var errors = new ArrayList<ErrorMessage>();
    var analyser = new SemanticAnalyer(
        List.of(
            label("a"),
            label("a")
        ),
        errors,
        20
    );
    analyser.analyseSemantics();
    assertEquals(1, errors.size());
  }

}
