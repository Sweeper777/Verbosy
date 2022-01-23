package io.github.sweeper777.verbosy.semantics;

import io.github.sweeper777.verbosy.syntax.instructions.GotoInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.syntax.instructions.HaltInstruction;
import io.github.sweeper777.verbosy.syntax.Instruction;
import io.github.sweeper777.verbosy.syntax.instructions.LabelInstruction;

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
  private final List<Instruction> instructions = new ArrayList<>();
  private final HashMap<BasicBlock, HashSet<BasicBlock>> successors = new HashMap<>();

  private ControlFlowGraph() {
  }

  public List<Instruction> getInstructions() {
    return instructions;
  }

  public BasicBlock getBasicBlock(int index) {
    return getBasicBlocks().get(index);
  }

  public List<BasicBlock> getBasicBlocks() {
    return basicBlocks;
  }

  public Set<BasicBlock> getSuccessor(BasicBlock basicBlock) {
    return getSuccessors().get(basicBlock);
  }

  public Set<BasicBlock> getSuccessor(int basicBlockIndex) {
    return getSuccessor(getBasicBlock(basicBlockIndex));
  }

  public HashMap<BasicBlock, HashSet<BasicBlock>> getSuccessors() {
    return successors;
  }

  public static ControlFlowGraphBuilder builder() {
    return new ControlFlowGraphBuilder();
  }

  public static class ControlFlowGraphBuilder {
    private ControlFlowGraph cfg = new ControlFlowGraph();
    private ControlFlowGraphBuilder() {}

    private int currentBlockStart = 0;
    private int currentBlockEndExclusive = 0;

    public ControlFlowGraphBuilder addInstruction(Instruction instr) {
      cfg.instructions.add(instr);
      currentBlockEndExclusive++;
      return this;
    }

    public ControlFlowGraphBuilder buildBasicBlock() {
      var block = new BasicBlock(currentBlockStart, currentBlockEndExclusive, cfg);
      cfg.basicBlocks.add(block);
      cfg.successors.put(block, new HashSet<>());
      currentBlockStart = currentBlockEndExclusive;
      return this;
    }

    public ControlFlowGraph connectedGraph() {
      cfg.basicBlocks.removeIf(BasicBlock::isEmpty);
      cfg.successors.keySet().removeIf(BasicBlock::isEmpty);
      cfg.basicBlocks.sort(Comparator.comparing(BasicBlock::getStartIndex));
      var labelLeaders = cfg.basicBlocks.stream().filter(x -> x.getFirstInstruction() instanceof LabelInstruction)
          .collect(Collectors.groupingBy(x -> ((LabelInstruction)x.getFirstInstruction()).getLabelName()));
      var basicBlockCount = cfg.basicBlocks.size();
      for (int i = 0; i < basicBlockCount; i++) {
        var block = cfg.basicBlocks.get(i);
        if (block.isEmpty()) {
          throw new AssertionError();
        }
        if (block.getLastInstruction() instanceof GotoInstructionBase) {
          var successors = labelLeaders.get(((GotoInstructionBase) block.getLastInstruction()).getLabelName());
          if (successors != null && !successors.isEmpty()) {
            cfg.successors.get(block).add(successors.get(0));
            if (block.getLastInstruction() instanceof GotoInstruction) {
              continue;
            }
          }
        }
        if (block.getLastInstruction() instanceof HaltInstruction) {
          continue;
        }
        if (i + 1 < cfg.basicBlocks.size()) {
          var nextBlock = cfg.basicBlocks.get(i + 1);
          cfg.successors.get(block).add(nextBlock);
        } else {
          var endBlock = new BasicBlock(cfg.instructions.size(), cfg.instructions.size(), cfg);
          cfg.basicBlocks.add(endBlock);
          cfg.successors.put(endBlock, new HashSet<>());
          cfg.successors.get(cfg.basicBlocks.get(i)).add(endBlock);
        }
      }
      return cfg;
    }
  }

  public HashSet<BasicBlock> findUnreachableBlocks(int startIndex) {
    if (startIndex < 0 || startIndex >= basicBlocks.size()) {
      throw new IndexOutOfBoundsException("startIndex is out of bounds!");
    }
    HashSet<BasicBlock> unreachables = new HashSet<>(basicBlocks);
    Queue<BasicBlock> queue = new ArrayDeque<>();
    unreachables.remove(basicBlocks.get(startIndex));
    queue.add(basicBlocks.get(startIndex));
    BasicBlock block;
    while ((block = queue.poll()) != null) {
      for (var successor : successors.get(block)) {
        if (unreachables.remove(successor)) {
          queue.add(successor);
        }
      }
    }
    return unreachables;
  }

  public HashSet<BasicBlock> findUnreachableBlocks() {
    return findUnreachableBlocks(0);
  }
}
