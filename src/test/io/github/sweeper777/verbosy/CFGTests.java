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

  @Test
  public void generatesCFGForIfThenElse() {
    var analyser = new SemanticAnalyer(
        List.of(
            add(20, false),
            add(20, false),
            goToIf0("foo"),
            add(20, false),
            add(20, false),
            goTo("bar"),
            label("foo"),
            add(20, false),
            add(20, false),
            label("bar")
        ),
        new ArrayList<>(),
        100,
        true);
    analyser.analyseSemantics();
    var cfg = analyser.getCFG();
    assertEquals(5, cfg.getBasicBlocks().size());

    assertEquals(Set.of(cfg.getBasicBlock(1), cfg.getBasicBlock(2)), cfg.getSuccessor(0));
    assertEquals(Set.of(cfg.getBasicBlock(3)), cfg.getSuccessors().get(cfg.getBasicBlock(1)));
    assertEquals(Set.of(cfg.getBasicBlock(3)), cfg.getSuccessors().get(cfg.getBasicBlock(2)));
    assertEquals(Set.of(cfg.getBasicBlock(4)), cfg.getSuccessors().get(cfg.getBasicBlock(3)));
    assertEquals(Set.of(), cfg.getSuccessors().get(cfg.getBasicBlock(4)));

    assertEquals(0, cfg.getBasicBlock(0).getStartIndex());
    assertEquals(3, cfg.getBasicBlock(0).getEndIndexExclusive());
    assertEquals(3, cfg.getBasicBlock(1).getStartIndex());
    assertEquals(6, cfg.getBasicBlock(1).getEndIndexExclusive());
    assertEquals(6, cfg.getBasicBlock(2).getStartIndex());
    assertEquals(9, cfg.getBasicBlock(2).getEndIndexExclusive());
    assertEquals(9, cfg.getBasicBlock(3).getStartIndex());
    assertEquals(10, cfg.getBasicBlock(3).getEndIndexExclusive());
    assertEquals(10, cfg.getBasicBlock(4).getStartIndex());
    assertEquals(10, cfg.getBasicBlock(4).getEndIndexExclusive());
  }

  @Test
  public void generatesCFGForConditionalBranch() {
    var analyser = new SemanticAnalyer(
        List.of(
            add(20, false),
            add(20, false),
            goToIf0("foo"),
            add(20, false),
            add(20, false),
            label("foo"),
            add(20, false),
            add(20, false)
        ),
        new ArrayList<>(),
        100,
        true);
    analyser.analyseSemantics();
    var cfg = analyser.getCFG();
    assertEquals(4, cfg.getBasicBlocks().size());

    assertEquals(Set.of(cfg.getBasicBlock(1), cfg.getBasicBlock(2)), cfg.getSuccessor(0));
    assertEquals(Set.of(cfg.getBasicBlock(2)), cfg.getSuccessors().get(cfg.getBasicBlock(1)));
    assertEquals(Set.of(cfg.getBasicBlock(3)), cfg.getSuccessors().get(cfg.getBasicBlock(2)));
    assertEquals(Set.of(), cfg.getSuccessors().get(cfg.getBasicBlock(3)));

    assertEquals(0, cfg.getBasicBlock(0).getStartIndex());
    assertEquals(3, cfg.getBasicBlock(0).getEndIndexExclusive());
    assertEquals(3, cfg.getBasicBlock(1).getStartIndex());
    assertEquals(5, cfg.getBasicBlock(1).getEndIndexExclusive());
    assertEquals(5, cfg.getBasicBlock(2).getStartIndex());
    assertEquals(8, cfg.getBasicBlock(2).getEndIndexExclusive());
    assertEquals(8, cfg.getBasicBlock(3).getStartIndex());
    assertEquals(8, cfg.getBasicBlock(3).getEndIndexExclusive());
  }

  @Test
  public void generatesCFGForWhileLoop() {
    var analyser = new SemanticAnalyer(
        List.of(
            label("foo"),
            add(20, false),
            add(20, false),
            goToIf0("bar"),
            add(20, false),
            goTo("foo"),
            label("bar")
        ),
        new ArrayList<>(),
        100,
        true);
    analyser.analyseSemantics();
    var cfg = analyser.getCFG();
    assertEquals(4, cfg.getBasicBlocks().size());

    assertEquals(Set.of(cfg.getBasicBlock(1), cfg.getBasicBlock(2)), cfg.getSuccessor(0));
    assertEquals(Set.of(cfg.getBasicBlock(0)), cfg.getSuccessors().get(cfg.getBasicBlock(1)));
    assertEquals(Set.of(cfg.getBasicBlock(3)), cfg.getSuccessors().get(cfg.getBasicBlock(2)));
    assertEquals(Set.of(), cfg.getSuccessors().get(cfg.getBasicBlock(3)));

    assertEquals(0, cfg.getBasicBlock(0).getStartIndex());
    assertEquals(4, cfg.getBasicBlock(0).getEndIndexExclusive());
    assertEquals(4, cfg.getBasicBlock(1).getStartIndex());
    assertEquals(6, cfg.getBasicBlock(1).getEndIndexExclusive());
    assertEquals(6, cfg.getBasicBlock(2).getStartIndex());
    assertEquals(7, cfg.getBasicBlock(2).getEndIndexExclusive());
    assertEquals(7, cfg.getBasicBlock(3).getStartIndex());
    assertEquals(7, cfg.getBasicBlock(3).getEndIndexExclusive());
  }

  @Test
  public void generatesCFGForHalts() {
    var analyser = new SemanticAnalyer(
        List.of(
            halt()
        ),
        new ArrayList<>(),
        100,
        true);
    analyser.analyseSemantics();
    var cfg = analyser.getCFG();
    assertEquals(1, cfg.getBasicBlocks().size());

    assertEquals(Set.of(), cfg.getSuccessor(0));

    assertEquals(0, cfg.getBasicBlock(0).getStartIndex());
    assertEquals(1, cfg.getBasicBlock(0).getEndIndexExclusive());
  }

}
