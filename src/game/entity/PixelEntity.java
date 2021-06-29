package game.entity;
import graphics.Vec2;
import game.gamegfx.Screen;
import game.gamegfx.Screen;
import game.gamegfx.SpriteManager;
import graphics.Color;

public class PixelEntity implements Entity {

    private Screen screen;

    public boolean isDead;

    private double posX;//topLeftmost point of bound
    private double posY;//topLeftmost point of bound

    private double vx;
    private double vy;

    private Color color;

    public PixelEntity(
        Screen screen, 
        Vec2 position, 
        Vec2 velocity,
        Color rgb){

        this.screen = screen;

        this.posX = position.x;
        this.posY = position.y;

        this.vx = velocity.x;
        this.vy = velocity.y;
        this.color = rgb;

        this.isDead = false;

    }

    public PixelEntity(
        Screen screen, 
        Vec2 position, 
        Vec2 velocity,
        int intRGB){

        this.screen = screen;

        this.posX = position.x;
        this.posY = position.y;

        this.vx = velocity.x;
        this.vy = velocity.y;
         
        int red = (intRGB >> 16) & 0xFF;
        int green = (intRGB >> 8) & 0xFF;
        int blue = intRGB & 0xFF;

        this.color = new Color("color",red,green,blue);

        this.isDead = false;

    }

    public void rePosition(Vec2 newPos){
        posX = newPos.x;
        posY = newPos.y;
    }

    @Override
    public void update() {
        this.posX += vx;
        this.posY += vy;
        

        if(this.posX>screen.width){
            // this.vx = this.vx*-1;
            // while(this.posX>screen.width){
            //     this.posX= this.posX+vx*2;
            // }
            // this.color.b = (int) ((int)255*Math.random());
            this.isDead = true;
        }
        if(this.posX<0){
            // this.vx = this.vx*-1;
            // while(this.posX<0){
            //     this.posX= this.posX+vx*2;
            // }
            // this.color.b = (int) ((int)255*Math.random());
            this.isDead = true;
        }



        if(this.posY>=screen.height-1){
            // vy = -vy;
            // this.posY= this.posY+vy*2;
            // this.color.g = (int) ((int)255*Math.random());
            this.isDead = true;
        }
        if(this.posY<0){
            // vy = -vy;
            // this.posY = this.posY+vy*2;
            // this.color.r = (int) ((int)255*Math.random());
            this.isDead = true;
        }
        

    }

    @Override
    public int getDrawPriority() {
        return 1;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public void compose(Screen screenLayers, SpriteManager sm) {
        if(!this.isDead){
            screen.putPixel((int)this.posX, (int)this.posY,this.color.r, this.color.g, this.color.b);
        }
        
    }


}