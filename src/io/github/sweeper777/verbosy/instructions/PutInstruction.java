package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.runtime.VerbosyParameter;
import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;
import io.github.sweeper777.verbosy.runtime.VerbosyValue;
import io.github.sweeper777.verbosy.instructions.primitive.ParameterInstruction;

import java.io.Serializable;

public class PutInstruction implements ParameterInstruction, Serializable {
    private long serialVersionUID = 13L;

    private VerbosyParameter param;

    @Override
    public VerbosyParameter getParameter() {
        return param;
    }

    @Override
    public void execute(VerbosyRuntime runtime) {
        if (param.getValue().getIsChar()) {
            return;
        }

        if (param.getValue().getIntValue() >= runtime.getMemory().length) {
            return;
        }

        if (runtime.getCurrent() == null) {
            return;
        }

        if (param.isPointer()) {
            VerbosyValue pointTo = runtime.getMemory()[param.getValue().getIntValue()];
            if (pointTo == null) {
                return;
            }

            if (pointTo.getIntValue() >= runtime.getMemory().length) {
                return;
            }
            runtime.getMemory()[pointTo.getIntValue()] = runtime.getCurrent();
        } else {
            runtime.getMemory()[param.getValue().getIntValue()] = runtime.getCurrent();
        }
    }

    public PutInstruction(VerbosyParameter param) {
        this.param = param;
    }
}
