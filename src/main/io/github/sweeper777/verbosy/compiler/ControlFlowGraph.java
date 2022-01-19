package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.GotoInstruction;
import io.github.sweeper777.verbosy.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.instructions.HaltInstruction;
import io.github.sweeper777.verbosy.instructions.Instruction;
import io.github.sweeper777.verbosy.instructions.LabelInstruction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class ControlFlowGraph {
  private final List<BasicBlock> basicBlocks = new ArrayList<>();
  private final List<Instruction> sourceCode = new ArrayList<>();
  private final HashMap<BasicBlock, HashSet<BasicBlock>> successors = new HashMap<>();

  private ControlFlowGraph() {
  }

  public List<Instruction> getSourceCode() {
    return sourceCode;
  }
}
