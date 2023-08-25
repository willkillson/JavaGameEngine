package game;

import game.object.GameObject;
import game.object.concrete.Colors;
import game.object.concrete.PixelUnit;
import game.object.concrete.Player;
import graphics.Color;
import graphics.Screen;
import graphics.vec.Vec2;
import input.InputEvent;
import java.util.Stack;
import java.util.UUID;

public class Game {

    private GameObject root;
    private Screen screen;
    private Stack<InputEvent> eventQue;

    private int cameraSpeed = 20;

    private boolean isMousePressed = false;
    private UUID player;

    public Game(Screen screen, Stack<InputEvent> eventQue) {
        // Store the pixel array
        this.screen = screen;
        this.root = new GameObject(new Vec2(0, 0), this.screen);
        this.eventQue = eventQue;
        init();
    }

    private void init() {

        GameObject player = new Player(new Vec2(screen.width / 2, screen.height / 2), this.screen);
        GameObject colors = new Colors(new Vec2(screen.width / 2, screen.height / 2), this.screen);

        this.root.addChild(player);
        this.root.addChild(colors);

        this.player = player.getUuid();
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

                        // Player movement
                        if (event.keyText.equals("Left")) {
                            GameObject playerGameObject = root.getGameObject(player);
                            playerGameObject.translatePosition(new Vec2(-5, 0));
                        }
                        if (event.keyText.equals("Right")) {
                            GameObject playerGameObject = root.getGameObject(player);
                            playerGameObject.translatePosition(new Vec2(5, 0));
                        }
                        if (event.keyText.equals("Up")) {
                            GameObject playerGameObject = root.getGameObject(player);
                            playerGameObject.translatePosition(new Vec2(0, 5));
                        }
                        if (event.keyText.equals("Down")) {
                            GameObject playerGameObject = root.getGameObject(player);
                            playerGameObject.translatePosition(new Vec2(0, -5));
                        }

                        // Kill player

                        if (event.keyText.equals("NumPad-5")) {
                            GameObject playerGameObject = root.getGameObject(player);
                            playerGameObject.destroy();
                        }
                        if (event.keyText.equals("NumPad-9")) {
                            root.destroy();
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

        root.compose();
    }
}
