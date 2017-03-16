package com.verbosy.instructions.gt;

import com.verbosy.instructions.primitive.Instruction;
import com.verbosy.instructions.primitive.Label;
import com.verbosy.runtime.VerbosyRuntime;

import java.io.Serializable;
import java.util.function.Predicate;

public class GotoInstruction implements Instruction, Serializable {
    private long serialVersionUID = 8L;
    private Label goToLabel;

    public Label getGoToLabel() {
        return goToLabel;
    }

    public Predicate<VerbosyRuntime> getGoToCondition() {
        return x -> true;
    }

    @Override
    public void execute(VerbosyRuntime runtime) {

    }

    public GotoInstruction(Label goToLabel) {
        this.goToLabel = goToLabel;
    }
}
