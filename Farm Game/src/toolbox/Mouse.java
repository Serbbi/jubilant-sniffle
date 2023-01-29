package toolbox;

import org.lwjgl.glfw.GLFW;
import renderEngine.DisplayManager;

public class Mouse {
    private static float mouseX;
    private static float mouseY;
    private static float scrollY;
    private static boolean pressedLeftButton;
    private static boolean pressedRightButton;

    public Mouse() {
        GLFW.glfwSetScrollCallback(DisplayManager.getWindow(), this::scroll_callback);
        GLFW.glfwSetMouseButtonCallback(DisplayManager.getWindow(), this::mouse_button_callback);
        GLFW.glfwSetCursorPosCallback(DisplayManager.getWindow(), this::cursor_position_callback);
    }

    private void cursor_position_callback(long window, double xpos, double ypos) {
        mouseX = (float) xpos;
        mouseY = (float) ypos;
    }

    private void mouse_button_callback(long window, int button, int action, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS) {
            pressedRightButton = true;
        } else {
            pressedRightButton = false;
        }
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
            pressedLeftButton = true;
        } else {
            pressedLeftButton = false;
        }
    }

    private void scroll_callback(long window, double xoffset, double yoffset) {
        scrollY = (float) yoffset;
    }

    public static float getMouseX() {
        return mouseX;
    }

    public static float getMouseY() {
        return mouseY;
    }

    public static float getScrollY() {
        return scrollY;
    }

    public static boolean isPressedLeftButton() {
        return pressedLeftButton;
    }

    public static boolean isPressedRightButton() {
        return pressedRightButton;
    }

    public static void setScrollY(float scrollY) {
        Mouse.scrollY = scrollY;
    }
}
