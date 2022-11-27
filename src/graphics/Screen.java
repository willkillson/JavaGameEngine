package graphics;

import java.util.ArrayList;

import game.entity.HexObject;
import graphics.hex.Layout;
import graphics.hex.Point;

public class Screen {

    public int width;
    public int height;
    public int[]pixels;

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

    public void sudoShader(){
        for(int i = 0;i< this.width/2;i++){
            for(int j = 0;j< this.height/2;j++){
                Vec4 fragmentShader = frag(new Vec2(i,j));
                putPixel(i,j,new Color(
                        "Shader",
                        (int)(fragmentShader.x*255),
                        (int)(fragmentShader.y*255),
                        (int)(fragmentShader.z*255)));
            }
        }

    }
    private double distance(Vec2 p1, Vec2 p2){
        return Math.sqrt(Math.pow(p2.x - p1.x,2) + Math.pow(p2.y-p1.y,2));
    }

    public Vec4 frag(Vec2 uv){
        uv.x /= this.width;
        uv.y /= this.height;
        uv.x *= this.width/this.height;
        uv.y  -= 0.25;
        uv.x -= 0.25;
        double d = distance(uv,new Vec2(0,0));

        return new Vec4(1.0*d,1.0*d,1.0*d,1.0);
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

        if(x<0 || x >= width){
            return;
        }

        if(y<0 || y >= height ){
            return;
        }

        try{
            int number = r<< 8;
            number = number + g << 8;
            number = number + b;
            pixels[x + (y * width)] =  number;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void putPixel(int x, int y, Color c) {
        putPixel(x,y,c.r,c.g,c.b);
    }   

    public void putHexagon(HexObject hex, Layout layout, Point origin, Point size, Color c){
        ArrayList<Point> corners = layout.polygonCorners(hex);
        corners.add(corners.get(0));
        for(int i = 0;i< 6;i++){
            this.plotLine(
                (int)corners.get(i).x,
                (int)corners.get(i).y,
                (int)(corners.get(i+1).x),
                (int)(corners.get(i+1).y),
                c);
        }
    }

    public void movePixels(int[] sourcePixels,int[] destinationPixels, int x_pos, int y_pos, int arrayWidth, int arrayHeight){
        int height = 0;
        int width = 1920;
        int center_x_pos =  x_pos - arrayWidth/2;
        int center_y_pos =  y_pos - arrayHeight/2;
        for(int i = center_x_pos + center_y_pos * width;i<destinationPixels.length;i++){
            if(i% width==0){
                if(height<arrayHeight){ 
                    for(int k = 0;k< arrayWidth;k++){   // Loop f writing in the sourcePixels buffer to the destinationPixels buffer
                        try{
                            if(sourcePixels[k+height*arrayWidth]!=0){ // Prevent writing in empty pixels/alphachannel
                                if(i>0){    // Prevent writing in negative indexs;
                                    if(k+center_x_pos>0){   // prevent wrap around left
                                        if(k+center_x_pos<width){   // prevent wrap around right
                                            destinationPixels[i+k+center_x_pos] = sourcePixels[k+height*arrayWidth];
                                        }
                                    }
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                height++;
                i= i+arrayWidth;
            }
        }
    }

    public int[] blendBuffers(int[] pixelBuffer1, int[] pixelBuffer2, double ratioPixelBuffer1, double ratioPixelBuffer2){
        int blueMask = 0xFF0000;
        int greenMask = 0xFF00;
        int redMask = 0xFF;
        
        int[] blendedPixelBuffer = new int[pixelBuffer1.length];
        for(int i = 0;i< pixelBuffer1.length;i++){
            
            int r1 = pixelBuffer1[i]&redMask;
            int r2 = pixelBuffer2[i]&redMask;

            int g1 = pixelBuffer1[i]&greenMask;
            int g2 = pixelBuffer2[i]&greenMask;

            int b1 = pixelBuffer1[i]&blueMask;
            int b2 = pixelBuffer2[i]&blueMask;

            int final_r = (int)(ratioPixelBuffer1*r1 + ratioPixelBuffer2*r2);
            if(final_r>redMask)
                final_r = redMask;

            int final_g = (int)(ratioPixelBuffer1*g1 + ratioPixelBuffer2*g2);
            if(final_g>greenMask)
                final_g = greenMask;

            int final_b = (int)(ratioPixelBuffer1*b1 + ratioPixelBuffer2*b2);
            if(final_b>blueMask)
                final_b = blueMask;

            int number = final_r << 8;
            number = number + final_g << 8;
            number = number + final_b;
            blendedPixelBuffer[i] = number;

        }
        return blendedPixelBuffer;
    }

}
