package io.github.sweeper777.verbosy.csharp;

import io.github.sweeper777.verbosy.compiler.CodeProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class CSharpDictCodeProvider implements CodeProvider {
  private final HashMap<String, String> templates = new HashMap<>();
  private final boolean readSpaceAsZero;
  private final boolean readInts;

  public CSharpDictCodeProvider(boolean readSpaceAsZero, boolean readInts) throws IOException {
    this.readSpaceAsZero = readSpaceAsZero;
    this.readInts = readInts;

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        Objects.requireNonNull(
            CSharpArrayCodeProvider.class.getResourceAsStream("/csharp-dict-code-templates.txt"))
    ))) {
      String line;
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

  @Override
  public String getHeader() {
    StringBuilder sb = new StringBuilder();
    sb.append(templates.get("header"));
    if (readInts) {
      sb.append(templates.get("readInts"));
    }
    if (readSpaceAsZero) {
      sb.append(templates.get("readSpaceAsZero"));
    }
    sb.append(templates.get("header2"));
    return sb.toString();
  }

  @Override
  public String getFooter() {
    return templates.get("footer");
  }

  @Override
  public String getInputInstruction() {
    return templates.get("input");
  }

  @Override
  public String getOutputInstruction() {
    return templates.get("output");
  }

  @Override
  public String getSetInstruction(int value, boolean isChar) {
    if (isChar) {
      return String.format(templates.get("setChar"), value);
    } else {
      return String.format(templates.get("setInt"), value);
    }
  }

  @Override
  public String getAddInstruction(int parameter, boolean isPointer) {
    return String.format(templates.get("add"), parameter, isPointer);
  }

  @Override
  public String getSubInstruction(int parameter, boolean isPointer) {
    return String.format(templates.get("sub"), parameter, isPointer);
  }

  @Override
  public String getIncInstruction(int parameter, boolean isPointer) {
    return String.format(templates.get("inc"), parameter, isPointer);
  }

  @Override
  public String getDecInstruction(int parameter, boolean isPointer) {
    return String.format(templates.get("dec"), parameter, isPointer);
  }

  @Override
  public String getPutInstruction(int parameter, boolean isPointer) {
    return String.format(templates.get("put"), parameter, isPointer);
  }

  @Override
  public String getTakeInstruction(int parameter, boolean isPointer) {
    return String.format(templates.get("take"), parameter, isPointer);
  }

  @Override
  public String getLabel(String name) {
    return String.format(templates.get("label"), name);
  }

  @Override
  public String getGoto(String label) {
    return String.format(templates.get("goto"), label);
  }

  @Override
  public String getGotoIf0(String label) {
    return String.format(templates.get("gotoIfZero"), label);
  }

  @Override
  public String getGotoIfNeg(String label) {
    return String.format(templates.get("gotoIfNeg"), label);
  }
}
