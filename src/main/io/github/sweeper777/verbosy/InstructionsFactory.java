package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.VerbosyParser.Set_instructionContext;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

public class InstructionsFactory extends VerbosyBaseListener {

  private final Parser p;

  public InstructionsFactory(Parser p) {
    this.p = p;
  }

  @Override
  public void exitSet_instruction(Set_instructionContext ctx) {
//    System.out.println("Found Set Instruction");
//    VerbosyParser.Set_character_argumentContext charArg = ctx.set_character_argument();
//    VerbosyParser.Set_integer_argumentContext intArg = ctx.set_integer_argument();
//    if (charArg != null) {
//      System.out.println("Character argument: " + charArg.getText());
//    } else if (intArg != null) {
//      System.out.println("Integer argument: " + intArg.getText());
//    }
  }

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    System.out.println(ctx.toInfoString(p));
  }
}
