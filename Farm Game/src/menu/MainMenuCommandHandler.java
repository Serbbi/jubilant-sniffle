package menu;

import renderEngine.DisplayManager;
import saver.GameSaver;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class MainMenuCommandHandler {
    private boolean isOpen = true;
    private GameSaver gameSaver;

    public MainMenuCommandHandler(GameSaver gameSaver) {
        this.gameSaver = gameSaver;
    }

    public void executeCommand(int commandCode) {
        String command = parseCommand(commandCode);
        switch (command) {
            case "newGame":
                isOpen = false;
                break;
            case "loadGame":
                isOpen = false;
                gameSaver.loadGame();
                break;
            case "options":
                break;
            case "exit":
                commandExit();
                break;
        }
    }

    private String parseCommand(int commandCode) {
        return switch (commandCode) {
            case 0 -> "newGame";
            case 1 -> "loadGame";
            case 2 -> "options";
            case 3 -> "exit";
            default -> "error";
        };
    }

    private void commandExit() {
        glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
    }

    public boolean isOpen() {
        return isOpen;
    }
}
