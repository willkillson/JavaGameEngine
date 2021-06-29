package game.entity;

import game.gamegfx.Screen;
import game.gamegfx.SpriteManager;

public interface Entity {

  public abstract void update();

  public abstract void compose(Screen screenLayers, SpriteManager sm);

  public abstract int getDrawPriority();

  public abstract boolean isDead();
  
}
