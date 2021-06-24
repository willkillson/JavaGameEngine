package game.Unit;

public abstract class GameObject implements Comparable<GameObject> {

  public abstract void update();

  public abstract void compose();

  public abstract int getDrawPriority();

  @Override
  public int compareTo(GameObject o){
    if(this.getDrawPriority()==o.getDrawPriority()){
      return 0;
    }else if(this.getDrawPriority()<o.getDrawPriority()){
      return -1;
    }else{
      return 1;
    }
  }
  
}
