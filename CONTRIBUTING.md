### Contribute!

If you want to add a new instruction to this language, you can do this:

First, create your instruction class. 

- If your instruction needs a parameter, implement `ParameterInstruction`. Please also provide a constructor that takes a `VerbosyParameter`.
- If your instruction is a kind of goto instruction, inherit `GotoInstruction`. Please also provide a constructor that takes a `Label`.
- If neither of those applies, implement `Instruction`. Please also provide a parameterless constructor.

You can refer to existing implementations of instructions.

Then, you need to add a key-value pair to the `instructionMap` in `CompilerUtility.java`. Use the pattern of your instruction as the key, and your instruction class as the value, just like the existing instructions.

You also need to add a key-value pair to the `instructionSubstringStrategy` which is also in `CompilerUtility.java`. Use your instruction class as the key, and the number of characters that your instruction has as the value.

If you need to do some special stuff when compiling your instruction, please handle it in the `compile` method.

After that, send me a pull request! But please first make sure that the syntax of the new instruction doesn't clash with existing ones!
