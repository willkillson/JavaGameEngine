package game.entity;

import graphics.hex.Hex;

public class HexObject extends Hex implements GameObject {

  public String type;
  public boolean isDead;

  public HexObject(int q, int r, int s, String type) {
    super(q, r, s);
    this.type = type;
    this.isDead = false;
    //TODO Auto-generated constructor stub
  }

  public HexObject(Hex hexRound, String type2) {
    super(hexRound.getQ(), hexRound.getR(), hexRound.getS());
    this.type = type2;
  }

  @Override
  public int compareTo(GameObject o) {
    if(this.getDrawPriority()==o.getDrawPriority()){
      return 0;
    }else if(this.getDrawPriority()<o.getDrawPriority()){
      return -1;
    }else{
      return 1;
    }
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void compose() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getDrawPriority() {
    return 0;
  }

  @Override
  public boolean isDead() {
    return this.isDead;
  }  
}
