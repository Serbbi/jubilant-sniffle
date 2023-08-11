package toolbox;

import entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderEngine.DisplayManager;

public class Maths {
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        Vector3f translation3 = new Vector3f(translation.x, translation.y, 0);
        matrix.translate(translation3);
        matrix.scale(new Vector3f(scale.x, scale.y, 1f));
        return matrix;
    }

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Matrix4f createTransformationMatrix
            (Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0));
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0));
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }

    public static Matrix4f createViewMatrix (Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }

    public static Matrix4f createProjectionMatrix() {
        float FOV = 70;
        float FAR_PLANE = 1000;
        float NEAR_PLANE = 0.1f;
        float aspectRatio = (float) DisplayManager.getWidth() / DisplayManager.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE+NEAR_PLANE)/frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2*NEAR_PLANE * FAR_PLANE)/frustum_length));
        projectionMatrix.m33(0);
        return projectionMatrix;
    }

    public static Vector3f getPointOnTerrain(Camera camera) {
        Vector3f currentRay = calculateRay(camera);
        if (intersectionInRange(0, 600, currentRay, camera)) {
            return binarySearch(0, 0, 600, currentRay, camera);
        }
        return new Vector3f(0,0,0);
    }

    public static Vector3f calculateRay(Camera camera) {
        Vector2f normalizedCoords = new Vector2f(0,0);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldCoords(eyeCoords, camera);
        return worldRay;
    }

    public static Vector3f toWorldCoords(Vector4f eyeCoords, Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        Matrix4f invertedView = viewMatrix.invert();
        Vector4f rayWorld = invertedView.transform(eyeCoords);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }

    public static Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f projectionMatrix = Maths.createProjectionMatrix();
        Matrix4f invertedProjection = new Matrix4f();
        // REMINDER TO USE MATRIX.INVERT(DESTINATION);!!!!! altfel modifica matricea initiala
        projectionMatrix.invert(invertedProjection);
        Vector4f eyeCoords = invertedProjection.transform(clipCoords);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private static Vector3f getPointOnRay(Vector3f ray, float distance, Camera camera) {
        Vector3f camPos = camera.getPosition();
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        scaledRay.add(start);
        return scaledRay;
    }

    public static Vector3f binarySearch(int count, float start, float finish, Vector3f ray, Camera camera) {
        float half = start + ((finish - start) / 2f);
        if (count >= 200) {//arbitrary chosen number
            return getPointOnRay(ray, half, camera);
        }
        if (intersectionInRange(start, half, ray, camera)) {
            return binarySearch(count + 1, start, half, ray, camera);
        } else {
            return binarySearch(count + 1, half, finish, ray, camera);
        }
    }

    public static boolean intersectionInRange(float start, float finish, Vector3f ray, Camera camera) {
        Vector3f startPoint = getPointOnRay(ray, start, camera);
        Vector3f endPoint = getPointOnRay(ray, finish, camera);
        return !isUnderGround(startPoint) && isUnderGround(endPoint);
    }

    private static boolean isUnderGround(Vector3f testPoint) {
        return testPoint.y < 0;
    }

    public static float mapFromPixelToScreenCoordinatesHeight(float pixels) {
        return (pixels / DisplayManager.getHeight() * 2 - 1) * -1;
    }
}
