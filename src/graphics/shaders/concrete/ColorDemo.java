package graphics.shaders.concrete;

import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec4;

import static graphics.vec.VecMath.rot;

public class ColorDemo extends FragmentShader {


    public ColorDemo(Screen screen, int totalThreads, int threadNumber, Vec2 resolution, Vec2 currentMousePosition, long time) {
        super(screen, totalThreads, threadNumber, resolution, currentMousePosition, time);
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {
        Vec2 uv = fragCoord.div(resolution);
        uv = uv.min(0.5);
        uv = rot(uv,Math.PI/2);
        uv = new Vec2(uv.y,uv.x);
        uv = uv.add(currentMousePosition.div(resolution));
        return new Vec4(uv.x, uv.y, 0,0);
    }
}
