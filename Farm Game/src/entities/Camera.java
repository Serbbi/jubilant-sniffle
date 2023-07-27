package entities;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.DisplayManager;
import terrain.Terrain;
import toolbox.Keyboard;
import toolbox.Maths;
import toolbox.Mouse;
import utils.JSON.JSONObject;
import utils.JSON.JSONable;
import utils.JSON.parser.JSONParser;

public class Camera implements JSONable {
    private Vector3f position = new Vector3f(25,10,25);
    private float pitch = 30;
    private float yaw = 0;
    private float roll;
    private static final int LOWER_LIMIT = 3;
    private static final int UPPER_LIMIT = 50;
    private static final int SPEED = 10;

    public Camera() {
        Keyboard keyboard = new Keyboard();
        Mouse mouse = new Mouse();
        GLFW.glfwSetKeyCallback(DisplayManager.getWindow() , keyboard::invoke);
    }

    public void move() {
        zoom();
        rotate();
        float currentSpeed = DisplayManager.getFrameTimeSeconds() * SPEED;
        float dx = (float) (currentSpeed * Math.sin(Math.toRadians(yaw)));
        float dz = (float) (currentSpeed * Math.cos(Math.toRadians(yaw)));
        if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
            increasePosX(dx);
            increasePosZ(-dz);
        } if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
            increasePosX(-dx);
            increasePosZ(dz);
        } if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
            increasePosX(-dz);
            increasePosZ(-dx);
        } if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
            increasePosX(dz);
            increasePosZ(dx);
        }
    }

    public void increasePosX(float x) {
        if(position.x + x > Terrain.getLimits().x && position.x + x < Terrain.getLimits().y) {
            position.x += x;
        }
    }

    public void increasePosY(float y) {
        if(position.y + y >= LOWER_LIMIT && position.y + y <= UPPER_LIMIT) {
            position.y += y;
        }
    }

    public void increasePosZ(float z) {
        if(position.z + z > Terrain.getLimits().x && position.z + z < Terrain.getLimits().y) {
            position.z += z;
        }
    }

    public void zoom() {
        if (Mouse.getScrollY() != 0) {
            Vector3f lookPoint = Maths.getPointOnTerrain(this);
            float xRatio = (position.x - lookPoint.x) * 0.2f;
            float yRatio = (position.y - lookPoint.y) * 0.2f;
            float zRatio = (position.z - lookPoint.z) * 0.2f;
            if (Mouse.getScrollY() > 0) {
                increasePosY(-yRatio);
                if (position.y <= LOWER_LIMIT) {
                    increasePosX(-xRatio);
                    increasePosZ(-zRatio);
                }
            } else {
                increasePosY(yRatio);
                increasePosX(xRatio);
                increasePosZ(zRatio);
//                if (position.y >= UPPER_LIMIT) {
//                    increasePosX(xRatio);
//                    increasePosZ(zRatio);
//                }
            }
            Mouse.setScrollY(0);
        }
    }

    public void rotate() {
        if (Mouse.isPressedRightButton()) {
            float mouseX = Mouse.getMouseX();
            yaw += (mouseX - DisplayManager.getWidth()/2.0) * 0.002f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("positionX", position.x);
        json.put("positionY", position.y);
        json.put("positionZ", position.z);
        json.put("pitch", pitch);
        json.put("yaw", yaw);
        json.put("roll", roll);
        return json;
    }

    public void loadFromJson(JSONObject json) {
        double x = (double) json.get("positionX");
        double y = (double) json.get("positionY");
        double z = (double) json.get("positionZ");
        double pitch = (double) json.get("pitch");
        double yaw = (double) json.get("yaw");
        double roll = (double) json.get("roll");
        position = new Vector3f((float) x, (float) y, (float) z);
        this.pitch = (float) pitch;
        this.yaw = (float) yaw;
        this.roll = (float) roll;
    }
}
