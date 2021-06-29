package game.gamegfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.util.HashMap;
import java.util.Map;

import util.Constants;

public class Screen {

  public enum LayerType{
    HEX_TILE,
    PLAYER_PIECE,
    PIXEL
  }

  private BufferedImage mainBI;
  public int[] mainPA;

  private Map<Integer,int[]> layers;

  private Map<Integer, Boolean> layerUpdate;
  

  public Screen(int numLayers){
    this.mainBI = new BufferedImage(Constants.WIDTH,Constants.HEIGHT,BufferedImage.TYPE_INT_RGB);
    this.mainPA = ((DataBufferInt)mainBI.getRaster().getDataBuffer()).getData();

    // Main.
    layers = new HashMap<Integer, int[]>();
    layerUpdate = new HashMap<Integer, Boolean>();
    for(int i = 0;i< numLayers; i++){
      layers.put(i, new int[Constants.HEIGHT * Constants.WIDTH]); 
      layerUpdate.put(i, true);
    }
  }

  public BufferedImage getMainBI(){
    return this.mainBI;
  }

  public void paintLayers(){
    for(int i = 0;i< mainPA.length;i++){
      //mainPA[i] = 0;  // Clear it
      if(layerUpdate.get(0)){
        if(layers.get(0)[i]!=0){
          mainPA[i] = layers.get(0)[i]; // Assign the buffer according to layer priority
        }
      }
    }
  
    for(int i = 0;i< layerUpdate.size();i++){
      this.layerUpdate.put(i, false);
    }
  }

  public int[] gitLayer(LayerType lt){
    switch(lt){
      case HEX_TILE:{
        return this.layers.get(0);
      }
      case PLAYER_PIECE:{
        return this.layers.get(1);

      }
      default:
        break;
    }
    
    return null;
  }

  public void updateLayer(LayerType lt){
    switch(lt){
      case HEX_TILE:{
        layerUpdate.put(0, true);
      }
      case PLAYER_PIECE:{
        layerUpdate.put(1, true);
      }
      default:
        break;
    }
  }

  public void movePixels(int[] sourcePixels,int[] destinationPixels, int x_pos, int y_pos, int arrayWidth, int arrayHeight){
    int height = 0;
    int width = 1920;
    int center_x_pos =  x_pos - arrayWidth/2;
    int center_y_pos =  y_pos - arrayHeight/2;
    for(int i = center_x_pos + center_y_pos * width;i<destinationPixels.length && height<arrayHeight;i++){
        if(i% width==0){
            if(height<arrayHeight){ 
                for(int k = 0;k< arrayWidth;k++){   // Loop f writing in the sourcePixels buffer to the destinationPixels buffer
                    try{
                        if(sourcePixels[k+height*arrayWidth]!=0){ // Prevent writing in empty pixels/alphachannel
                            if(i>0){    // Prevent writing in negative indexs;
                                if(k+center_x_pos>0){   // prevent wrap around left
                                    if(k+center_x_pos<width){   // prevent wrap around right
                                        destinationPixels[i+k+center_x_pos] = sourcePixels[k+height*arrayWidth];
                                    }
                                }
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            height++;
            i= i+arrayWidth;
        }
    }
}



}
