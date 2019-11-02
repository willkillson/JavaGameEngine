package com.company.game;

import com.company.graphics.Color;
import com.company.graphics.Screen;

public class Unit{

    private Screen screen;

    private int boundX;
    private int boundY;

    private double posX;//topLeftmost point of bound
    private double posY;//topLeftmost point of bound

    private double vx;
    private double vy;

    Color color;

    public Unit(Screen screen){
        this.screen = screen;

        this.posX = 0;
        this.posY = 0;

        this.boundX = (int) ((screen.height-1)*Math.random());
        this.boundY = (int) ((screen.height-1)*Math.random());

        this.vx = Math.random()*0.1;
        this.vy = Math.random()*0.1;

        color = new Color("RANDOM",(int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));

    }

    public void update(){

        this.posX += vx;
        this.posY+= vy;

        if(this.posX+this.boundX>screen.width){
            this.vx = this.vx*-1;
            while(this.posX+this.boundX>screen.width){
                this.posX= this.posX+vx*2;
            }
        }
        if(this.posX<0){
            this.vx = this.vx*-1;
            while(this.posX<0){
                this.posX= this.posX+vx*2;
            }
        }



        if(this.posY>=screen.height-boundY-1){
            vy = -vy;
            this.posY= this.posY+vy*2;
        }
        if(this.posY<0){
            vy = -vy;
            this.posY = this.posY+vy*2;
        }


    }

    public void compose(){
        for(int x = (int)this.posX;x< (int)this.posX+this.boundX;x++){
            for(int y = (int)this.posY;y<this.posY+this.boundY;y++){
                screen.putPixel(x,y,color);
            }
        }

    }

}