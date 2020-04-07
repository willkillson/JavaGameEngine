package game;

import graphics.Screen;

import java.util.ArrayList;

public class Game {

    private Screen screen;

    private ArrayList<Unit> units;

    public Game(Screen screen){
        //store the pixel array
        this.screen = screen;
    }



    public void init(){
        //create some unit objects and store them into an array

        units = new ArrayList<>();
        for(int i = 0;i< 10;i++){
            units.add(new Unit(screen));
        }

    }

    public void update(){
        for(Unit u: units){
            u.update();
        }
    }

    public void composeFrame(){

        screen.clearFrame();
        for(Unit u: units){
            u.compose();
        }

    }




}


