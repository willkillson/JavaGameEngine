package game;

import game.entity.PixelUnit;
import game.entity.WorldHexGrid;
import graphics.Color;
import graphics.Screen;
import graphics.vec.Vec2;
import input.InputEvent;
import java.util.Stack;

public class Game {

    private GameObject root;
    private Screen screen;
    private Stack<InputEvent> eventQue;

    private WorldHexGrid whg;

    private int cameraSpeed = 20;

    private boolean isMousePressed = false;

    public Game(Screen screen, Stack<InputEvent> eventQue) {
        // Store the pixel array
        this.root = new GameObject();
        this.screen = screen;
        this.eventQue = eventQue;
    }

    public void init() {
        // grid setup
        //        this.whg = new WorldHexGrid(screen);
        //        composables.add(this.whg);
    }

    public void update() {

        // Handles events
        this.processEventQue();

        // Handle Game Objects
        root.update();
    }

    /**
     * Kills all units in game.
     */
    public void kill() {}

    private void processEventQue() {
        if (eventQue.size() > 0) {
            while (eventQue.size() > 0) {
                InputEvent event = eventQue.pop();
                switch (event.name) {
                    case "mouseMoved":
                        if (isMousePressed) {
                            // Drawing on screen
                            Vec2 gameObjectPosition =
                                    Screen.ScreenTooWorldCoord(event.position, root.getWorldPosition());
                            GameObject pixelObject =
                                    new PixelUnit(screen, gameObjectPosition, new Vec2(0, 0), Color.RED());
                            root.addChild(pixelObject);
                        }
                        this.screen.updateMousePosition(event.position_x, event.position_y);
                        break;
                    case "mousePressed":
                        if (event.keyText.compareTo("1") == 0) {
                            isMousePressed = !isMousePressed;
                        } else if (event.keyText.compareTo("3") == 0) {
                            // right click
                        } else if (event.keyText.compareTo("2") == 0) {
                            // "Middle Mouse
                        }
                        break;
                    case "mouseReleased":
                        isMousePressed = !isMousePressed;
                        break;
                    case "keyPressed":
                        if (event.keyText.equals("W")) {
                            Vec2 position = root.getWorldPosition();
                            position.add(new Vec2(0, cameraSpeed));
                            root.setPosition(position);
                        }
                        if (event.keyText.equals("A")) {
                            Vec2 position = root.getWorldPosition();
                            position.add(new Vec2(cameraSpeed, 0));
                            root.setPosition(position);
                        }
                        if (event.keyText.equals("S")) {
                            Vec2 position = root.getWorldPosition();
                            position.add(new Vec2(0, -cameraSpeed));
                            root.setPosition(position);
                        }
                        if (event.keyText.equals("D")) {
                            Vec2 position = root.getWorldPosition();
                            position.add(new Vec2(-cameraSpeed, 0));
                            root.setPosition(position);
                        }
                        System.out.println(event.keyText);
                        break;
                    default: {
                        System.out.println("Not Implemented: " + event.keyText);
                    }
                }
            }
        }
    }

    public void composeFrame(long currentTime) {
        // Clear the screen buffer.
        screen.clearFrame();

        // Clear the dead units
        //        composables.removeIf((e) -> e.isDead());
        screen.sudoShader(currentTime);

        root.compose();


    }
}
