import renderEngine.DisplayManager;

public class Main {
    public static void main(String[] args) {
        DisplayManager displayManager = new DisplayManager();
        displayManager.createDisplay();
        displayManager.updateDisplay();
        displayManager.closeDisplay();
    }
}