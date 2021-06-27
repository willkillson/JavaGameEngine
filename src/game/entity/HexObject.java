package game.entity;

import game.gamegfx.ScreenLayers.LayerType;
import graphics.hex.Hex;

public class HexObject extends Hex {

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

}
