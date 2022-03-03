package io.github.sweeper777.verbosy.syntax.instructions;

import io.github.sweeper777.verbosy.codegen.cs.CodeProvider;
import io.github.sweeper777.verbosy.syntax.Instruction;
import io.github.sweeper777.verbosy.syntax.VerbosyValue;

public class SetInstruction implements Instruction {

  private final int lineNo;
  private final int columnNo;
  private final VerbosyValue parameter;

  public int getLineNo() {
    return lineNo;
  }

  public int getColumnNo() {
    return columnNo;
  }

  public VerbosyValue getParameter() {
    return parameter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SetInstruction that = (SetInstruction) o;

    return parameter.equals(that.parameter);
  }

  @Override
  public int hashCode() {
    return parameter.hashCode();
  }

  public SetInstruction(int lineNo, int columnNo,
      VerbosyValue parameter) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
    this.parameter = parameter;
  }

  @Override
  public String toString() {
    return "SET " + getParameter();
  }

  @Override
  public String getCode(CodeProvider provider) {
    return provider.getSetInstruction(parameter.getValue(), parameter.isChar());
  }
}
