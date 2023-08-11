package menu;

import renderEngine.Loader;
import saver.GameSaver;

public class MainMenu {
    private MainMenuScreen mainMenuScreen;
    private MainMenuCommandHandler commandHandler;

    public MainMenu(Loader loader, GameSaver gameSaver) {
        commandHandler = new MainMenuCommandHandler(gameSaver);
        mainMenuScreen = new MainMenuScreen(loader, commandHandler);
    }


    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public boolean isOpen() {
        return commandHandler.isOpen();
    }
}
