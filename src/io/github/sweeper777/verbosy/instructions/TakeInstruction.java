package io.github.sweeper777.verbosy.instructions;

import io.github.sweeper777.verbosy.runtime.VerbosyParameter;
import io.github.sweeper777.verbosy.runtime.VerbosyRuntime;
import io.github.sweeper777.verbosy.runtime.VerbosyValue;
import io.github.sweeper777.verbosy.instructions.primitive.ParameterInstruction;

import java.io.Serializable;

public class TakeInstruction implements ParameterInstruction, Serializable {
    private long serialVersionUID = 15L;
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

        if (runtime.getMemory()[param.getValue().getIntValue()] == null) {
            return;
        }

        if (param.isPointer()) {
            VerbosyValue pointTo = runtime.getMemory()[param.getValue().getIntValue()];
            if (pointTo == null) {
                return;
            }

            if (pointTo.getIsChar()) {
                return;
            }

            if (pointTo.getIntValue() >= runtime.getMemory().length) {
                return;
            }

            if (runtime.getMemory()[pointTo.getIntValue()] == null) {
                return;
            }

            runtime.setCurrent(runtime.getMemory()[pointTo.getIntValue()]);
        } else {
            runtime.setCurrent(runtime.getMemory()[param.getValue().getIntValue()]);
        }
    }

    public TakeInstruction(VerbosyParameter param) {
        this.param = param;
    }
}
