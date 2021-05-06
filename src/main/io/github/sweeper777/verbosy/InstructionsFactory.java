package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.VerbosyParser.InstructionContext;

public class InstructionsFactory extends VerbosyBaseListener {

  @Override
  public void exitInstruction(InstructionContext ctx) {
    System.out.println(ctx.getText());
  }
}
