package io.github.sweeper777.verbosy.semantics;

import io.github.sweeper777.verbosy.Diagnostic;
import io.github.sweeper777.verbosy.Diagnostic.Type;
import io.github.sweeper777.verbosy.syntax.Instruction;
import io.github.sweeper777.verbosy.syntax.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.syntax.instructions.HaltInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.LabelInstruction;
import io.github.sweeper777.verbosy.syntax.instructions.ParameterPointerInstructionBase;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SemanticAnalyer {
  private final List<Instruction> instructions;
  private final List<Diagnostic> diagnostics;
  private final int memorySize;
  private final boolean generateWarnings;
  private Set<String> labels;
  private Set<String> usedLabels;
  private ControlFlowGraph cfg;
  private Collection<BasicBlock> unreachableBlocks;

  private static final String DUPLICATE_LABEL_MSG = "Duplicate label '%s'";
  private static final String UNKNOWN_LABEL_MSG = "Unknown label '%s'";
  private static final String MEMORY_UNAVAILABLE_MSG = "Memory position %d is not available. Valid range: %d-%d";
  private static final String UNUSED_LABEL_MSG = "Unused label: %s";
  private static final String UNREACHABLE_CODE = "Unreachable code";
  private static final String REDUNDANT_HALT = "Redundant halt instruction";

  public SemanticAnalyer(
      List<Instruction> instructions,
      List<Diagnostic> diagnostics, int memorySize, boolean generateWarnings) {
    this.instructions = instructions;
    this.diagnostics = diagnostics;
    this.memorySize = memorySize;
    this.generateWarnings = generateWarnings;
  }

  public void analyseSemantics() {
    labels = new HashSet<>();
    checkDuplicateLabels();
    checkInstructionsValid();
    findUnreachableCode();
    findRedundantHalt();
  }

  private void checkDuplicateLabels() {
    for (Instruction i : instructions) {
      if (i instanceof LabelInstruction) {
        String labelName = ((LabelInstruction)i).getLabelName();
        boolean isNewLabel = labels.add(labelName);
        if (!isNewLabel) {
          diagnostics.add(new Diagnostic(
              i.getLineNo(), i.getColumnNo(),
              String.format(DUPLICATE_LABEL_MSG, labelName),
              Type.ERROR));
        }
      }
    }
  }

  private void checkInstructionsValid() {
    usedLabels = new HashSet<String>();
    var allLabels = new HashSet<LabelInstruction>();

    for (Instruction i : instructions) {
      if (i instanceof GotoInstructionBase) {
        String labelName = ((GotoInstructionBase)i).getLabelName();
        if (!labels.contains(labelName)) {
          diagnostics.add(new Diagnostic(
              i.getLineNo(), i.getColumnNo(),
              String.format(UNKNOWN_LABEL_MSG, labelName),
              Type.ERROR));
        } else {
          usedLabels.add(labelName);
        }
      }

      if (generateWarnings && i instanceof LabelInstruction) {
        allLabels.add((LabelInstruction)i);
      }

      if (i instanceof ParameterPointerInstructionBase) {
        var parameter = ((ParameterPointerInstructionBase)i).getParameter();
        if (parameter < 0 || parameter >= memorySize) {
          diagnostics.add(new Diagnostic(
              i.getLineNo(), i.getColumnNo(),
              String.format(MEMORY_UNAVAILABLE_MSG, parameter, 0, memorySize),
              Type.ERROR));
        }
      }
    }

    if (generateWarnings) {
      var unusedLabels = new HashSet<>(allLabels);
      unusedLabels.removeIf(x -> usedLabels.contains(x.getLabelName()));
      for (var label : unusedLabels) {
        diagnostics.add(new Diagnostic(
            label.getLineNo(),
            label.getColumnNo(),
            String.format(UNUSED_LABEL_MSG, label.getLabelName()),
            Type.WARNING
        ));
      }
    }
  }

  private void findRedundantHalt() {
    if (!generateWarnings) {
      return;
    }
    int num=instructions.size()-1;
    IntStream.rangeClosed(0, num).mapToObj(i->instructions.get(num-i))
        .dropWhile(x -> x instanceof LabelInstruction)
        .findFirst()
        .filter(x -> x instanceof HaltInstruction)
        .ifPresent(x -> diagnostics.add(new Diagnostic(
            x.getLineNo(),
            x.getColumnNo(),
            REDUNDANT_HALT,
            Type.WARNING
        )));
  }

  private void findUnreachableCode() {
    if (!generateWarnings) {
      return;
    }

    cfg = generateCFG();
    var unreachableBlocks = cfg.findUnreachableBlocks();
    for (var block : unreachableBlocks) {
      if (block.isEmpty()) continue;
      diagnostics.add(new Diagnostic(
          block.getFirstInstruction().getLineNo(),
          block.getFirstInstruction().getColumnNo(),
          UNREACHABLE_CODE,
          Type.WARNING
      ));
    }
  }

  private ControlFlowGraph generateCFG() {
    var builder = ControlFlowGraph.builder();
    for (var instruction : instructions) {
      if (instruction instanceof LabelInstruction && usedLabels.contains(((LabelInstruction) instruction).getLabelName())) {
        builder
            .buildBasicBlock()
            .addInstruction(instruction);
      } else if (instruction instanceof GotoInstructionBase || instruction instanceof HaltInstruction) {
        builder
            .addInstruction(instruction)
            .buildBasicBlock();
      } else {
        builder.addInstruction(instruction);
      }
    }
    builder.buildBasicBlock();
    return builder.connectedGraph();
  }

  public ControlFlowGraph getCFG() {
    return cfg;
  }

  public Collection<BasicBlock> getUnreachableBlocks() {
    return unreachableBlocks;
  }
}
