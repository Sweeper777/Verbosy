package io.github.sweeper777.instructions.gt;

import io.github.sweeper777.instructions.primitive.Instruction;
import io.github.sweeper777.instructions.primitive.Label;
import io.github.sweeper777.runtime.VerbosyRuntime;

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
