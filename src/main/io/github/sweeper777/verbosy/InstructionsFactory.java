package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.verbosyParser.InstructionContext;

public class InstructionsFactory extends verbosyBaseListener {

  @Override
  public void exitInstruction(InstructionContext ctx) {
    System.out.println(ctx.getText());
  }
}
