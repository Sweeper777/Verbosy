package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.compiler.SemanticAnalyer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.github.sweeper777.verbosy.TestUtils.*;
import static org.junit.Assert.assertEquals;

public class CFGTests {
  @Test
  public void generatesSimpleCFG() {
    var analyser = new SemanticAnalyer(
        List.of(
            add(20, true),
            sub(20, false),
            take(10, true),
            put(10, false)
        ),
        new ArrayList<>(),
        100,
        true);
    analyser.analyseSemantics();
    var cfg = analyser.getCFG();
    assertEquals(2, cfg.getBasicBlocks().size());
    assertEquals(Set.of(cfg.getBasicBlock(1)), cfg.getSuccessor(0));
    assertEquals(Set.of(), cfg.getSuccessors().get(cfg.getBasicBlock(1)));
    assertEquals(0, cfg.getBasicBlock(0).getStartIndex());
    assertEquals(4, cfg.getBasicBlock(0).getEndIndexExclusive());
    assertEquals(4, cfg.getBasicBlock(1).getStartIndex());
    assertEquals(4, cfg.getBasicBlock(1).getEndIndexExclusive());
  }

}
