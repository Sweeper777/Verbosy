package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyParameter;
import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.compiler.VerbosyValue;
import com.verbosy.instructions.primitive.ParameterInstruction;

public class DecInstruction implements ParameterInstruction {
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

        VerbosyValue memory;
        if (param.isPointer()) {
            if (runtime.getMemory()[param.getValue().getIntValue()].getIsChar()) {
                return;
            }

            int pointTo = runtime.getMemory()[param.getValue().getIntValue()].getIntValue();
            if (pointTo >= runtime.getMemory().length) {
                return;
            }

            if (runtime.getMemory()[pointTo] == null) {
                return;
            }

            memory = runtime.getMemory()[pointTo];
            memory = memory.increment(-1);
            runtime.getMemory()[pointTo] = memory;
            runtime.setCurrent(memory);
        } else {
            memory = runtime.getMemory()[param.getValue().getIntValue()];
            memory = memory.increment(-1);
            runtime.getMemory()[param.getValue().getIntValue()] = memory;
            runtime.setCurrent(memory);
        }
    }

    public DecInstruction(VerbosyParameter param) {
        this.param = param;
    }
}
