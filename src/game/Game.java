package game;

import graphics.Color;
import graphics.Screen;
import graphics.Vec2;
import input.InputEvent;
import input.InputHandler;

import java.util.ArrayList;
import java.util.Stack;

import game.Unit.PixelUnit;

public class Game {

    private Screen screen;
    private Stack<InputEvent> eventQue;
    private ArrayList<PixelUnit> units;

    public Game(Screen screen, InputHandler inputHandler){
        //store the pixel array
        this.screen = screen;
        this.eventQue = inputHandler.eventQue;
    }



    public void init(){
        //create some unit objects and sdtore them into an array

        units = new ArrayList<>();
        
        for(int i = 0;i< 1000;i++){
            units.add(
                new PixelUnit(screen,new Vec2(screen.width/2,screen.height/2), 
                new Vec2(0.5*Math.random(),0.5*Math.random()), 
                new Color("RANDOM",(int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())))
            );
        }
        // units.add(
        //     new PixelUnit(screen,new Vec2(screen.width/2,screen.height/2), 
        //     new Vec2(0.5*Math.random(),0.5*Math.random()), 
        //     new Color("RANDOM",(int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())))
        // );

    }

    public Vec2 getScreenCordinates(Vec2 mouseCords){
        // public static int width = 600;
        // public static int height = width / 16 * 9;
        // public static int scale = 3;
        double mouseX = mouseCords.x/3;
        double mouseY = mouseCords.y/3;
        return new Vec2(mouseX, mouseY);
    }

    public void update(){

        if(eventQue.size()>0){
            while(eventQue.size()>0){
                InputEvent event = eventQue.pop();
    
                switch(event.name){
                    case "mousePressed":
                    for(PixelUnit u: units){
                        Vec2 newPos = getScreenCordinates(new Vec2(event.position_x,event.position_y));
                        u.rePosition(newPos);
                    }
                    break;
                    case "mouseReleased":
                    break;
                }
            }
        }else{
            for(PixelUnit u: units){
                u.update();
            }
        }
    }

    public void composeFrame(){
        System.out.println("CurrentEvents: " + this.eventQue.size());

        screen.clearFrame();
        for(PixelUnit u: units){
            u.compose();
        }

    }




}


