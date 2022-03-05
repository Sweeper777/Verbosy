package io.github.sweeper777.verbosy.codegen.cs;

public interface CSharpCodeProvider {
  String getHeader();
  String getFooter();
  String getInputInstruction();
  String getOutputInstruction();
  String getSetInstruction(int value, boolean isChar);
  String getAddInstruction(int parameter, boolean isPointer);
  String getSubInstruction(int parameter, boolean isPointer);
  String getIncInstruction(int parameter, boolean isPointer);
  String getDecInstruction(int parameter, boolean isPointer);
  String getPutInstruction(int parameter, boolean isPointer);
  String getTakeInstruction(int parameter, boolean isPointer);
  String getLabel(String name);
  String getGoto(String label);
  String getGotoIf0(String label);
  String getGotoIfNeg(String label);
}
