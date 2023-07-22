package graphics.shaders.concrete;

import graphics.Screen;
import graphics.vec.Vec2;
import graphics.vec.Vec4;
import graphics.shaders.FragmentShader;

import java.util.concurrent.CountDownLatch;

import static graphics.vec.VecMath.smoothStep;

public class FragmentCircleDemo extends FragmentShader {


    public FragmentCircleDemo(
            Screen screen,
            int threadNumber,
            int threadCount,
            Vec2 resolution,
            Vec2 currentMousePosition,
            long time) {
        super(screen, threadNumber,threadCount, resolution, currentMousePosition, time);
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {

        // Fix aspect ratio so it is square.
        fragCoord.x /= FragmentShader.resolution.x;
        fragCoord.y /= FragmentShader.resolution.y;
        double ratio = (double) FragmentShader.resolution.x / FragmentShader.resolution.y;
        fragCoord.x *= ratio;
        // Set UV coordinates between [-1,1]
        fragCoord.y -= 0.50;
        fragCoord.x -= (0.5*ratio);

        // Get mouse coordinates, normalize them to [-1,1] and put them in the center
        Vec2 currentMousePosition = this.currentMousePosition.div(resolution);
        currentMousePosition.x *= ratio;
        currentMousePosition.y -= 0.50;
        currentMousePosition.x -= (0.5*ratio);

        double circle = circle(fragCoord,new Vec2(0,0),currentMousePosition.x,currentMousePosition.y);
        Vec4 out = new Vec4(1-circle,0.0,0.0,1.0);

        return out;
    }

    public double circle(Vec2 uv, Vec2 position, double r, double blur){
        double d = uv.min(position).length();
        double c = smoothStep(r,r-blur, d);
        return c;
    }
}
