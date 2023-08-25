package graphics.shaders.concrete;

import graphics.Color;
import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec4;

public class Pixel extends FragmentShader {
    private Color color;

    public Pixel(Screen screen, int threadNumber, int threadCount, Vec2 resolution, Vec2 position, long time, Color color) {
        super(screen, threadNumber, threadCount, resolution, position, time);
        this.color = color;
    }

    @Override
    public Vec4 frag(Vec2 fragCoord) {
        return new Vec4(color.r,color.g,color.b, 0);
    }
}
