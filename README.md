# Verbosy

Verbosy is a language very based on [SPL](https://github.com/Sweeper777/SPL-Compiler). It has 12 basic instructions: `i`, `o`, `+`, `-`, `^`, `v`, `\`, `/`, `>`, `>0`, `>-`, `~`.


## Runtime

The Verbosy runtime is represented by the `VerbosyRuntime` interface. A Verbosy runtime has a memory of arbitrary size and a slot called `Current`.

## Type System

The Verbosy type system is very simple, there are two types `char` (a  unsigned 16-bit UTF-16 code unit), and `int` (a signed 32-bit integer). Each memory location and `Current` can store a value that is of one of these types. The type of the value can change at runtime. Note that initially, all memory locations and `Current` store _no value at all_.

## Structure of VerbosyProgram

A Verbosy program contains a number of instructions separated by whitespace. Some instructions take parameters. For example, in the instruction `/14`, the `/` denotes a `PutInstruction` and the `14` denotes the parameter `14`.

A Verbosy program will terminate after one of the following conditions is met:

- A halt instruction is executed.
- The last instruction, which is not a goto instruction, is executed.
- EOF is found when executing an input instruction.
- A negative memory location is accessed

## Pointers

Some instructions can accept a pointer as the parameter. The `*` suffix denotes a pointer. For example, in `\14*`, the pointer points to the value in slot 14 of the memory.

When evaluated, the parameter will be magically "replaced" by the value in memory that it points to. If the value of slot 14 is `10`, `\14*` is the same as `\10`.

## Labels

Labels are denoted by surrounding colons (`:`) around the label's name. For example: `:a:`, `:start:`.

Labels act like markers. You can use the goto instructions (`>`, `>0`, `>-`) to make the code execution jump to a specific label. Label names can only contain letters.

## Comments

Single line comments begin with `//`. Multi-line comments are delimited by `/*` and `*/`. 

```
// single line comment
/*
multi
line
comment!
*/
```

## Instruction Descriptions

- Input Instruction (represented by `i`): gets the user's input as a `VerbosyValue` and put the value in the `Current` slot. Inputs can be a single character or an integer.
- Output Instruction (represented by `o`): prints the value in `Current`. If the value is an integer, a space character will also be printed.
- Set Instruction (represented by `~`): this instruction takes  a parameter. It sets the value of `Current` to the value of the parameter. For example, `~10` will set `Current` to `10`. This instruction does not accept pointers.
- Add Instruction (represented by `+`): this instruction takes a parameter. It sums the value in the memory slot corresponding to the parameter and `Current`. It then assigns the sum to `Current`. Pointers can be used.
- Sub Instruction (represented by `-`): this instruction takes a parameter. It calculates the difference of `Current` and the value in the memory slot corresponding to the parameter (`Current` - value in memory slot), assigning the difference to `Current`. Pointers can be used.
- Inc Instruction (represented by `^`): this instruction takes a parameter. It increases the value in the memory slot corresponding to the parameter by 1, and assigns the incremented value to `Current`. Pointers can be used.
- Dec Instruction (represented by `v`): this instruction takes a parameter. It decreases the value in the memory slot corresponding to the parameter by 1, and assigns the decremented value to `Current`. Pointers can be used.
- Put instruction (represented by `/`): this instruction takes a parameter. It assigns the value in `Current` to the memory slot corresponding to the parameter.
- Take instruction (represented by `\`): this instruction takes a parameter. It assigns the value in the memory slot corresponding to the parameter to `Current`.
- Goto instruction (represented by `>`): this instruction requires a label. Please write the name of the label immediately after the name of the instruction. It will make the code execution jump to where the label is.
- GotoIf0 instruction (represented by `>0`): same as the Goto instruction, except that it will only jump to the label if `Current` is 0.
- GotoIfNeg instruction (represented by `>-`): same as the Goto instruction, except that it will only jump to the label if `Current` is less than 0.
- Halt instruction (represented by `x`): stops the execution of the verbosy program

An instruction will do nothing if any of the following is true

- its parameter is not a pointer, and it is out of range of the memory (on some implementations, this will cause a compiler error),
- its parameter is a pointer, and it points to a non-negative location that is out of range of the memory, or;
- it attempts to read a memory location (or `Current`) which stores no value at all.

## Hex Escapes

Most characters can be used in verbatim as the parameter for the set instruction, in order to set `Current` to a character. In some cases you can't, because the parser treats that character in a special way. e.g. all whitespace are ignored. In such a case, you can specify that character in a set instruction using its hex value, prefixed by `\`.

For example, `~\20` sets `Current` to the space character.

## Type Conversions

Using the fact that `+` and `-` instructions keep the type of `Current` when adding/subtracting, type conversions can be done by setting a `Current` to `0` (for converting to `int`) or `\0` (for converting to `char`), then adding the value to be converted to `Current`, then putting into the original location.

For example, if there is a `int` in location 3 that you want to convert to `char`, you can do:

```
~\0 +3 /3
```

Now there is a `char` in location 3.

## The Java Implementation

This implementation compiles Verbosy code into C#, and requires you to have `csc` installed. The compiler options are:

```
 -h,--help                          Show help, ignores all other options
 -i,--read-ints                     parses integers on the input stream
                                    automatically
 -n,--nowarn                        disable warnings
 -o,--output <output-file>          output file path
 -s,--memory-size <size>            number of elements in the memory
                                    array, default to 1024
 -S,--output <output-source-file>   file path for output C# source file
 -z,--space-as-zero                 reads spaces in the input as 0s. Makes
                                    reading words easier!
```

#### Hello World

This prints "Hello World".

    ~H o ~e o ~l o ~l o ~o o ~\20 o ~W o ~o o ~r o ~l o ~d o
    
#### Input Echo

This prints an arbitrary-length input.

    :a: i o >a
    
#### Increment

This increments the input by 1.

    i /0 ^0 o
    
#### Addition

Adds up the two inputs and prints the result.

    i /0 i +0 o

Compile with `-i`.
    
#### 1 to 10

Prints "1 2 3 4 5 6 7 8 9 10 "

    ~0 /0 ~10 /1 :a: ^0 o \0 -1 >-a
    
#### Infinite Loop

    :a: >a
    
#### Reverse

Reverses the 0-terminated input string. On

    ~0 /1000 :a: ^1000 i >0b /1000* >a :b: v1000 :c: \1000* o v1000 >0a >c
    
Compile with `-z`.

#### Truth Machine

Prints 0 if input is 0. Prints 1 forever if input is 1.

    i >0a ~\31 :b: o >b :a: ~\30 o