package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyParameter;
import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.compiler.VerbosyValue;

public class PutInstruction implements ParameterInstruction {

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
        } else {
            runtime.getMemory()[param.getValue().getIntValue()] = runtime.getCurrent();
        }
    }

    public PutInstruction(VerbosyParameter param) {
        this.param = param;
    }
}
