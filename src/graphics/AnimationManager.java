package graphics;

import java.awt.image.BufferedImage;

import game.Game;
import java.awt.*;

public class AnimationManager {

  private SpriteSet spriteSet;
  private BufferedImage currentAnimationSheet;

  private int updatesPerFrame;  //How many updates per frame
  private int currentFrameTime; //
  private int frameIndex;

  public AnimationManager(SpriteSet spriteset){
    this.spriteSet = spriteset;
    this.updatesPerFrame = 20;
    this.frameIndex = 0;
    this.currentFrameTime = 0;
    this.playAnimation("stand");
  }

  public Image getSprite(){
    return currentAnimationSheet.getSubimage(Game.SPRITE_SIZE*frameIndex, 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
  }

  public void update(){
    currentFrameTime++;
    if(currentFrameTime>= updatesPerFrame){
      currentFrameTime = 0;
      frameIndex++;
      if(frameIndex>= (currentAnimationSheet.getWidth()/Game.SPRITE_SIZE-1)){
        frameIndex = 0;
      }
    }
  }

  public void playAnimation(String name){
    this.currentAnimationSheet = (BufferedImage)spriteSet.get(name);
  }
  
}
