package graphics.shaders.concrete.smiley;

import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec4;
import graphics.vec.VecMath;

public class Circle extends FragmentShader {

    public Circle(
            Screen screen, int threadNumber, int threadCount, Vec2 resolution, Vec2 currentMousePosition, long time) {
        super(screen, threadNumber, threadCount, resolution, currentMousePosition, time);
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {

        Vec2 uv = fragCoord.div(resolution); // [0,1]
        assert uv.y <= 1 : "y dem is incorrect";
        assert uv.y >= 0 : "y dem is incorrect";
        assert uv.x <= 1 : "x dem is incorrect";
        assert uv.x >= 0 : "x dem is incorrect";

        uv = uv.min(0.5); // [-0.5, 0.5]
        assert uv.y <= 0.5 : "y dem is incorrect";
        assert uv.y >= -0.5 : "y dem is incorrect";
        assert uv.x <= 0.5 : "x dem is incorrect";
        assert uv.x >= -0.5 : "x dem is incorrect";

        uv.x *= resolution.x / resolution.y;

        double d = uv.length();
        double r = 0.5;

        double c = 1 - VecMath.smoothStep(r, r - position.x / 1000, d);

        Vec4 out = new Vec4(c, c, c, 0);
        return out;
    }
}
