# Verbosy

Verbosy is a language very similar to [SPL](https://github.com/Sweeper777/SPL-Compiler). It has 12 basic instructions: `i`, `o`, `+`, `-`, `^`, `v`, `\`, `/`, `>`, `>0`, `>-`, `~`.

### Runtime

The Verbosy runtime is represented by the `VerbosyRuntime` interface. A Verbosy runtime has a memory of arbitrary size and a slot called `Current`. It is used to get input and output text.

### Type System

The Verbosy type system is very simple, there is only one type - `VerbosyValue`. A `VerbosyValue` can either store a single character, or an integer. The type a `VerbosyValue` is storing is inferred at runtime and can change dynamically at runtime.

### Usage in Java

To compile Verbosy code, create a new instance of the `VerbosyCompiler` class and call `compile`, which returns a `VerbosyProgram`.

```
// this code prints "HelloWorld"
String code = "~H o ~e o ~l o ~l o ~o o ~W o ~o o ~r o ~l o ~d o";
try {
    VerbosyCompiler compiler = new VerbosyCompiler();
    VerbosyProgram program = compiler.compile(code);
} catch (CompilerErrorException e) {
    System.out.println(e.getMessage());
}
```

To run the `VerbosyProgram`, you need to first create a runtime. You can either use the `StandardRuntime` or create your own runtime by implementing the `VerbosyRuntime` interface.

```
StandardRuntime runtime = new StandardRuntime("some inputs", 20);
program.run(runtime);
```

###  Run the main class

You can run the main class by doing this command:

    java io.github.sweeper777.verbosy.Main
    
You can then enter the Verbosy code, press enter, then enter the inputs for the Verbosy program, After pressing enter once more, the program will start running.

### Structure of VerbosyProgram

A Verbosy program contains a number of instructions separated by spaces. Some instructions take parameters. For example, in the instruction `/14`, the `/` denotes a `PutInstruction` and the `14` denotes the parameter `14`.

A Verbosy program will terminate after one of the following conditions is met:

- The last instruction, which is not a goto instruction, is executed.
- No more input is found when executing an input instruction.

### Pointers

Some instructions can accept a pointer as the parameter. The `*` suffix denotes a pointer. For example, in `\14`, the pointer points to the value in slot 14 of the memory.

When evaluated, the parameter will be magically "replaced" by the value in memory that it points to. If the value of slot 14 is `10`, `\14*` is the same as `\10`.

### Labels

Labels are denoted by surrounding colons (`:`) around the label's name. For example: `:a:`, `:start:`.

Labels act like markers. You can use the goto instructions (`>`, `>0`, `>-`) to make the code execution jump to a specific label. Label names can only contain letters.

### Instruction Descriptions

- Input Instruction (represented by `i`): gets the user's input as a `VerbosyValue` and put the value in the `Current` slot. Inputs can be a single character or an integer.
- Output Instruction (represented by `o`): prints the value in `Current` to the `PrintStream` returned by the runtime's `getOutput` method. If the value is an integer, a space character will also be printed.
- Set Instruction (represented by `~`): this instruction takes  a parameter. It sets the value of `Current` to the value of the parameter. For example, `~10` will set `Current` to `10`. This instruction does not accept pointers.
- Add Instruction (represented by `+`): this instruction takes a parameter. It sums the value in the memory slot corresponding to the parameter and `Current`. It then assigns the sum to `Current`. Pointers can be used.
- Sub Instruction (represented by `-`): this instruction takes a parameter. It calculates the difference of `Current` and the value in the memory slot corresponding to the parameter (`Current` - value in memory slot). Pointers can be used.
- Inc Instruction (represented by `^`): this instruction takes a parameter. It increases the value in the memory slot corresponding to the parameter by 1. Pointers can be used.
- Dec Instruction (represented by `v`): this instruction takes a parameter. It decreases the value in the memory slot corresponding to the parameter by 1. Pointers can be used.
- Put instruction (represented by `/`): this instruction takes a parameter. It assigns the value in `Current` to the memory slot corresponding to the parameter.
- Take instruction (represented by `\`): this instruction takes a parameter. It assigns the value in the memory slot corresponding to the parameter to `Current`.
- Goto instruction (represented by `>`): this instruction requires a label. Please write the name of the label immediately after the name of the instruction. It will make the code execution jump to where the label is.
- GotoIf0 instruction (represented by `>0`): same as the Goto instruction, except that it will only jump to the label if `Current` is 0.
- GotoIfNeg instruction (represented by `>-`): same as the Goto instruction, except that it will only jump to the label if `Current` is less than 0.

### Example Programs

#### Hello World

This prints "HelloWorld".

    ~H o ~e o ~l o ~l o ~o o ~W o ~o o ~r o ~l o ~d o
    
#### Input Echo

This prints the input.

    :a: i o >a
    
#### Increment

This increments the input by one.

    i /0 ^0 o
    
#### Addition

Adds up the two inputs and prints the result.

    i /0 i +0 o
    
#### 1 to 10

Prints "1 2 3 4 5 6 7 8 9 10 "

    ~0 /0 ~10 /1 :a: ^0 o \0 -1 >-a
    
#### Infinite Loop

    :a: >a
    
#### Reverse

Reverses the space-terminated input string.

    ~0 /14 :a: ^14 i >0b /14* >a :b: v14 :c: \14* o v14 >0a >c
    
