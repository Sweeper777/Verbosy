package com.verbosy.instructions;

import com.verbosy.compiler.VerbosyParameter;
import com.verbosy.compiler.VerbosyRuntime;
import com.verbosy.compiler.VerbosyValue;
import com.verbosy.instructions.primitive.ParameterInstruction;

public class AddInstruction implements ParameterInstruction {
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

        if (runtime.getMemory()[param.getValue().getIntValue()] == null) {
            return;
        }

        if (runtime.getMemory()[param.getValue().getIntValue()].getIsChar()) {
            return;
        }

        if (runtime.getCurrent().getIsChar()) {
            return;
        }

        int current = runtime.getCurrent().getIntValue();
        int memory;

        if (param.isPointer()) {
            int pointTo = runtime.getMemory()[param.getValue().getIntValue()].getIntValue();
            if (pointTo >= runtime.getMemory().length) {
                return;
            }

            if (runtime.getMemory()[pointTo] == null) {
                return;
            }

            if (runtime.getMemory()[pointTo].getIsChar()) {
                return;
            }

            memory = runtime.getMemory()[pointTo].getIntValue();
        } else {
            memory = runtime.getMemory()[param.getValue().getIntValue()].getIntValue();
        }

        runtime.setCurrent(new VerbosyValue(current + memory));
    }

    public AddInstruction(VerbosyParameter param) {
        this.param = param;
    }
}
