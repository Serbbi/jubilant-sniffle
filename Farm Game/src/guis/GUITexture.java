package guis;

import kotlin.Pair;
import org.joml.Vector2f;
import renderEngine.DisplayManager;

public class GUITexture implements Cloneable {

    private int texture;
    private Vector2f position;
    private Vector2f scale;
    private float ratio;
    private Pair<Pair<Float, Float>, Pair<Float, Float>> textureCoords;

    public GUITexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
        ratio = scale.y / scale.x;
        reSetTextureCoords();
    }

    public void reSetTextureCoords() {
        int displayWidth = DisplayManager.getWidth();
        int displayHeight = DisplayManager.getHeight();
        float halfOfTextureWidth = scale.x * displayWidth / 2.0f;
        float halfOfTextureHeight = scale.y * displayHeight / 2.0f;
        float scalingFactorWidth = displayWidth / 2.0f;
        float scalingFactorHeight = displayHeight / -2.0f;
        Pair<Float, Float> bottomLeft = new Pair<>((position.x + 1) * scalingFactorWidth - halfOfTextureWidth, (position.y - 1) * scalingFactorHeight + halfOfTextureHeight);
        Pair<Float, Float> topRight = new Pair<>((position.x + 1) * scalingFactorWidth + halfOfTextureWidth, (position.y - 1) * scalingFactorHeight - halfOfTextureHeight);
        textureCoords = new Pair<>(bottomLeft, topRight);
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public float getRatio() {
        return ratio;
    }

    public Pair<Pair<Float, Float>, Pair<Float, Float>> getTextureCoords() {
        return textureCoords;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
        reSetTextureCoords();
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
        reSetTextureCoords();
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public GUITexture clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (GUITexture) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
