package graphics;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import graphics.io.ImageLoader;
import util.Constants;

public class SpriteLibrary {

  private final static String PATH_TO_SPRITES = Constants.RESOURCE_PATH + "sprites/"; 
  // private final static String PATH_TO_SPRITES = "./assets"; 

  private Map<String, SpriteSet> units;

  public SpriteLibrary(){
    this.units = new HashMap<>();



    // SpriteSet spriteSet1 = new SpriteSet();
    // spriteSet1.addSheet("walk", ImageLoader.loadImage(PATH_TO_SPRITES+"walk.png"));
    // units.put("walk", spriteSet1);

    File root = new File(PATH_TO_SPRITES);

    String[] directories = root.list(new FilenameFilter(){
      @Override
      public boolean accept(File current, String name){
        return new File(current, name).isDirectory();
      }
    });

    for(String directory: directories){

      File dir = new File(root+"/"+ directory);
      
      File[] files = dir.listFiles();
      SpriteSet ss = new SpriteSet();
      for(File file: files){
        String spriteName = file.getName().substring(0,file.getName().length()-4);
        String fn = file.getName();
        ss.addSheet(spriteName, ImageLoader.loadImage(file.getAbsolutePath()));
      }
      this.units.put(directory.substring(0,directory.length()), ss);      
    }



  }

  public SpriteSet getSpriteSet(String name){
    return this.units.get(name);
  }

}
