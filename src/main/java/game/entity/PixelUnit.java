package game.entity;

import game.GameObject;
import graphics.Color;
import graphics.Screen;
import graphics.vec.Vec2;

public class PixelUnit extends GameObject {

    private Screen screen;

    public boolean isDead;

    private Vec2 velocity;

    private Color color;

    public PixelUnit(Screen screen, Vec2 position, Vec2 velocity, Color rgb) {
        super();
        this.screen = screen;
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

    public PixelUnit(Screen screen, Vec2 position, Vec2 velocity, int intRGB) {
        super();

        this.screen = screen;
        this.setPosition(position);
        this.velocity = new Vec2(velocity.x, velocity.y);

        int red = (intRGB >> 16) & 0xFF;
        int green = (intRGB >> 8) & 0xFF;
        int blue = intRGB & 0xFF;

        this.color = new Color("color", red, green, blue);

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

        screen.putPixel(
                (int) this.getWorldPosition().x,
                (int) this.getWorldPosition().y,
                this.color.r,
                this.color.g,
                this.color.b,
                false);
    }

    //    @Override
    //    public void kill() {
    //        double random = Math.random();
    //        int y;
    //        int x;
    //        if(random > 0.5){
    //            y = -1;
    //        }else{
    //            y = 1;
    //        }
    //        random = Math.random();
    //        if(random > 0.5){
    //            x = -1;
    //        }else{
    //            x = 1;
    //        }
    //        this.vx = x*Math.random()*5 + 1;
    //        this.vy = y*Math.random()*5 + 1;
    //    }
}
