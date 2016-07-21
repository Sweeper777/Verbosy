package com.verbosy.instructions;

import com.verbosy.runtime.VerbosyParameter;
import com.verbosy.runtime.VerbosyRuntime;
import com.verbosy.runtime.VerbosyValue;
import com.verbosy.instructions.primitive.ParameterInstruction;

public class TakeInstruction implements ParameterInstruction {
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
