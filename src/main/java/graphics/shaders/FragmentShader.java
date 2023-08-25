package graphics.shaders;

import graphics.Color;
import graphics.Screen;
import graphics.vec.Vec2;
import graphics.vec.Vec4;

public abstract class FragmentShader extends Thread {

    public static Vec2 resolution;
    public static Vec2 position;
    public Screen screen;
    public int threadNumber;
    public int threadCount;
    public long time;

    public FragmentShader(Screen screen, int threadNumber, int threadCount, Vec2 resolution, Vec2 position, long time) {
        this.screen = screen;
        this.threadNumber = threadNumber;
        this.threadCount = threadCount;
        this.resolution = resolution;
        // Adjust the position against the resolution so
        // that the center is in the middle of the shader instead of the top left.
        this.position = new Vec2(position.x - resolution.x / 2, position.y - resolution.y / 2);
        this.time = time;
    }

    public abstract Vec4 frag(Vec2 fragCoord);

    public synchronized void run() {
        for (int i = (int) (resolution.x / threadCount * (threadNumber - 1));
                i < resolution.x / threadCount * threadNumber;
                i++) {
            for (int j = 0; j < resolution.y; j++) {
                Vec4 out = frag(new Vec2(i, j));
                Color color = new Color("Shader", (int) (out.x * 255), (int) (out.y * 255), (int) (out.z * 255));
                screen.putPixel(i + (int) position.x, j + (int) position.y, color, false);
            }
        }
    }
}
