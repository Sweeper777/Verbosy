package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyRuntime;

import java.util.function.Predicate;

public class GotoInstruction implements Instruction {
    private Label goToLabel;

    public Label getGoToLabel() {
        return goToLabel;
    }

    public Predicate<VerbosyRuntime> getGoToCondition() {
        return x -> true;
    }

    @Override
    public void execute(VerbosyRuntime runtime) {
        // TODO jump to label
    }

    public GotoInstruction(Label goToLabel) {
        this.goToLabel = goToLabel;
    }
}
