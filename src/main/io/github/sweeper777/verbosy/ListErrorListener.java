package io.github.sweeper777.verbosy;

import io.github.sweeper777.verbosy.Diagnostic.Type;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;
import java.util.List;

public class ListErrorListener implements ANTLRErrorListener {

  private final List<Diagnostic> diagnosticList;

  public ListErrorListener(List<Diagnostic> diagnosticList) {
    this.diagnosticList = diagnosticList;
  }

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s,
      RecognitionException e) {
    diagnosticList.add(new Diagnostic(i, i1, s, Type.ERROR));
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
