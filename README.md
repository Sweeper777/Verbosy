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

To run the `VerbosyProgram`, you need to first create a runtime. You can either use the `StandardRuntime` or create your own runtime by implementing `VerbosyRuntime`.

```
StandardRuntime runtime = new StandardRuntime("some inputs", 20);
program.run(runtime);
```

