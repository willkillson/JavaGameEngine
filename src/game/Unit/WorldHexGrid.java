package game.Unit;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Screen;
import graphics.hex.FractionalHex;
import graphics.hex.Hex;
import graphics.hex.Layout;
import graphics.hex.Orientation;
import graphics.hex.Point;
import input.ImageLoader;

public class WorldHexGrid extends GameObject { 
 
  private Screen screen;
  private Orientation orientation;
  private Point size;
  private Point origin;
  private Layout layout;
  private ArrayList<Hex> hexs;
  

  // Graphics
  private boolean redraw; // Used for determining if the grid needs to be redrawn.
  private int[] gridPixelArray;
  private BufferedImage bi_water_tile_1000 = ImageLoader.loadImage("./assets/hex_tiles/water_hex_tile_1000.png");

  public WorldHexGrid(Screen screen){
    this.screen = screen;
    this.orientation = Orientation.layoutPointy();
    this.size = new Point(100.100,100.0);
    this.origin = new Point(screen.width/2.0,screen.height/2.0);
    this.layout = new Layout(orientation,size,origin);
    this.hexs = new ArrayList<Hex>();
    this.redraw = true;
    this.gridPixelArray = new int[this.screen.height*this.screen.width];
  }

  public void createHex(Point position){
    FractionalHex fHex = this.layout.pixelToHex(position);
    Hex roundedHex = layout.hexRound(fHex);
    this.hexs.add(roundedHex);
    this.redraw = true;
  }

  public void deleteHex(Point position){
    FractionalHex fHex = this.layout.pixelToHex(position);
    Hex roundedHex = layout.hexRound(fHex);
    this.hexs.removeIf((hex)->{
        return hex.equals(roundedHex);
    });
    this.redraw = true;
  }

  public void changeSize(int amount){
    this.size = new Point(this.size.x + amount, this.size.y + amount);
    this.redraw = true;
  }

  public void changeOrientationPointy(){
    this.orientation = Orientation.layoutPointy();
    this.redraw = true;
  }

  public void changeOrientationFlat(){
    this.orientation = Orientation.layoutFlat();
    this.redraw = true;
  }

  private void clearFrame(){
    for(int i = 0;i<gridPixelArray.length;i++){
      gridPixelArray[i] = 0;
    }
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
  }

  @Override
  public void compose() {
    if(this.redraw){
      this.clearFrame();
      this.layout = new Layout(orientation,size,origin);
      int iWidth = (int) (Math.sqrt(3)*size.x);
      int iHeight = (int) (size.x *2);
      BufferedImage resizedImage3 = ImageLoader.resizeImage(bi_water_tile_1000, iWidth, iHeight);
      int[] pixelArray = resizedImage3.getRGB(0, 0, resizedImage3.getWidth(), resizedImage3.getHeight(), null, 0, resizedImage3.getWidth());
      //// Red outline
      //Color red = new Color("Red", 255,0,0);
      this.hexs.forEach((hex)->{
          Point point = layout.hexToPixel(hex);
          screen.movePixels(pixelArray,this.gridPixelArray,(int)point.x,(int)point.y,resizedImage3.getWidth(),resizedImage3.getHeight());
          //screen.putHexagon(hex, layout, origin, size, red);
      });
      this.redraw = false;
    }else{

      // Copy all our grid pixels into our screen pixels buffer
      for(int i = 0;i<screen.pixels.length;i++){
        screen.pixels[i] = this.gridPixelArray[i];
      }

    }
  }

  @Override
  public int getDrawPriority() {
    return 0;
  }
}
