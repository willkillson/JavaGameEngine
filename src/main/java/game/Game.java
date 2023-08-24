package game;

import graphics.Screen;
import graphics.hex.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import game.entity.GameObject;
import game.entity.WorldHexGrid;
import input.InputEvent;

public class Game {

    private Screen screen;
    private Stack<InputEvent> eventQue;
    private ArrayList<GameObject> gameObjects;
    private WorldHexGrid whg;

    public Game(Screen screen, Stack<InputEvent> eventQue){
        // Store the pixel array
        this.screen = screen;
        this.eventQue = eventQue;
        gameObjects = new ArrayList<>();
    }
    
    public void init(){
        //grid setup
        this.whg = new WorldHexGrid(screen);
        gameObjects.add(this.whg);
    }

    public void update(){

        // Handles events
        this.processEventQue();

        for(GameObject u: gameObjects){
//            u.update();
        }
    }

    private void processEventQue(){
        if(eventQue.size()>0){
            while(eventQue.size()>0){
                InputEvent event = eventQue.pop();
                switch(event.name){
                    case "mouseMoved":
                        this.screen.updateMousePosition(event.position_x,event.position_y);
                        break;
                    case "mousePressed":
                        if(event.keyText.compareTo("1")==0){
                            //left click
                            this.whg.createHex(new Point(event.position_x, event.position_y),this.whg.selectedHexType);
                        }else if(event.keyText.compareTo("3")==0){
                            //right click
                            this.whg.deleteHex(new Point(event.position_x, event.position_y),gameObjects);
                        } 
                        break;
                    case "mouseReleased":
                        break;
                    case "keyPressed":
                        if(event.keyText=="NumPad +"){
                            this.whg.changeSize(1);
                        }
                        if(event.keyText=="NumPad -"){
                            this.whg.changeSize(-1);
                        }
                        if(event.keyText.compareTo("NumPad-9")==0){
                            this.whg.changeOrientationPointy();
                        }
                        if(event.keyText.compareTo("NumPad-8")==0){
                            this.whg.changeOrientationFlat();
                        }
                        break;
                    case "select":{
                        this.whg.selectedHexType = event.keyText;
                        break;
                    }
                    case "save":{
                        this.whg.saveMap(event.keyText);
                        break;
                    }
                    case "load":{
                        this.whg.loadMap(event.keyText);
                        break;
                    }
                }
            }
        }
    }

    public void composeFrame(){


//        // Clear the screen buffer.
        screen.clearFrame();
//
//
        // Clear the dead units
        gameObjects.removeIf((e)->{
            return e.isDead();
        });

        // Sort the game objects so they render according to their priorities
        Collections.sort(this.gameObjects);

        for(GameObject u: gameObjects){
            u.compose();
        }
        screen.sudoShader();


    }

}


