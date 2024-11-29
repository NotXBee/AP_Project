# Angry Birds

This is the working model of the AP project assigned to us by the IIIT Delhi which aims to clone the popular game 'Angry Birds' by Rovio.

This project is a working model of the game with physics, game logic, different birds, blocks and pigs with different properties.
The game consists of three levels with increasing difficulty. The player has to clear all the pigs in the level to move to the next level.

3 different structures : 
1. Glass (Low HP)
2. Wood (Medium HP)
3. Metal (High HP)

3 different pigs : 
1. Normal Pig (Low HP, Low Radius)
2. Helmet Pig (High HP, High Radius)
3. Crowned Pig (Medium HP, Medium Radius)

3 different birds : 
1. Red (Normal bird)
2. Matilda (Can be launched twice)
3. Terrence (Heavy damage dealer, High Radius)

The player can save their progress by using the save and exit button at the paused screen.
The progress can be loaded by using the load button at the main menu.

This game uses test.ser file to store the progress of the player including any progress they might have made in the given level.

The game is implemented using the LWJGL library in Java.

The contributors to this project are:
1. Aditya Nuli - 2023360
2. Piyush Keshan - 2023376

## Running the project

To run the project, you just have to run Lwgjl3Launcher.java file in the lwjgl3 package.

### [Git-hub](https://github.com/NotXBee/AP_Project)

### [UML Diagram](UML.png)

### [Demo Video](https://youtu.be/NwC9Fnb7jeM)

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
