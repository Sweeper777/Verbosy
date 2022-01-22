package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.CodeProvider;
import java.io.IOException;

public interface CodeProviderSupplier {
  CodeProvider get(int memorySize, boolean readSpaceAsZero, boolean readInts) throws IOException;
}
