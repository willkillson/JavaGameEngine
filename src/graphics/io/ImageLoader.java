package graphics.io;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
  
  public static BufferedImage loadImage(String fileName)
  {
      BufferedImage bi = null;
      try {
          bi = ImageIO.read(new File(fileName));
      } catch (IOException e) {
          e.printStackTrace();
          System.out.println("Image could not be read");
          System.exit(1);
      }

      
      return bi;
  }
  
  public static BufferedImage resizeImage(BufferedImage originial, int width, int height){
      BufferedImage resizedImage = null;
      try {
          resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
          Graphics2D graphics2D = resizedImage.createGraphics();
          graphics2D.drawImage(originial, 0, 0, width, height, null);
          graphics2D.dispose();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return resizedImage;
  }
  
  public static int[] getPixelArray(BufferedImage bi, int width, int height){
    
    int[] image3Pixels = ((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
    int[] pixelArray = new int[width*height];  
    
    int skip_by = bi.getRaster().getHeight();
    int curHeight = 0;
    int runningIndex = 0;
    int index = 0;
    try{
    while(runningIndex<image3Pixels.length && curHeight<height){
        for(int j = 0;j< width;j++){
            //System.out.println("pixel_index: "+(runningIndex+j) + " pixel_value: " + image3Pixels[(runningIndex+j)]);
            pixelArray[index] = image3Pixels[(runningIndex+j)];
            index++;
        }
        runningIndex = runningIndex + skip_by;
        curHeight++;
    }
    return pixelArray;
    }catch(Exception e){
    e.printStackTrace();
    return pixelArray;
    }
  }

}
