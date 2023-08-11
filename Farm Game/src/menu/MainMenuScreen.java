package menu;

import guis.GUITexture;
import kotlin.Pair;
import org.joml.Vector2f;
import renderEngine.Loader;
import toolbox.Mouse;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen {
    private final GUITexture background;
    private final GUITexture title;
    private ArrayList<GUITexture> buttons;
    private final ArrayList<GUITexture> copyButtons;
    private List<GUITexture> alternativeButtons;
    private final Loader loader;
    private final MainMenuCommandHandler commandHandler;

    public MainMenuScreen(Loader loader, MainMenuCommandHandler commandHandler) {
        this.loader = loader;
        this.commandHandler = commandHandler;
        background = new GUITexture(loader.loadTexture("mainMenu/background"), new Vector2f(0,0), new Vector2f(1.1f,1));
        title = new GUITexture(loader.loadTexture("mainMenu/title"), new Vector2f(0,0), new Vector2f(0.61f,0.127f));
        title.setBelowOfTop(16.9f);

        loadButtons();
        copyButtons = new ArrayList<>(buttons);
        loadButtonsAlternatives();
    }

    private void loadButtons() {
        buttons = new ArrayList<>();
        buttons.add(new GUITexture(loader.loadTexture("mainMenu/newGame"), new Vector2f(0,0), new Vector2f(0.233f,0.05f)));
        buttons.get(0).setBelowOfTop(41.31f);
        buttons.add(new GUITexture(loader.loadTexture("mainMenu/loadGame"), new Vector2f(0,0), new Vector2f(0.243f,0.051f)));
        buttons.get(1).setBelowOfTop(54.37f);
        buttons.add(new GUITexture(loader.loadTexture("mainMenu/options"), new Vector2f(0,0), new Vector2f(0.164f,0.063f)));
        buttons.get(2).setBelowOfTop(67.56f);
        buttons.add(new GUITexture(loader.loadTexture("mainMenu/exit"), new Vector2f(0,0), new Vector2f(0.077f,0.049f)));
        buttons.get(3).setBelowOfTop(81.87f);
    }

    private void loadButtonsAlternatives() {
        alternativeButtons = new ArrayList<>();
        alternativeButtons.add(new GUITexture(loader.loadTexture("mainMenu/newGameAlternative"), new Vector2f(0,0), new Vector2f(0.233f,0.05f)));
        alternativeButtons.get(0).setPosition(buttons.get(0).getPosition());
        alternativeButtons.add(new GUITexture(loader.loadTexture("mainMenu/loadGameAlternative"), new Vector2f(0,0), new Vector2f(0.243f,0.051f)));
        alternativeButtons.get(1).setPosition(buttons.get(1).getPosition());
        alternativeButtons.add(new GUITexture(loader.loadTexture("mainMenu/optionsAlternative"), new Vector2f(0,0), new Vector2f(0.164f,0.063f)));
        alternativeButtons.get(2).setPosition(buttons.get(2).getPosition());
        alternativeButtons.add(new GUITexture(loader.loadTexture("mainMenu/exitAlternative"), new Vector2f(0,0), new Vector2f(0.077f,0.049f)));
        alternativeButtons.get(3).setPosition(buttons.get(3).getPosition());
    }

    public void check() {
        float mouseX = Mouse.getMouseX();
        float mouseY = Mouse.getMouseY();

        for (int i = 0; i < buttons.size(); i++) {
            Pair<Float, Float> bottomLeft = buttons.get(i).getTextureCoords().getFirst();
            Pair<Float, Float> topRight = buttons.get(i).getTextureCoords().getSecond();
            if(mouseX >= bottomLeft.getFirst() && mouseX <= topRight.getFirst() &&
                    mouseY <= bottomLeft.getSecond() && mouseY >= topRight.getSecond()) {
                buttons.set(i, alternativeButtons.get(i));
                if(Mouse.isPressedLeftButton()) {
                    commandHandler.executeCommand(i);
                }
            } else {
                buttons.set(i, copyButtons.get(i));
            }
        }
    }

    public List<GUITexture> getAllGuis() {
        List<GUITexture> allGuis = new ArrayList<>();
        allGuis.add(background);
        allGuis.add(title);
        allGuis.addAll(buttons);
        return allGuis;
    }

    public List<GUITexture> getAlternativeButtons() {
        return alternativeButtons;
    }
}
