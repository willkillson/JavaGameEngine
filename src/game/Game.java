package game;

import graphics.SpriteLibrary;
import graphics.SpriteSet;
import graphics.hex.Layout;
import graphics.hex.Orientation;
import graphics.hex.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import game.entity.Entity;
import game.entity.HexGrid;
import game.entity.UnitEntity;
import game.gamegfx.Screen;
import game.gamegfx.Screen;
import game.gamegfx.SpriteManager;
import input.InputEvent;
import util.Constants;

public class Game {

    public static final int SPRITE_SIZE = 64;

    private ArrayList<Entity> gameEntities;
    private HexGrid hexGrid;
    private String selectedHexName;

    private SpriteManager spriteManager;
    private Screen screenLayers;
    private Stack<InputEvent> eventQue;
    private SpriteLibrary spriteLibrary;

    public Game(Screen screenLayers, Stack<InputEvent> eventQue){

        /*
            Layer 0 -> hexs
            Layer 1 -> player pieces
            Layer 2 -> pixel graphics
        */
        this.screenLayers = screenLayers;
        this.eventQue = eventQue;

    }
    
    public void init(){

        this.spriteLibrary = new SpriteLibrary();

        this.selectedHexName = "water_hex";
        
        Point size = new Point(35.0d,35.0d);
        
        Point origin = new Point(Constants.WIDTH/2, Constants.HEIGHT/2);
        Orientation orientation = Orientation.layoutPointy();

        this.spriteManager = new SpriteManager(size);
        gameEntities = new ArrayList<>();

        //grid setup
        this.hexGrid = new HexGrid(
            size,
            orientation,
            origin);

        gameEntities.add(this.hexGrid);

        UnitEntity ue = new UnitEntity(this.spriteLibrary, (double)Constants.WIDTH/2, (double)Constants.HEIGHT/2);

        gameEntities.add(ue);
    }

    public void update(){

        // Handles events
        this.processEventQue();

        for(Entity u: gameEntities){
            u.update();
        }
    }

    private void processEventQue(){
        if(eventQue.size()>0){
            while(eventQue.size()>0){
                InputEvent event = eventQue.pop();
                switch(event.name){
                    case "mousePressed":
                        {
                            Point point = new Point(event.position_x, event.position_y);
                            


                            if(event.keyText.compareTo("1")==0){    //left click
                                String spriteName = this.selectedHexName;
                                this.hexGrid.createHex(point,spriteName, spriteManager, screenLayers);
                            }else if(event.keyText.compareTo("3")==0){  //right click

                                //this.hexGrid.deleteHex(point,pixelEntities);
                            } 
                        }
                        break;
                    case "mouseReleased":
                        break;
                    case "keyPressed":
                        // if(event.keyText=="NumPad +"){
                        //     this.hexGrid.changeSize(1);
                        // }
                        // if(event.keyText=="NumPad -"){
                        //     this.hexGrid.changeSize(-1);
                        // }
                        // if(event.keyText.compareTo("NumPad-9")==0){
                        //     this.hexGrid.changeOrientationPointy();
                        // }
                        // if(event.keyText.compareTo("NumPad-8")==0){
                        //     this.hexGrid.changeOrientationFlat();
                        // }
                        break;
                    case "select":{
                        // this.hexGrid.selectedHexType = event.keyText;
                        break;
                    }
                    case "save":{
                        // this.hexGrid.saveMap(event.keyText);
                        break;
                    }
                    case "load":{
                        // this.hexGrid.loadMap(event.keyText);
                        break;
                    }
                }
            }
        }
    }

    public void composeFrame(){

        // // Clear the screen buffer.
        // screen.clearFrame();

        // // Clear the dead units
        gameEntities.removeIf((e)->{
            return e.isDead();
        });

        // pixelEntities.removeIf((e)->{
        //     return e.isDead();
        // });
        
        // // Sort the game objects so they render according to their priorities
        // Collections.sort(this.gameEntities);

        for(Entity e: gameEntities){
            e.compose(this.screenLayers, this.spriteManager);
        }

        

        // for(Entity e: pixelEntities){
        //     e.compose();
        // }

        this.screenLayers.paintLayers();
    }

}


