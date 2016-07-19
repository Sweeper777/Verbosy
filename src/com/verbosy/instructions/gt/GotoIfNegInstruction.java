package com.verbosy.instructions.gt;

import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.instructions.primitive.Label;

import java.util.function.Predicate;

public class GotoIfNegInstruction extends GotoInstruction {
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
