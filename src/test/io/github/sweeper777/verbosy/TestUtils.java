package io.github.sweeper777.verbosy;

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
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TestUtils {
  public static void parseCharStream(CharStream charStream, List<Instruction> instructions, List<ErrorMessage> errors) {
    VerbosyLexer lexer = new VerbosyLexer(charStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    VerbosyParser parser = new VerbosyParser(tokenStream);
    parser.getErrorListeners().clear();
    parser.addErrorListener(new ListErrorListener(errors));
    InstructionsFactory factory = new InstructionsFactory(errors);
    ParseTreeWalker.DEFAULT.walk(factory, parser.compilationUnit());
    instructions.addAll(factory.getParsedInstructions());
  }

}
