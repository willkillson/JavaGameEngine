package game.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.lang.model.type.UnionType;

import game.gamegfx.Screen;
import game.gamegfx.SpriteManager;
import graphics.AnimationManager;
import graphics.SpriteLibrary;

public class UnitEntity implements Entity {

  private AnimationManager animationManager;

  private double posX;//topLeftmost point of bound
  private double posY;//topLeftmost point of bound

  public UnitEntity(SpriteLibrary sl, double pos_x, double pos_y){
    
    this.posX = pos_x;
    this.posY = pos_y;
    this.animationManager = new AnimationManager(sl.getSpriteSet("dave"));
    
  }

  @Override
  public void update() {
    this.animationManager.update();

    
  }

  @Override
  public void compose(Screen screenLayers, SpriteManager sm) {
    // TODO Auto-generated method stub
    
    Image image = this.animationManager.getSprite();
    BufferedImage bi = (BufferedImage)image;

    int w = bi.getWidth();
    int h = bi.getHeight();

    int[] pa = bi.getRGB(0,0,w,h,null ,0,w);
    
    screenLayers.movePixels(pa, screenLayers.mainPA, (int)this.posX, (int)this.posY, 64, 64);
    
  }

  @Override
  public int getDrawPriority() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isDead() {
    // TODO Auto-generated method stub
    return false;
  }
  
}
