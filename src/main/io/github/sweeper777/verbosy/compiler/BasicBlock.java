package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.Instruction;

public class BasicBlock {

  private final int startIndex;
  private final int endIndexExclusive;
  private final ControlFlowGraph cfg;

  public BasicBlock(int startIndex, int endIndexExclusive, ControlFlowGraph cfg) {
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
    return isEmpty() ? null : getCFG().getSourceCode().get(getStartIndex());
  }

  public Instruction getLastInstruction() {
    return isEmpty() ? null : getCFG().getSourceCode().get(getEndIndexExclusive() - 1);
  }
}
