package io.github.sweeper777.verbosy.instructions.gt;

import io.github.sweeper777.verbosy.instructions.primitive.Label;
import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;

import java.io.Serializable;
import java.util.function.Predicate;

public class GotoIf0Instruction extends GotoInstruction implements Serializable {
    private long serialVersionUID = 6L;
    public GotoIf0Instruction(Label goToLabel) {
        super(goToLabel);
    }

    @Override
    public Predicate<VerbosyRuntime> getGoToCondition() {
        return runtime -> runtime.getCurrent() != null
                && !runtime.getCurrent().getIsChar()
                && runtime.getCurrent().getIntValue() == 0;
    }
}
