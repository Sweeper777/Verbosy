### New Instructions

If you want to add a new instruction to this language, you can do this:

First, create your instruction class. 

- If your instruction needs a parameter, implement `ParameterInstruction`. Please also provide a constructor that takes a `VerbosyParameter`.
- If your instruction is a kind of goto instruction, inherit `GotoInstruction`. Please also provide a constructor that takes a `Label`.
- If neither of those applies, implement `Instruction`. Please make sure there is a parameterless constructor (whether implicit or explicit) in that class.

You can refer to existing implementations of instructions.

Then, you need to add a key-value pair to the `instructionMap` in `CompilerUtility.java`. Use the pattern of your instruction as the key, and your instruction class as the value, just like the existing instructions.

You also need to add a key-value pair to the `instructionSubstringStrategy` which is also in `CompilerUtility.java`. Use your instruction class as the key, and the number of characters that your instruction has as the value.

If you need to do some special stuff when compiling your instruction, please handle it in the `compile` method.

After that, send me a pull request! But please first make sure that the syntax of the new instruction doesn't clash with existing ones!

Remember to describe your instruction (what it does, usage, syntax etc) in the Instruction Descriptions section in README.md!

#### Example

You want to add an instruction that, when executed, prints the string `"Verbosy is the best!"`.

First, create an instruction class:

```
public class MyInstruction implements Instruction {
    @Override
    public void execute(VerbosyRuntime runtime) {
        runtime.getOutput().print("Verbosy is the best!");
    }
}
```

In the static constructor of the `CompilerUtility` class, add a key-value pair to both the `instructionMap` and `instructionSubstringStrategy` fields.

    instructionMap.put(Pattern.compile("P"), MyInstruction.class); // The new instruction is denoted by the "P" character!
    instructionSubstringStrategy.put(MyInstruction.class, 1); // The new instruction's length is 1, so the value you put into the map should be 1 as well
    
In the Instruction Descriptions section of README.md, describe the instruction:

    - My Instruction (represented by `P`): prints the string "Verbosy is awesome!".
    
### New Features

You are more than welcome to add new features! Just make sure to describe the new feature in README.md1

### Bug Fixes

Just send a PR for bug fixes and I will review it ASAP!