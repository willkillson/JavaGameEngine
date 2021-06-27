package game.entity;

import game.gamegfx.ScreenLayers;
import game.gamegfx.SpriteManager;

public interface Entity {

  public abstract void update();

  public abstract void compose(ScreenLayers screenLayers, SpriteManager sm);

  public abstract int getDrawPriority();

  public abstract boolean isDead();
  
}
