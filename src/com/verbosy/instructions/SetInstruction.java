package com.verbosy.instructions;

import com.verbosy.runtime.VerbosyParameter;
import com.verbosy.runtime.VerbosyRuntime;
import com.verbosy.instructions.primitive.ParameterInstruction;

public class SetInstruction implements ParameterInstruction {
    private VerbosyParameter param;

    @Override
    public VerbosyParameter getParameter() {
        return param;
    }

    @Override
    public void execute(VerbosyRuntime runtime) {
        runtime.setCurrent(param.getValue());
    }

    public SetInstruction(VerbosyParameter param) {
        this.param = param;
    }
}
