package renderEngine;

import components.GrowthComponent;
import entities.Camera;
import entities.Entity;
import entities.Light;
import fontRendering.TextMaster;
import guis.GUIManager;
import guis.GUIRenderer;
import inventory.Inventory;
import items.ItemFactory;
import items.plants.Plant;
import kotlin.Pair;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import terrain.Terrain;
import toolbox.*;

import java.nio.IntBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {
    private static int width[] = new int[1];
    private static int height[] = new int[1];
    private static long window;
    private static long lastFrameTime;
    private static float delta;
    private ExecutorService executorService;
    private GUIManager guiManager;
    private MasterRenderer renderer;

    public void createDisplay() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        width[0] = 2160;
        height[0] = 1440;
        // Create the window
        window = glfwCreateWindow(width[0], height[0], "Farming Game", NULL, NULL);

        if ( window == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        glfwSetWindowSizeCallback(window, this::window_size_callback);
        executorService = Executors.newCachedThreadPool();

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        lastFrameTime = getCurrentTime();
    }

    public void updateDisplay() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        Loader loader = new Loader();
        renderer = new MasterRenderer();
        GUIRenderer guiRenderer = new GUIRenderer(loader);
        Terrain terrain = new Terrain(loader);
        TextMaster.init(loader);

        Light light = new Light(new Vector3f(0,50,50), new Vector3f(1,1,1));
        Camera camera = new Camera();
        MousePicker mousePicker = new MousePicker(camera);

        Inventory inventory = new Inventory(loader);
        ItemFactory itemFactory = new ItemFactory(terrain, mousePicker, inventory, loader);
        itemFactory.createShovel();
        itemFactory.createCarrotSeeds();
        itemFactory.createHoe();

        GrowthComponent growthComponent = new GrowthComponent();
        executorService.submit(growthComponent);

        guiManager = new GUIManager(inventory);
        guiManager.resizeGUIs();

        PlantModelsStorage.initializePlantModels(loader);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            calculateTime();
            glfwGetWindowSize(window, width, height);
            camera.move();
            mousePicker.update();
            renderer.render(light,camera);

            for (Plant plant: StorageObjects.getPlants()) {
                renderer.processEntity((Entity) plant);
            }
            renderTerrain(renderer,terrain);

            guiRenderer.render(inventory.getGui().getInventoryBar());
            if(inventory.isOpen()) {
                guiRenderer.render(inventory.getGui().getBigInventory());
            }
            guiRenderer.render(inventory.getGui().getItemIconsFromInventoryBar());
            inventory.select();
            inventory.updateFlyingItem();

            if(Mouse.isPressedLeftButton()) {
                inventory.mouseSelect();
                if(!inventory.isOpen()) {
                    inventory.useItem();
                }
            }

            if(Keyboard.isKeyDown(GLFW_KEY_E)) {
                inventory.setOpen(!inventory.isOpen());
            }
            if(Keyboard.isKeyDown(GLFW_KEY_Q)) {
                itemFactory.createCarrotSeeds();
                guiManager.resizeGUIs();
            }
            if (Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(window, true);
            }
            TextMaster.render();


            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        TextMaster.cleanUp();
        guiRenderer.cleanUp();
        loader.cleanUp();
        renderer.cleanUp();
    }

    public void renderTerrain(MasterRenderer renderer, Terrain terrain) {
        for (Pair<Float,Float> pair: terrain.getTiles().keySet()) {
            renderer.processEntity(terrain.getTiles().get(pair));
        }
    }

    public void closeDisplay() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        executorService.shutdownNow();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static long getWindow() {
        return window;
    }

    public static int getWidth() {
        return width[0];
    }

    public static int getHeight() {
        return height[0];
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public void calculateTime() {
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }

    public void window_size_callback(long window, int width, int height) {
        DisplayManager.width[0] = width;
        DisplayManager.height[0] = height;
        glViewport(0, 0, width, height);
        float aspectRatio = (float)width / (float)height;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-aspectRatio, aspectRatio, -1.0f, 1.0f, -1.0f, 1.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        guiManager.resizeGUIs();
        renderer.resetProjectionMatrix();
    }

}
