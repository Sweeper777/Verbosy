package io.github.sweeper777.verbosy.compiler;

import java.util.BitSet;
import java.util.List;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

public class ListErrorListener implements ANTLRErrorListener {

  private final List<CompilerOutput> compilerOutputList;

  public ListErrorListener(List<CompilerOutput> compilerOutputList) {
    this.compilerOutputList = compilerOutputList;
  }

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s,
      RecognitionException e) {
    compilerOutputList.add(new CompilerOutput(i, i1, s));
  }

  @Override
  public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet,
      ATNConfigSet atnConfigSet) {

  }

  @Override
  public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet,
      ATNConfigSet atnConfigSet) {

  }

  @Override
  public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2,
      ATNConfigSet atnConfigSet) {

  }
}
