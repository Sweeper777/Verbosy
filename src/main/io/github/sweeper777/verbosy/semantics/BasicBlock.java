package io.github.sweeper777.verbosy.semantics;

import io.github.sweeper777.verbosy.syntax.Instruction;

import java.util.List;

public class BasicBlock {

  private final int startIndex;
  private final int endIndexExclusive;
  private final ControlFlowGraph cfg;

  public BasicBlock(int startIndex, int endIndexExclusive, ControlFlowGraph cfg) {
    if (startIndex > endIndexExclusive) {
      throw new IllegalArgumentException("startIndex > endIndexExclusive");
    }
    if (startIndex > cfg.getInstructions().size() || endIndexExclusive > cfg.getInstructions().size() ||
        startIndex < 0) {
      throw new IndexOutOfBoundsException("startIndex and endIndexExclusive must be within bounds!");
    }

    this.startIndex = startIndex;
    this.endIndexExclusive = endIndexExclusive;
    this.cfg = cfg;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndexExclusive() {
    return endIndexExclusive;
  }

  public ControlFlowGraph getCFG() {
    return cfg;
  }

  public boolean isEmpty() {
    return getStartIndex() == getEndIndexExclusive();
  }

  public Instruction getFirstInstruction() {
    return isEmpty() ? null : getCFG().getInstructions().get(getStartIndex());
  }

  public Instruction getLastInstruction() {
    return isEmpty() ? null : getCFG().getInstructions().get(getEndIndexExclusive() - 1);
  }

  public List<Instruction> getInstructions() {
    return cfg.getInstructions().subList(getStartIndex(), getEndIndexExclusive());
  }
}
