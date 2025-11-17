# Red-Nosed Reports (advent of code #2 2024)

This repository contains my solution to the [challenge #2 of the 2024 Advent of Code](https://adventofcode.com/2024/day/2), written in Java.

## Prerequisites
- Java 21

## Building and tests
- To build, run:
```
./gradlew build
```

- To launch the junit tests, run:
```
./gradlew test
```

- To build the jar archive, run:
```
./gradlew jar
```

## Running
First get your input, and save it in `./data/input.txt`.

To launch the application, run:
```
./gradlew run
```

Alternatively, it is possible to run from the jar file:
```
java -jar build/libs/<jar_file_name>.jar
```

To use a different input file:
```
java -jar build/libs/<jar_file_name>.jar path/to/input.txt
```
