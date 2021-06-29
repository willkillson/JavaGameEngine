package game.gamegfx;

import java.awt.image.BufferedImage;

import java.util.HashMap;
import java.util.Map;

import graphics.hex.Point;
import graphics.io.ImageLoader;

public class PixelManager {

  private Map<String, BufferedImage> rawBI; //Raw Buffer Images
  private Map<String, BufferedImage> scaledBI;  //Scaled Buffer Images
  private Map<String, int[]> scaledPA;  //Scaled Pixel Arrays
  private Map<String, int[]> workingPA; // The Pixel array that will be moved into our screen buffer

  private int wpa_width = 1920;
  private int wpa_height = 1080;

  public PixelManager(){
    rawBI = new HashMap<String, BufferedImage>();
    scaledBI = new HashMap<String, BufferedImage>();
    scaledPA = new HashMap<String, int[]>();
    workingPA = new HashMap<String, int[]>();

    rawBI.put("water_hex", ImageLoader.loadImage("./assets/hex_tiles/png/water_hex_tile_1000.png"));
    rawBI.put("plains_hex", ImageLoader.loadImage("./assets/hex_tiles/png/plains2_hex.png"));
    rawBI.put("empty_hex", ImageLoader.loadImage("./assets/hex_tiles/png/empty_hex_tile_1000.png"));
    rawBI.put("player_yello", ImageLoader.loadImage("./assets/hex_tiles/png/player_yellow_hex.png"));
  }

  public void reScaleHexImages(Point size){
    int iWidth = (int) (Math.sqrt(3)*size.x);
    int iHeight = (int) (size.x *2);

    // Resize the raw hex images.
    scaledBI.put("water_hex", ImageLoader.resizeImage(rawBI.get("water_hex"), iWidth, iHeight));
    scaledBI.put("plains_hex", ImageLoader.resizeImage(rawBI.get("plains_hex"), iWidth, iHeight));
    scaledBI.put("empty_hex", ImageLoader.resizeImage(rawBI.get("empty_hex"), iWidth, iHeight));
    scaledBI.put("player_yellow", ImageLoader.resizeImage(rawBI.get("player_yellow"), iWidth, iHeight));
  
    // Get the pixel arrays from the resized hex images
    scaledPA.put("water_hex",calculatePixelArray(scaledBI.get("water_hex")));
    scaledPA.put("plains_hex",calculatePixelArray(scaledBI.get("plains_hex")));
    scaledPA.put("empty_hex",calculatePixelArray(scaledBI.get("empty_hex")));
    scaledPA.put("player_yellow",calculatePixelArray(scaledBI.get("player_yellow")));
  }

  public void createWorkingPixelBuffer(String entitiesName){
    this.workingPA.put(entitiesName, new int[this.wpa_width * this.wpa_height]);
    this.clearWorkingPixelBuffer(entitiesName);
  }

  public void clearWorkingPixelBuffer(String wpaName){
    this.workingPA.put(wpaName, new int[this.wpa_width * this.wpa_height]);
  }

  public int[] getWPA(String wpaName){
    return this.workingPA.get(wpaName);
  }

  public void writeWPAtoScreen(Screen screen, String wpaName){
    for(int i = 0;i<screen.pixels.length;i++){
      if(this.workingPA.get(wpaName)[i]!=0 || wpaName.compareTo("empty_hex")==0){
        screen.pixels[i] = this.workingPA.get(wpaName)[i];
      }
    }
  }



  /**
   * This is a helper method for reCalculateHexs to reduce code clutter.
   * @param bi the resized buffered image we wish to operate on.
   * @return  int pixel array of the reized buffered image.
   */
  private int[] calculatePixelArray(BufferedImage bi){
    int size_x = bi.getWidth();
    int size_y = bi.getHeight();
    return bi.getRGB(0, 0, size_x, size_y, null, 0, size_x);
  }

  public int[] getScaledPA(String type){
    return this.scaledPA.get(type);
  }

  public int getWidth(String type){
    return this.scaledBI.get(type).getWidth();
  }

  public int getHeight(String type){
    return this.scaledBI.get(type).getHeight();
  }
}
