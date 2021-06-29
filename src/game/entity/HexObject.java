package game.entity;

import game.gamegfx.Screen;
import game.gamegfx.Screen.LayerType;
import game.gamegfx.SpriteManager;
import graphics.hex.Hex;

public class HexObject extends Hex implements Entity {

  public LayerType layerType;
  public String spriteName;
  public boolean isDead;

  public HexObject(int q, int r, int s, LayerType type, String spriteName) {
    super(q, r, s);
    this.layerType = type;
    this.spriteName = spriteName;
    this.isDead = false;
  }

  public HexObject(Hex hexRound, LayerType layerType, String spriteName) {
    super(hexRound.getQ(), hexRound.getR(), hexRound.getS());
    this.layerType = layerType;
    this.spriteName = spriteName;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void compose(Screen screenLayers, SpriteManager sm) {
    // TODO Auto-generated method stub
    
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
