# efa - electronic Logbook

This project has been configured to use Gradle as a build tool. The Gradle wrapper scripts (`gradlew` and `gradlew.bat`) are included, but the wrapper JAR file is not. Follow the instructions below to set up the build environment.

## Setting up the build environment

### Building the project

To build the project, run:

```bash
./gradlew build
```

This will compile the code and run the tests.

### Creating a fat JAR

To create a fat JAR with all dependencies included, run:

```bash
./gradlew fatJar
```

The resulting JAR file will be located in `build/libs/efa-2.0.0-fat.jar`.

### Running the application

To run the application, you can use:

```bash
./gradlew run
```

## Project Structure

- `de/` - Main source code directory
- `plugins/` - Contains plugin JAR files
- `help/` - Help documentation
- `fmt/` - Format templates
- `cfg/` - Configuration files
- `eou/` - End-user documentation

## Dependencies

The project depends on several libraries:

- JavaFX for the GUI
- Various plugins for specific functionality (FTP, PDF, etc.)

All dependencies are configured in the `build.gradle.kts` file.

## Java Version

The project is configured to use Java 11. Make sure you have Java 11 or later installed.