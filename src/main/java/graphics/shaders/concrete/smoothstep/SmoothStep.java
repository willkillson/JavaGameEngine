package graphics.shaders.concrete.smoothstep;

import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec3;
import graphics.vec.Vec4;
import graphics.vec.VecMath;

/**
 * Start with a parabola,
 * a = x^2
 * Take another parabola,
 * x^2
 * Now shift it to the right,
 * (x-1)^2
 * Invert it,
 * -(x-1)^2
 * Now shift it up,
 * b = 1-(x-1)^2
 *
 * Linearly interpolate both functions a,b
 * a(1-x)+bx, and we get
 * x^2(1-x)+(1-(x-1)^2)x
 * Simplified,
 *  it becomes x * x * (3 - 2 * x)
 *
 * This is the basis for smoothstep functionality.
 */
public class SmoothStep extends FragmentShader {

    public SmoothStep(
            Screen screen, int threadNumber, int threadCount, Vec2 resolution, Vec2 currentMousePosition, long time) {
        super(screen, threadNumber, threadCount, resolution, currentMousePosition, time);
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {
        Vec2 uv = fragCoord.div(resolution); // [0,1]
        Vec3 c = new Vec3(0);

        double m = VecMath.GLSLSmoothStep(position.x / 1000, position.y / 1000, uv.x);
        c = c.add(m);

        return new Vec4(c, 0.0);
    }
}
