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

    public ControlFlowGraphBuilder addLine(Instruction instr) {
      cfg.sourceCode.add(instr);
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
          if (!successors.isEmpty()) {
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
          var endBlock = new BasicBlock(cfg.sourceCode.size(), cfg.sourceCode.size(), cfg);
          cfg.basicBlocks.add(endBlock);
          cfg.successors.put(endBlock, new HashSet<>());
          cfg.successors.get(cfg.basicBlocks.get(i)).add(endBlock);
        }
      }
      return cfg;
    }
  }

  }
}
