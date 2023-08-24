package input;

public class InputEvent {

  public String name;
  public String keyText;
  public int position_x;
  public int position_y;
  
  public InputEvent(String name, int position_x, int position_y){
    this.name = name;
    this.position_x = position_x;
    this.position_y = position_y;
    this.keyText = "";
  }
  public InputEvent(String name, String keyText){
    this.name = name;
    this.keyText = keyText;
  }
}
