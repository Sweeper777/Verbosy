package io.github.sweeper777.verbosy.instructions.gt;

import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;
import io.github.sweeper777.verbosy.instructions.primitive.Label;

import java.io.Serializable;
import java.util.function.Predicate;

public class GotoIfNegInstruction extends GotoInstruction implements Serializable {
    private long serialVersionUID = 7L;
    public GotoIfNegInstruction(Label goToLabel) {
        super(goToLabel);
    }

    @Override
    public Predicate<VerbosyRuntime> getGoToCondition() {
        return runtime -> runtime.getCurrent() != null
                && !runtime.getCurrent().getIsChar()
                && runtime.getCurrent().getIntValue() < 0;
    }
}
