package graphics.shaders;

import graphics.Screen;
import graphics.vec.Vec2;
import graphics.vec.Vec4;

public abstract class FragmentShader extends Thread {

    public static Vec2 resolution;
    public static Vec2 currentMousePosition;
    public Screen screen;
    public int threadNumber;

    public FragmentShader(Screen screen, int threadNumber,Vec2 resolution, Vec2 currentMousePosition){
        this.screen = screen;
        this.threadNumber = threadNumber;
        this.resolution = resolution;
        this.currentMousePosition = currentMousePosition;
    }

    public abstract Vec4 frag(Vec2 fragCoord);

    public abstract void run();

    /**
     * https://en.wikipedia.org/wiki/Smoothstep
     * @param edge0
     * @param edge1
     * @param x
     * @return
     */
    public static double smoothStep (double edge0, double edge1, double x){
        if (x < edge0)
            return 0;

        if (x >= edge1)
            return 1;

        // Scale/bias into [0..1] range
        x = (x - edge0) / (edge1 - edge0);

        return x * x * (3 - 2 * x);
    }

    public static double distance(Vec2 p1, Vec2 p2){
        return Math.sqrt(Math.pow(p2.x - p1.x,2) + Math.pow(p2.y-p1.y,2));
    }

    public static double circle(Vec2 uv, Vec2 position, double r, double blur){
        double d = uv.min(position).length();
        double c = smoothStep(r,r-blur, d);
        return c;
    }


}
