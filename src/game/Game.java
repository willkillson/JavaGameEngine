package game;

import graphics.Color;
import graphics.Screen;
import graphics.Vec2;
import graphics.hex.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import input.InputEvent;

import game.Unit.GameObject;
import game.Unit.WorldHexGrid;
import game.Unit.PixelUnit;

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
        //create some flying pixel objects
        for(int i = 0;i< 100000;i++){
            gameObjects.add(
                new PixelUnit(screen,new Vec2(screen.width*Math.random(),screen.height*Math.random()), 
                new Vec2(1*Math.random(),10*Math.random()), 
                new Color("RANDOM",(int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())))
            );
        }
    }

    public void update(){

        // Handles events
        this.processEventQue();

        for(GameObject u: gameObjects){
            u.update();
        }
    }

    private void processEventQue(){
        if(eventQue.size()>0){
            while(eventQue.size()>0){
                InputEvent event = eventQue.pop();
                switch(event.name){
                    case "mousePressed":
                        if(event.keyText.compareTo("1")==0){
                            //left click
                            this.whg.createHex(new Point(event.position_x, event.position_y));
                        }else if(event.keyText.compareTo("3")==0){
                            //right click
                            this.whg.deleteHex(new Point(event.position_x, event.position_y));
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
                }
            }
        }
    }

    public void composeFrame(){

        // Clear the screen buffer.
        screen.clearFrame();
   
        // Sort the game objects so they render according to their priorities
        Collections.sort(this.gameObjects);

        for(GameObject u: gameObjects){
            u.compose();
        }

    }

}


