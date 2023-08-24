package graphics.shaders.concrete;

import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec4;
import graphics.vec.VecMath;

public class ColorDemo extends FragmentShader {
    public ColorDemo(
            Screen screen, int threadNumber, int threadCount, Vec2 resolution, Vec2 currentMousePosition, long time) {
        super(screen, threadNumber, threadCount, resolution, currentMousePosition, time);
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {
        fragCoord = VecMath.rot(fragCoord,Math.sin(time/1000d));
        Vec2 uv = fragCoord.div(resolution.y);


        uv = uv.min(0.5);
        double num1 = Math.abs(Math.sin(time/1000d));
        double num2 = Math.abs(Math.cos(time/1000d));
        uv.add(new Vec2(uv.x*num1,uv.y*num2));
        return new Vec4(uv.x, uv.y, 0, 0);
    }
}
