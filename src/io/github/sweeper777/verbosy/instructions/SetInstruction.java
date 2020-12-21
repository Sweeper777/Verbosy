package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.runtime.VerbosyParameter;
import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;
import io.github.sweeper777.verbosy.instructions.primitive.ParameterInstruction;

import java.io.Serializable;

public class SetInstruction implements ParameterInstruction, Serializable {

    private long serialVersionUID = 2L;
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
