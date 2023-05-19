package guis;

import models.TexturedModel;
import org.joml.Vector2f;

public class GUIElement {
    private Vector2f position;
    private Vector2f scale;
    private TexturedModel texturedModel;

    public GUIElement(Vector2f position, TexturedModel texturedModel, Vector2f scale) {
        this.position = position;
        this.texturedModel = texturedModel;
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return position;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }
}
