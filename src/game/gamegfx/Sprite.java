package game.gamegfx;

import java.awt.image.BufferedImage;

import game.gamegfx.Screen.LayerType;
import graphics.hex.Point;
import graphics.io.ImageLoader;

public class Sprite {

  private LayerType layerType;
  private BufferedImage rawBufferedImage;
  private BufferedImage scaledBufferImage;
  private int[] scaledPixelBuffer;

  public Sprite(BufferedImage rbi, LayerType st){
    layerType = st;
    rawBufferedImage = rbi;
  }  

  public void scaleBufferImage(Point size){
    int iWidth = (int) (Math.sqrt(3)*size.x);
    int iHeight = (int) (size.x *2);
    // Resize the raw hex images.
    scaledBufferImage = ImageLoader.resizeImage(rawBufferedImage, iWidth, iHeight);
    // Get the pixel arrays from the resized hex images
    scaledPixelBuffer = calculatePixelArray(scaledBufferImage);
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

  public LayerType getType(){
    return this.layerType;
  }

  public int [] getSPA(){
    return scaledPixelBuffer;
  }

}
