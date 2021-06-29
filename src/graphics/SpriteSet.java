package graphics;

import java.awt.*;
import java.util.*;

public class SpriteSet {
  
  private Map<String, Image> animationSheets;

  public SpriteSet(){
    this.animationSheets = new HashMap<>();
  }

  public void addSheet(String name, Image animationSheet){
    animationSheets.put(name,animationSheet);
  }

  public Image get(String name){
    return this.animationSheets.get(name);
  }
}
