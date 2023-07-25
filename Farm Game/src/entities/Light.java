package entities;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import toolbox.Keyboard;

public class Light {
    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation = new Vector3f(1,0,0);

    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }

    public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public void modify() {
        //1.0, 0.95, 0.86 is good
        if(Keyboard.isKeyDown(GLFW.GLFW_KEY_I)) {
            colour.x = Math.min(1, colour.x + 0.01f);
            System.out.println("Colour: " + colour);
        } else if (Keyboard.isKeyDown(GLFW.GLFW_KEY_J)) {
            colour.x = Math.max(0, colour.x - 0.01f);
            System.out.println("Colour: " + colour);
        } else if (Keyboard.isKeyDown(GLFW.GLFW_KEY_O)) {
            colour.y = Math.min(1, colour.y + 0.01f);
            System.out.println("Colour: " + colour);
        } else if (Keyboard.isKeyDown(GLFW.GLFW_KEY_K)) {
            colour.y = Math.max(0, colour.y - 0.01f);
            System.out.println("Colour: " + colour);
        } else if (Keyboard.isKeyDown(GLFW.GLFW_KEY_P)) {
            colour.z = Math.min(1, colour.z + 0.01f);
            System.out.println("Colour: " + colour);
        } else if (Keyboard.isKeyDown(GLFW.GLFW_KEY_L)) {
            colour.z = Math.max(0, colour.z - 0.01f);
            System.out.println("Colour: " + colour);
        }
    }
}
