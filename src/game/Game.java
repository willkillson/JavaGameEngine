package game;


import graphics.Color;
import graphics.Screen;
import graphics.Vec2;
import input.InputEvent;
import input.InputHandler;

import java.util.ArrayList;
import java.util.Stack;

import game.Unit.GameObject;
import game.Unit.Grid;
import game.Unit.PixelUnit;

public class Game {

    private Screen screen;
    private Stack<InputEvent> eventQue;
    private ArrayList<GameObject> gameObjects;

    public Game(Screen screen, InputHandler inputHandler){
        //store the pixel array
        this.screen = screen;
        this.eventQue = inputHandler.eventQue;
        gameObjects = new ArrayList<>();
    }
    public void init(){
        //create some unit objects and sdtore them into an array
        for(int i = 0;i< 10;i++){
            gameObjects.add(
                new PixelUnit(screen,new Vec2(screen.width*Math.random(),screen.height*Math.random()), 
                new Vec2(1*Math.random(),10*Math.random()), 
                new Color("RANDOM",(int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())))
            );
        }
        gameObjects.add(new Grid(screen));

    }

    public Vec2 getScreenCordinates(Vec2 mouseCords){
        // public static int width = 600;
        // public static int height = width / 16 * 9;
        // public static int scale = 3;

                
        double mouseX = mouseCords.x;
        double mouseY = mouseCords.y;
        return new Vec2(mouseX, mouseY);
    }

    public void update(){

        if(eventQue.size()>0){
            while(eventQue.size()>0){
                InputEvent event = eventQue.pop();
    
                switch(event.name){
                    case "mousePressed":
                    for(GameObject u: gameObjects){
                        //Vec2 newPos = getScreenCordinates(new Vec2(event.position_x,event.position_y));
                        //u.rePosition(newPos);
                    }
                    break;
                    case "mouseReleased":
                    break;
                }
            }
        }else{
            for(GameObject u: gameObjects){
                u.update();
            }
        }
    }

    public void composeFrame(){
        System.out.println("CurrentEvents: " + this.eventQue.size());

        screen.clearFrame();
        for(GameObject u: gameObjects){
            u.compose();
        }

    }




}


