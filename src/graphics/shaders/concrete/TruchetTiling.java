package graphics.shaders.concrete;

import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec3;
import graphics.vec.Vec4;

import static graphics.vec.VecMath.*;
import static java.lang.Math.*;

public class TruchetTiling extends FragmentShader {

    public TruchetTiling(Screen screen, int threadNumber, Vec2 resolution, Vec2 currentMousePosition, long time) {
        super(screen, threadNumber, resolution, currentMousePosition, time);
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {
        Vec2 uv = fragCoord.div(resolution);
        Vec3 col = new Vec3(0);

        uv = uv.mul(10);
        Vec2 gv = fractional(uv).min(0.5);
        Vec2 id = new Vec2(Math.floor(uv.x), Math.floor(uv.y));
        // returns a random number [0,1]
        double n = hash21(id);

        double width = 0.01;
        // Random Flipping
        if(n <.5){
            gv.x *= -1;
        }

        double d= abs(gv.x+gv.y);
        double mask = 1-smoothStep(0.01,-0.01, d-width);
        col = col.add(mask);

        //// Draws boxes around
        //if ((gv.x >.48)||(gv.y>.48)){
        //    col = col.add(new Vec3(1,0,0));
        //}
        return new Vec4(col,1.0);
    }

    double hash21(Vec2 p){
        p = fractional(p.mul(new Vec2(234.34, 435.345)));
        p = p.add(dot(p,p.add(34.23)));
        return fractional(p.x*p.y);
    }

}
