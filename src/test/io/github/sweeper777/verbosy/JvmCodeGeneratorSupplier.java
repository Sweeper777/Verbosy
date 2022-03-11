package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.jvm.JvmCodeGenerator;

public interface JvmCodeGeneratorSupplier {
    JvmCodeGenerator get(boolean readInts, boolean readSpaceAsZero);
}
