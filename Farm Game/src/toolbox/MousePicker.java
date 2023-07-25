package toolbox;

import entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderEngine.DisplayManager;

public class MousePicker {
    private static final float RAY_RANGE = 600;
    private Vector3f currentRay = new Vector3f();
    private Matrix4f viewMatrix;
    private Camera camera;
    private Vector3f currentTerrainPoint;

    public MousePicker(Camera cam) {
        camera = cam;
        viewMatrix = Maths.createViewMatrix(camera);
    }

    public Vector3f getCurrentTerrainPoint() {
        return currentTerrainPoint;
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
//        viewMatrix = Maths.createViewMatrix(camera);
        currentRay = calculateMouseRay();
        if (Maths.intersectionInRange(0, RAY_RANGE, currentRay, camera)) {
            currentTerrainPoint = Maths.binarySearch(0, 0, RAY_RANGE, currentRay, camera);
        } else {
            currentTerrainPoint = null;
        }
    }

    private Vector3f calculateMouseRay() {
        float mouseX = Mouse.getMouseX();
        float mouseY = Mouse.getMouseY();
        Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Vector4f eyeCoords = Maths.toEyeCoords(clipCoords);
        Vector3f worldRay = Maths.toWorldCoords(eyeCoords, camera);
        return worldRay;
    }

    private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
        float x = (2.0f * mouseX) / DisplayManager.getWidth() - 1f;
        float y = 1f - (2.0f * mouseY) / DisplayManager.getHeight();
        return new Vector2f(x, y);
    }
}
