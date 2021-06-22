package game;


import graphics.Color;
import graphics.Screen;
import graphics.Vec2;
import graphics.hex.Hex;
import graphics.hex.Layout;
import graphics.hex.Orientation;
import graphics.hex.Point;
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

    // Hex grid system
    private Orientation orientation;
    private Point size;
    private Point origin;
    private Layout layout;
    private ArrayList<Hex> hexs;

    public Game(Screen screen, InputHandler inputHandler){
        //store the pixel array
        this.screen = screen;
        this.eventQue = inputHandler.eventQue;
        gameObjects = new ArrayList<>();

    }
    
    public void init(){
        //grid setup
        
        this.orientation = Orientation.layoutPointy();
        this.size = new Point(5.0,5.0);
        this.origin = new Point(screen.width/2.0,screen.height/2.0);
        this.layout = new Layout(orientation,size,origin);

        this.hexs = new ArrayList();
        for(int i = -3;i<= 3;i++){
            for(int j = -3;j<= 3;j++){
                for(int k = -3;k<= 3;k++){
                    Hex hex = new Hex(i,j,k);
                    this.hexs.add(hex);
                }
            }
        }

        //create some unit objects and sdtore them into an array
        for(int i = 0;i< 100;i++){
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
                        Vec2 newPos = getScreenCordinates(new Vec2(event.position_x,event.position_y));
                        System.out.println("mousePressed: "+newPos.x + " : "+newPos.y);
                        this.origin = new Point(newPos.x, newPos.y);
                        this.layout = new Layout(orientation,size,origin);
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
        //System.out.println("CurrentEvents: " + this.eventQue.size());

        screen.clearFrame();

        Color red = new Color("Red", 255,0,0);
        this.hexs.forEach((hex)->{
            screen.putHexagon(hex, layout, origin, size, red);
        });

        for(GameObject u: gameObjects){
            u.compose();
        }

    }

}


