package io.github.sweeper777.verbosy;

import static org.junit.Assert.assertEquals;

import io.github.sweeper777.verbosy.compiler.CompilerOutput;
import io.github.sweeper777.verbosy.compiler.ListErrorListener;
import io.github.sweeper777.verbosy.compiler.VerbosyCompiler;
import io.github.sweeper777.verbosy.compiler.VerbosyValue;
import io.github.sweeper777.verbosy.instructions.AddInstruction;
import io.github.sweeper777.verbosy.instructions.DecInstruction;
import io.github.sweeper777.verbosy.instructions.GotoIf0Instruction;
import io.github.sweeper777.verbosy.instructions.GotoIfNegInstruction;
import io.github.sweeper777.verbosy.instructions.GotoInstruction;
import io.github.sweeper777.verbosy.instructions.IncInstruction;
import io.github.sweeper777.verbosy.instructions.InputInstruction;
import io.github.sweeper777.verbosy.instructions.Instruction;
import io.github.sweeper777.verbosy.instructions.InstructionsFactory;
import io.github.sweeper777.verbosy.instructions.LabelInstruction;
import io.github.sweeper777.verbosy.instructions.OutputInstruction;
import io.github.sweeper777.verbosy.instructions.PutInstruction;
import io.github.sweeper777.verbosy.instructions.SetInstruction;
import io.github.sweeper777.verbosy.instructions.SubInstruction;
import io.github.sweeper777.verbosy.instructions.TakeInstruction;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TestUtils {
  public static void parseCharStream(CharStream charStream, List<Instruction> instructions, List<CompilerOutput> errors) {
    VerbosyLexer lexer = new VerbosyLexer(charStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    VerbosyParser parser = new VerbosyParser(tokenStream);
    parser.getErrorListeners().clear();
    parser.addErrorListener(new ListErrorListener(errors));
    InstructionsFactory factory = new InstructionsFactory(errors);
    ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
    instructions.addAll(factory.getParsedInstructions());
  }

  public static String runCode(String code, String input, VerbosyCompiler compiler)
      throws IOException, InterruptedException {
    compiler.compile(CharStreams.fromString(code), "out.exe");
    assertEquals(0, compiler.getCompilerOutputs().size());
    var process = Runtime.getRuntime().exec("mono out.exe");
    if (input != null) {
      process.getOutputStream().write(input.getBytes(StandardCharsets.UTF_8));
      process.getOutputStream().close();
    }
    process.waitFor();
    byte[] outputBytes = process.getInputStream().readAllBytes();
    return new String(outputBytes, StandardCharsets.UTF_8);
  }

  public static InputInstruction input() {
    return new InputInstruction(0, 0);
  }

  public static OutputInstruction output() {
    return new OutputInstruction(0, 0);
  }

  public static SetInstruction set(int val, boolean isChar) {
    return new SetInstruction(0, 0, new VerbosyValue(val, isChar));
  }

  public static AddInstruction add(int parameter, boolean isPointer) {
    return new AddInstruction(0, 0, parameter, isPointer);
  }

  public static SubInstruction sub(int parameter, boolean isPointer) {
    return new SubInstruction(0, 0, parameter, isPointer);
  }

  public static IncInstruction inc(int parameter, boolean isPointer) {
    return new IncInstruction(0, 0, parameter, isPointer);
  }

  public static DecInstruction dec(int parameter, boolean isPointer) {
    return new DecInstruction(0, 0, parameter, isPointer);
  }

  public static TakeInstruction take(int parameter, boolean isPointer) {
    return new TakeInstruction(0, 0, parameter, isPointer);
  }

  public static PutInstruction put(int parameter, boolean isPointer) {
    return new PutInstruction(0, 0, parameter, isPointer);
  }

  public static LabelInstruction label(String name) {
    return new LabelInstruction(0, 0, name);
  }

  public static GotoInstruction goTo(String label) {
    return new GotoInstruction(0, 0, label);
  }

  public static GotoIf0Instruction goToIf0(String label) {
    return new GotoIf0Instruction(0, 0, label);
  }

  public static GotoIfNegInstruction goToIfNeg(String label) {
    return new GotoIfNegInstruction(0, 0, label);
  }
}
