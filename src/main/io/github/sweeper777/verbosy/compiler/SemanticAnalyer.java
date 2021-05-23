package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.compiler.CompilerOutput.Type;
import io.github.sweeper777.verbosy.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.instructions.Instruction;
import io.github.sweeper777.verbosy.instructions.LabelInstruction;
import io.github.sweeper777.verbosy.instructions.ParameterPointerInstructionBase;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemanticAnalyer {
  private final List<Instruction> instructions;
  private final List<CompilerOutput> compilerOutputs;
  private final int memorySize;
  private final boolean generateWarnings;
  private Set<String> labels;

  private static final String DUPLICATE_LABEL_MSG = "Duplicate label '%s'";
  private static final String UNKNOWN_LABEL_MSG = "Unknown label '%s'";
  private static final String MEMORY_UNAVAILABLE_MSG = "Memory position %d is not available. Valid range: %d-%d";
  private static final String UNUSED_LABEL_MSG = "Unused label: %s";

  public SemanticAnalyer(
      List<Instruction> instructions,
      List<CompilerOutput> compilerOutputs, int memorySize, boolean generateWarnings) {
    this.instructions = instructions;
    this.compilerOutputs = compilerOutputs;
    this.memorySize = memorySize;
    this.generateWarnings = generateWarnings;
  }

  public void analyseSemantics() {
    labels = new HashSet<>();
    checkDuplicateLabels();
    checkInstructionsValid();
  }

  private void checkDuplicateLabels() {
    for (Instruction i : instructions) {
      if (i instanceof LabelInstruction) {
        String labelName = ((LabelInstruction)i).getLabelName();
        boolean isNewLabel = labels.add(labelName);
        if (!isNewLabel) {
          compilerOutputs.add(new CompilerOutput(
              i.getLineNo(), i.getColumnNo(),
              String.format(DUPLICATE_LABEL_MSG, labelName),
              Type.ERROR));
        }
      }
    }
  }

  private void checkInstructionsValid() {
    var usedLabels = new HashSet<String>();
    var allLabels = new HashSet<LabelInstruction>();

    for (Instruction i : instructions) {
      if (i instanceof GotoInstructionBase) {
        String labelName = ((GotoInstructionBase)i).getLabelName();
        if (!labels.contains(labelName)) {
          compilerOutputs.add(new CompilerOutput(
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
          compilerOutputs.add(new CompilerOutput(
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
        compilerOutputs.add(new CompilerOutput(
            label.getLineNo(),
            label.getColumnNo(),
            UNUSED_LABEL_MSG,
            Type.WARNING
        ));
      }
    }
  }
}
