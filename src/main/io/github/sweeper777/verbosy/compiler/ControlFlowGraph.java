package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControlFlowGraph {
  private final List<BasicBlock> basicBlocks = new ArrayList<>();
  private final List<Instruction> sourceCode;
  private final HashMap<BasicBlock, List<BasicBlock>> successors = new HashMap<>();

  public ControlFlowGraph(List<Instruction> code) {
    this.sourceCode = code;
  }

  public List<Instruction> getSourceCode() {
    return sourceCode;
  }
}
