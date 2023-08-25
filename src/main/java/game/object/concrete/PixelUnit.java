package game.object.concrete;

import game.object.GameObject;
import graphics.Color;
import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.shaders.concrete.Pixel;
import graphics.shaders.concrete.smiley.Circle;
import graphics.vec.Vec2;

import java.util.ArrayList;

public class PixelUnit extends GameObject {

    private Vec2 velocity;

    private Color color;

    private Vec2 traveled;
    private Vec2 boundary;

    private boolean isAnimating;

    public PixelUnit(Screen screen, Vec2 position, Vec2 velocity, Color color) {
        super(position, screen);
        this.setPosition(position);
        double r1 = Math.random();
        double r2 = Math.random();

        double ur = 1.0;
        if (r1 > 0.5) {
            ur = -1.0;
        }
        double lr = 1.0;
        if (r2 > 0.5) {
            lr = -1.0;
        }

        this.velocity = new Vec2(r1 * 2 * ur+0.5, r2 * 2 * lr+0.5);
        this.color = color;

        // used to test when to destroy this pixel unit.
        this.traveled = new Vec2(0,0);
        this.boundary = new Vec2(100,100);
    }

    @Override
    public void update() {
        super.children.forEach((child) -> child.update());
        Vec2 newPosition = this.getLocalPosition();
        newPosition.add(velocity);
        traveled.add(velocity);
        this.setPosition(newPosition);

        if(Math.abs(this.traveled.x)>this.boundary.x ||
        Math.abs(this.traveled.y)>this.boundary.y){
            this.setAlive(false);
        }

    }

    @Override
    public void compose() {
        super.children.forEach((child) -> child.compose());
        Vec2 shaderResolution = new Vec2(5, 5);

        int threadCount = 1;

        ArrayList<FragmentShader> fragmentShaderThreads = new ArrayList<>();
        // Assign each thread its thread number.
        for (int i = 1; i <= threadCount; i++) {
            FragmentShader fragmentShader =
                    new Pixel(screen, i, threadCount, shaderResolution, this.getWorldPosition(), 0,color);
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
//        this.screen.putPixel(
//                (int) this.getWorldPosition().x,
//                (int) this.getWorldPosition().y,
//                this.color.r,
//                this.color.g,
//                this.color.b,
//                false);
    }
}
