package graphics.shaders;

import graphics.Color;
import graphics.Screen;
import graphics.vec.Vec2;
import graphics.vec.Vec4;

import java.util.concurrent.CountDownLatch;

public abstract class FragmentShader extends Thread {

    public static Vec2 resolution;
    public static Vec2 currentMousePosition;
    public Screen screen;
    public int totalThreads;
    public int threadNumber;
    public long time;

    public FragmentShader(Screen screen,int totalThreads, int threadNumber, Vec2 resolution, Vec2 currentMousePosition, long time){
        this.screen = screen;
        this.threadNumber = threadNumber;
        this.resolution = resolution;
        this.currentMousePosition = currentMousePosition;
        this.time = time;
        this.totalThreads = totalThreads;
    }

    public abstract Vec4 frag(Vec2 fragCoord);

    public synchronized void run() {
        // totalThreads = 2
        // num 0 -> 0 to width/2
        // num 1 -> width/2 -> width

        // totalThreads = 4
        // num 0 -> 0 to width * 0.25
        // num 1 -> width * 0.25 to width * 0.50
        // num 2 -> width *0.50 to width * 0.75
        // num 4 -> width * 0.75 to width * 1

        int i = (int)((double)threadNumber/(double)totalThreads * screen.width);
        double ratio = ((double)threadNumber+1.0/(double)totalThreads);
        int end_i = (int)(screen.width*ratio);
        for(; i< end_i; i++){
            for(int j = 0;j< screen.height;j++){
                Vec4 out = frag(new Vec2(i,j));
                screen.putPixel(
                        i,
                        j,
                        new Color("Shader",
                                (int)(out.x*255),
                                (int)(out.y*255),
                                (int)(out.z*255)));
            }
        }

    }

}
