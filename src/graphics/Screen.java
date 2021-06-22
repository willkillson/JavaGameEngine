package graphics;

import java.util.ArrayList;

public class Screen {

    public int width;
    public int height;
    public int[] pixels;

    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width*height];
    }

    public void clearFrame(){
        for(int i = 0;i<   pixels.length;i++){
            pixels[i] = 0;
        }
    }

    public void plotLine(int x0, int y0,int x1, int y1,Color c){
      plotLine(x0,y0,x1,y1,c.r,c.g,c.b);
    }

    public void plotLine(int x0, int y0,int x1, int y1,int r, int g, int b ){
        /*
            Bresenham's line aalgorithm
            https://en.wikipedia.org/wiki/Bresenham's_line_algorithm
         */

        if(Math.abs(y1-y0)<Math.abs(x1-x0))
        {
            if(x0>x1){
                plotLineLow(x1,y1,x0,y0,r,g,b);
            }
            else
            {
                plotLineLow(x0,y0,x1,y1,r,g,b);
            }
        }
        else
        {
            if(y0>y1){
                plotLineHigh(x1,y1,x0,y0,r,g,b);
            }
            else
            {
                plotLineHigh(x0,y0,x1,y1,r,g,b);
            }
        }


    }

    public void plotLineLow(int x0, int y0, int x1, int y1, int r, int g, int b){
        double dx = x1-x0;
        double dy = y1-y0;
        double yi = 1;
        if(dy<0){
            yi = -1;
            dy = -dy;
        }
        double D =  2*dy - dx;
        double y = y0;
        for(int x = x0;x<x1;x++){
            putPixel((int)x,(int)y,r,g,b);
            if(D>0){
                y = y+yi;
                D = D-2*dx;
            }
            D = D+2*dy;
        }

    }

    public void plotLineHigh(int x0, int y0, int x1, int y1, int r, int g, int b){
        double dx = x1-x0;
        double dy = y1-y0;
        double xi = 1;
        if(dx<0){
            xi = -1;
            dx = -dx;
        }
        double D =  2*dx - dy;
        double x = x0;
        for(int y= y0;y<y1;y++){
            putPixel((int)x,(int)y,r,g,b);
            if(D>0){
                x = x+xi;
                D = D-2*dy;
            }
            D = D+2*dx;
        }

    }

    public void drawRect(Vec2 p1, Vec2 p2,int r, int g, int b){
        drawRect((int)p1.x,(int)p1.y,(int)p2.x,(int)p2.y,r,g,b);
    }

    public void drawRect(int x0, int y0, int x, int y, int r, int g, int b){
        for(int i = x0;i< x;i++){
            for(int j = y0;j< y;j++){
                putPixel(i,j,r,g,b);
            }
        }
    }

    public void drawRect(int x0, int y0, int x, int y, Color c){
        for(int i = x0;i< x;i++){
            for(int j = y0;j< y;j++){
                putPixel(i,j,c.r,c.g,c.b);
            }
        }
    }

    public void putPixel(Vec2 point, int r, int g, int b){
        putPixel((int)point.x,(int)point.y,r,g,b);
    }

    public void putPixel(int x, int y, int r, int g, int b){

        int number = r<< 8;
        number = number + g << 8;
        number = number + b;
        pixels[x + y * width] =  number;

    }

    public void putPixel(int x, int y, Color c) {
        putPixel(x,y,c.r,c.g,c.b);
    }   

    public void putHexagon(Vec2 center, int size, Color c){
        

        Color red = new Color("Red", 255,0,0);
        Color green = new Color("Green", 0,255,0);
        Color blue = new Color("Blue", 0,0,255);

        ArrayList<Vec2> verticies = new ArrayList();
        for(int i = 0;i<6;i++){
            plotLine(
                (int)center.x, 
                (int)center.y, 
                (int)(center.x+ (size *  Math.cos(Math.PI/3*i))), 
                (int)(center.y+ (size *  Math.sin(Math.PI/3*i))), 
                c);
            verticies.add(
                new Vec2(
                center.x+ (size *  Math.cos(Math.PI/3*i)),
                center.y+ (size *  Math.sin(Math.PI/3*i)))
                );
        }
        verticies.add(
            new Vec2(
            center.x+ (size ),
            center.y+ (Math.sin(Math.PI/3)))
            );

        for(int i = 0;i< 6;i++){
            plotLine(
                (int)verticies.get(i).x,
                (int)verticies.get(i).y,
                (int)(verticies.get(i+1).x),
                (int)(verticies.get(i+1).y),
                 c);
        }

    }



}
