package game.object.concrete;

import game.object.GameObject;
import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.shaders.concrete.ColorDemo;
import graphics.shaders.concrete.smiley.Circle;
import graphics.vec.Vec2;

import java.util.ArrayList;

public class Colors extends GameObject {

    public Colors(Vec2 position, Screen screen) {
        super(position, screen);
    }

    @Override
    public void compose() {
        if (this.isAlive()) {
            Vec2 shaderResolution = new Vec2(500, 500);

            int threadCount = 4;

            ArrayList<FragmentShader> fragmentShaderThreads = new ArrayList<>();
            // Assign each thread its thread number.
            for (int i = 1; i <= threadCount; i++) {
                FragmentShader fragmentShader =
                        new ColorDemo(screen, i, threadCount, shaderResolution, this.getWorldPosition(), 0);
                fragmentShaderThreads.add(fragmentShader);
            }

            // Start the threads
            fragmentShaderThreads.forEach((thread) -> {
                thread.start();
            });

            // Join all the threads.
            fragmentShaderThreads.forEach((thread) -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void destroy() {
        this.setAlive(false);
    }
}
