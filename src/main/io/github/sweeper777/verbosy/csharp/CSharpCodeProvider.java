package io.github.sweeper777.verbosy.csharp;

import io.github.sweeper777.verbosy.CodeProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class CSharpCodeProvider implements CodeProvider {

  private final HashMap<String, String> templates = new HashMap<>();
  private final int memorySize;

  public CSharpCodeProvider(int memorySize) throws IOException {
    this.memorySize = memorySize;

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        Objects.requireNonNull(
            CSharpCodeProvider.class.getResourceAsStream("csharp-code-templates.txt"))
    ))) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("###")) {
          String key = line.substring(3);
          StringBuilder sb = new StringBuilder();
          while ((line = reader.readLine()) != null && !line.startsWith("###")) {
            sb.append(line);
          }
          templates.put(key, sb.toString());
        }
      }
    }
  }

}
