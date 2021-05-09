package io.github.sweeper777.verbosy.instructions;

public interface ParameterPointerInstructionSupplier<T extends ParameterPointerInstructionBase>  {
  T createInstruction(int lineNo, int columnNo,
      int parameter, boolean pointer);
}
