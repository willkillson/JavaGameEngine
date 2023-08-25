package game.object.concrete;

import game.object.GameObject;
import graphics.Color;
import graphics.Screen;
import graphics.vec.Vec2;

public class PixelUnit extends GameObject {

    public boolean isDead;

    private Vec2 velocity;

    private Color color;

    public PixelUnit(Screen screen, Vec2 position, Vec2 velocity, Color rgb) {
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

        this.velocity = new Vec2(r1 * 2 * ur, r2 * 2 * lr);
        this.color = rgb;
        this.isDead = false;
    }

    @Override
    public void update() {
        super.children.forEach((child) -> child.update());

        Vec2 newPosition = this.getLocalPosition();
        newPosition.add(velocity);
        this.setPosition(newPosition);
    }

    @Override
    public void compose() {
        super.children.forEach((child) -> child.compose());
        this.screen.putPixel(
                (int) this.getWorldPosition().x,
                (int) this.getWorldPosition().y,
                this.color.r,
                this.color.g,
                this.color.b,
                false);
    }
}
