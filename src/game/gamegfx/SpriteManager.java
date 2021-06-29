package game.gamegfx;

import java.util.HashMap;
import java.util.Map;

import game.gamegfx.Screen;
import game.gamegfx.Screen.LayerType;
import graphics.hex.Point;
import graphics.io.ImageLoader;

public class SpriteManager {



  private Map<String, Sprite> sprites;
  private Point hexSize;
  private int hexScaledWidth;
  private int hexScaledHeight;
  public Map<String, LayerType> layerTypeMap;
  
  public SpriteManager(Point hexSize){

    sprites = new HashMap<String,Sprite>();

    layerTypeMap = new HashMap<String,LayerType>();

    layerTypeMap.put("water_hex", LayerType.HEX_TILE);
    layerTypeMap.put("plains_hex", LayerType.HEX_TILE);
    layerTypeMap.put("empty_hex", LayerType.HEX_TILE);
    layerTypeMap.put("player_yellow", LayerType.HEX_TILE);
        
    sprites.put("water_hex", new Sprite(ImageLoader.loadImage("./assets/hex_tiles/png/water_hex_tile_1000.png"), LayerType.HEX_TILE));
    sprites.put("plains_hex", new Sprite(ImageLoader.loadImage("./assets/hex_tiles/png/plains2_hex.png"), LayerType.HEX_TILE));
    sprites.put("empty_hex", new Sprite(ImageLoader.loadImage("./assets/hex_tiles/png/empty_hex_tile_1000.png"), LayerType.HEX_TILE));
    sprites.put("player_yellow", new Sprite(ImageLoader.loadImage("./assets/hex_tiles/png/player_yellow_hex.png"), LayerType.HEX_TILE));
    this.hexSize = hexSize;

    sprites.keySet().forEach(e->{
      if(sprites.get(e).getType() == LayerType.HEX_TILE){
        sprites.get(e).scaleBufferImage(this.hexSize);
      }
    });

    this.calculateHexs(this.hexSize);

  }

  public void calculateHexs(Point size){
    sprites.keySet().forEach(e->{
      if(sprites.get(e).getType() == LayerType.HEX_TILE){
        sprites.get(e).scaleBufferImage(size);
        this.hexScaledWidth = (int) (Math.sqrt(3)*size.x);
        this.hexScaledHeight = (int) (size.y *2);
      }
    });
  }

  public int getWidth(LayerType lt){
    return this.hexScaledWidth;
  }

  public int getHeight(LayerType lt){

    return this.hexScaledHeight;
  }

  public int[] getSPA(String name){
    return this.sprites.get(name).getSPA();
  }
  
}
