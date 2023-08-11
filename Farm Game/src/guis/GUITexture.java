package guis;

import kotlin.Pair;
import org.joml.Vector2f;
import renderEngine.DisplayManager;
import toolbox.Maths;

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

    public void setBelowOfTop(float percentage) {
        float pixels = percentage / 100 * DisplayManager.getHeight();
        float halfOfTextureHeight = this.getScale().y * DisplayManager.getHeight() / 2;
        pixels += halfOfTextureHeight;
        this.setPosition(new Vector2f(0, Maths.mapFromPixelToScreenCoordinatesHeight(pixels)));
    }

    public void setToRightOfTexture(GUITexture referenceTexture, float percentage) {
        int displayWidth = DisplayManager.getWidth();
        int distanceInPixels = (int) (percentage * displayWidth);
        float halfOfReferenceObject = referenceTexture.getPosition().x * displayWidth / 2 + (displayWidth * referenceTexture.getScale().x / 2.0f);
        float halfOfObjectToPlace = (displayWidth * this.getScale().x / 2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;

        float percentageOfScreen = (float) distanceInPixels / (displayWidth / 2.0f);
        this.setPosition(new Vector2f(percentageOfScreen, this.getPosition().y));
    }

    public void setToLeftOfTexture(GUITexture guiTexture, float distance) {
        int displayWidth = DisplayManager.getWidth();
        int distanceInPixels = (int) (distance * -1 * displayWidth);
        float halfOfReferenceObject = guiTexture.getPosition().x * displayWidth / 2 - (displayWidth * guiTexture.getScale().x / 2.0f);
        float halfOfObjectToPlace = (displayWidth * this.getScale().x / -2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;

        float percentageOfScreen = (float) distanceInPixels / (displayWidth / 2.0f);
        this.setPosition(new Vector2f(percentageOfScreen, this.getPosition().y));
    }

    public void setToAboveOfTexture(GUITexture guiTexture, float distance) {
        int displayHeight = DisplayManager.getHeight();
        int distanceInPixels = (int) (distance * displayHeight);
        float halfOfReferenceObject = guiTexture.getPosition().y * displayHeight / 2 + (displayHeight * guiTexture.getScale().y / 2.0f);
        float halfOfObjectToPlace = (displayHeight * this.getScale().y / 2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;
        float percentageOfScreen = (float) distanceInPixels / (displayHeight / 2.0f);
        this.setPosition(new Vector2f(this.getPosition().x, percentageOfScreen));
    }

    public void setToBelowOfTexture(GUITexture guiTexture, float distance) {
        int displayHeight = DisplayManager.getHeight();
        int distanceInPixels = (int) (distance * -1 * displayHeight);
        float halfOfReferenceObject = guiTexture.getPosition().y * displayHeight / 2 - (displayHeight * guiTexture.getScale().y / 2.0f);
        float halfOfObjectToPlace = (displayHeight * this.getScale().y / -2.0f);
        distanceInPixels += halfOfReferenceObject + halfOfObjectToPlace;
        float percentageOfScreen = (float) distanceInPixels / (displayHeight / 2.0f);
        this.setPosition(new Vector2f(this.getPosition().x, percentageOfScreen));
    }
}
