# Semesterproject-team3.2

## About the application
This game is a part of a semester project at SDU.
It's both a CLI and a GUI game.
The game is about the project Ocean Cleanup, where the goal is to talk to the employees, and help them by doing a small task.

## Dependencies
 - Jackson.databind
 - JavaFX.controls
 - JavaFX.fxml
 - JavaFX.media

## How to run the application

### CLI
1. Run the main method in `oceanCleanup.src.textUI.OceanCleanupApplication`

### JavaFX
1. Create an application run-configuration
2. Insert `oceanCleanup.src.GUI.GameApplication` as main class
3. Add `VM options` in the drop-down menu `Modify options`
4. Insert `--add-modules javafx.controls,javafx.fxml,javafx.media --module-path lib` in `VM options`
