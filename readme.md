# clipboard-jvm

A simple clipboard library for jvm, developed by kotlin.

## usage

just get the clipboard and use it

in kotlin

```kotlin
import org.yeungyeah.clipboard.Clipboard
val clipboard = Clipboard.getClipboard()
println(clipboard.get())
clipboard.set("Hello World")
println(clipboard.get())
```

or in java

```java
import org.yeungyeah.clipboard.Clipboard;

Clipboard clipboard = Clipboard.getClipboard();
System.out.println(clipboard.get());
clipboard.set("Hello World");
System.out.println(clipboard.get());
```

> it's going to deploy on the maven central repository, so you can use it by add dependency in your project. (but now now)

## method

This library implements the clipboard accessing library by call external shell command to access the clipboard. The project provide the clipboard binary file for windows and linux.

It uses 
- `pbcopy` and `pbpaste` for macOS
- `xsel` for linux
- `clipboard.exe` for windows

However, it just tested on macOS, but in theory, it should work on linux and windows.