package game.gamegfx;

import java.util.HashMap;
import java.util.Map;

import util.Constants;

public class ScreenLayers {

  public enum LayerType{
    HEX_TILE,
    PLAYER_PIECE,
    PIXEL
  }

  public int[] main;
  private Map<Integer,int[]> layers;
  

  public ScreenLayers(int numLayers){
    // Main.
    main = new int[Constants.HEIGHT * Constants.WIDTH];
    layers = new HashMap<Integer, int[]>();
    for(int i = 0;i< numLayers; i++){
      layers.put(i, new int[Constants.HEIGHT * Constants.WIDTH]); 
    }
  }

  public void paintLayers(){
    for(int i = 0;i< main.length;i++){
      main[i] = 0;  // Clear it
      main[i] = layers.get(0)[i]; // Assign the buffer acording to layer priority
      // for(int j = 0;j< layers.size();j++){
      //   main[i] = layers.get(j)[i]; // Assign the buffer acording to layer priority
      // }
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

  public void movePixels(int[] sourcePixels,int[] destinationPixels, int x_pos, int y_pos, int arrayWidth, int arrayHeight){
    int height = 0;
    int width = 1920;
    int center_x_pos =  x_pos - arrayWidth/2;
    int center_y_pos =  y_pos - arrayHeight/2;
    for(int i = center_x_pos + center_y_pos * width;i<destinationPixels.length;i++){
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
