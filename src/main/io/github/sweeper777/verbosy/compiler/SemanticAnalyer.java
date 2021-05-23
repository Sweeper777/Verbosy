package io.github.sweeper777.verbosy.compiler;

import io.github.sweeper777.verbosy.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.instructions.Instruction;
import io.github.sweeper777.verbosy.instructions.LabelInstruction;
import io.github.sweeper777.verbosy.instructions.ParameterPointerInstructionBase;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemanticAnalyer {
  private final List<Instruction> instructions;
  private final List<ErrorMessage> errorMessages;
  private final int memorySize;
  private Set<String> labels;

  private static final String DUPLICATE_LABEL_MSG = "Duplicate label '%s'";
  private static final String UNKNOWN_LABEL_MSG = "Unknown label '%s'";
  private static final String MEMORY_UNAVAILABLE_MSG = "Memory position %d is not available. Valid range: %d-%d";

  public SemanticAnalyer(
      List<Instruction> instructions,
      List<ErrorMessage> errorMessages, int memorySize) {
    this.instructions = instructions;
    this.errorMessages = errorMessages;
    this.memorySize = memorySize;
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
          errorMessages.add(new ErrorMessage(
              i.getLineNo(), i.getColumnNo(),
              String.format(DUPLICATE_LABEL_MSG, labelName)
          ));
        }
      }
    }
  }

  private void checkInstructionsValid() {
    for (Instruction i : instructions) {
      if (i instanceof GotoInstructionBase) {
        String labelName = ((GotoInstructionBase)i).getLabelName();
        if (!labels.contains(labelName)) {
          errorMessages.add(new ErrorMessage(
              i.getLineNo(), i.getColumnNo(),
              String.format(UNKNOWN_LABEL_MSG, labelName)
          ));
        }
      }

      if (i instanceof ParameterPointerInstructionBase) {
        var parameter = ((ParameterPointerInstructionBase)i).getParameter();
        if (parameter < 0 || parameter >= memorySize) {
          errorMessages.add(new ErrorMessage(
              i.getLineNo(), i.getColumnNo(),
              String.format(MEMORY_UNAVAILABLE_MSG, parameter, 0, memorySize)
          ));
        }
      }
    }
  }
}
