package game.Unit;

public interface GameObject extends Comparable<GameObject> {

  public abstract void update();

  public abstract void compose();

  public abstract int getDrawPriority();
  
}
