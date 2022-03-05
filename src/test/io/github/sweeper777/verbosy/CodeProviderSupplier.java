package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.codegen.cs.CSharpCodeProvider;
import java.io.IOException;

public interface CodeProviderSupplier {
  CSharpCodeProvider get(int memorySize, boolean readSpaceAsZero, boolean readInts) throws IOException;
}
