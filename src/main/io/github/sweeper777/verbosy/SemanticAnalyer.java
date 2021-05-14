package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.instructions.GotoInstructionBase;
import io.github.sweeper777.verbosy.instructions.Instruction;
import io.github.sweeper777.verbosy.instructions.LabelInstruction;
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

  public SemanticAnalyer(
      List<Instruction> instructions,
      List<ErrorMessage> errorMessages, int memorySize) {
    this.instructions = instructions;
    this.errorMessages = errorMessages;
    this.memorySize = memorySize;
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

  private void checkUnknownLabels() {
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
    }
  }
}
