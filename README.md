# FallingRocks

FallingRocks is a GUI game made with Swing.

## Controls
- Arrow keys - move
- Space - select or slow down
- P key - pause

## Build and run
(Working on an M2 MacBook using temurin, Java v19).

```
chase@Chases-MacBook-Air FallingRocks % java --version
openjdk 19 2022-09-20
OpenJDK Runtime Environment Temurin-19+36 (build 19+36)
OpenJDK 64-Bit Server VM Temurin-19+36 (build 19+36, mixed mode)
chase@Chases-MacBook-Air FallingRocks %
chase@Chases-MacBook-Air FallingRocks % find . -name "*.java" > /tmp/sources.txt && javac --class-path libs/* -d bin @/tmp/sources.txt && rm /tmp/sources.txt
chase@Chases-MacBook-Air FallingRocks % java --class-path "libs/*":bin/ non_gamepanel.Main
```

This application works best on a Unix machine. Becuase of different application
window insets on different operating systems, the GUI is not the correct size
on non-Unix operating systems.
