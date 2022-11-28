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
    public int threadNumber;
    public long time;

    public FragmentShader(Screen screen, int threadNumber, Vec2 resolution, Vec2 currentMousePosition, long time){
        this.screen = screen;
        this.threadNumber = threadNumber;
        this.resolution = resolution;
        this.currentMousePosition = currentMousePosition;
        this.time = time;
    }

    public abstract Vec4 frag(Vec2 fragCoord);

    public synchronized void run() {
        if(threadNumber==1){
            for(int i = 0;i< screen.width;i++){
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

}
