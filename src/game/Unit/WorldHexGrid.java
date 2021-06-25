package game.Unit;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Optional;

import graphics.Screen;
import graphics.hex.FractionalHex;
import graphics.hex.Hex;
import graphics.hex.Layout;
import graphics.hex.Orientation;
import graphics.hex.Point;
import input.ImageLoader;

//https://github.com/stleary/JSON-java
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorldHexGrid implements GameObject { 
 
  private Screen screen;
  private Orientation orientation;
  private Point size;
  private Point origin;
  private Layout layout;
  private ArrayList<HexObject> hexs;

  public String selectedHexType;
  

  // Graphics
  private boolean redraw; // Used for determining if the grid needs to be redrawn.
  private boolean reCalculate;  // Used if the origin/layout/size changes
  private int[] gridPixelArray;
  private BufferedImage bi_water_tile_1000 = ImageLoader.loadImage("./assets/hex_tiles/water_hex_tile_1000.png");
  private BufferedImage bi_plains_tile_1000 = ImageLoader.loadImage("./assets/hex_tiles/plains_hex_tile_1000.png");
  private BufferedImage bi_empty_tile_1000 = ImageLoader.loadImage("./assets/hex_tiles/empty_hex_tile_1000.png");

  public WorldHexGrid(Screen screen){
    this.screen = screen;
    this.orientation = Orientation.layoutPointy();
    this.size = new Point(100.100,100.0);
    this.origin = new Point(screen.width/2.0,screen.height/2.0);
    this.layout = new Layout(orientation,size,origin);
    this.hexs = new ArrayList<HexObject>();
    this.redraw = true;
    this.reCalculate = true;
    this.gridPixelArray = new int[this.screen.height*this.screen.width];
    this.selectedHexType = "water_hex";
  }

  public void createHex(Point position, String type){
    FractionalHex fHex = this.layout.pixelToHex(position);
    HexObject roundedHex = (HexObject) new HexObject(layout.hexRound(fHex),type);
    roundedHex.type = this.selectedHexType;
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

  public void saveMap(String mapName){
    StackWalker walker = StackWalker.getInstance();
    Optional<String> methodName = walker.walk(frames->
      frames.findFirst()
      .map(StackWalker.StackFrame::getMethodName));
    System.out.println("TODO: "+ methodName.get());

    JSONArray hexJsonArray = new JSONArray();

    this.hexs.forEach(hex->{
      JSONObject newHexJson = new JSONObject();
      newHexJson.put("type", hex.type);
      newHexJson.put("q", hex.getQ());
      newHexJson.put("r", hex.getR());
      newHexJson.put("s", hex.getS());
      hexJsonArray.put(newHexJson);
    });

    //Write JSON file
    try (FileWriter file = new FileWriter("./assets/maps/"+mapName+".json")) {
      //We can write any JSONArray or JSONObject instance to the file
      file.write(hexJsonArray.toString()); 
      file.flush();

    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void loadMap(String mapName){
    StackWalker walker = StackWalker.getInstance();
    Optional<String> methodName = walker.walk(frames->
      frames.findFirst()
      .map(StackWalker.StackFrame::getMethodName));
    System.out.println("TODO: "+ methodName.get());

    hexs = new ArrayList<HexObject>();

    try {
        FileReader reader = new FileReader("./assets/maps/"+mapName+".json");
        //JSON parser object to parse read file
        JSONTokener jsonTokener = new JSONTokener(reader);
        while(jsonTokener.more()){
          JSONArray jsonArray = (JSONArray)(jsonTokener.nextValue());
          for(int i = 0;i< jsonArray.length();i++){
            JSONObject jo = jsonArray.getJSONObject(i);

            Hex newHex = new Hex((int)jo.getInt("q"),(int)jo.getInt("r"),(int)jo.getInt("s"));
            HexObject roundedHex = (HexObject) new HexObject(newHex,jo.getString("type"));
            this.hexs.add(roundedHex);
            this.redraw = true;

          }          
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }


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


    // // TODO
    // if(this.reCalculate){
    //   // redo everything
    // }else if(this.redraw){
    //   // Just add/remove something

    // }else{

    // }


    if(this.redraw){
      this.clearFrame();
      this.layout = new Layout(orientation,size,origin);
      int iWidth = (int) (Math.sqrt(3)*size.x);
      int iHeight = (int) (size.x *2);


      BufferedImage re_water_hex = ImageLoader.resizeImage(bi_water_tile_1000, iWidth, iHeight);
      BufferedImage re_plains_hex = ImageLoader.resizeImage(bi_plains_tile_1000, iWidth, iHeight);
      BufferedImage re_empty_hex = ImageLoader.resizeImage(bi_empty_tile_1000, iWidth, iHeight);

      int[] re_water_hex_pixelArray = re_water_hex.getRGB(0, 0, re_water_hex.getWidth(), re_water_hex.getHeight(), null, 0, re_water_hex.getWidth());
      int[] re_plains_hex_pixelArray = re_plains_hex.getRGB(0, 0, re_plains_hex.getWidth(), re_plains_hex.getHeight(), null, 0, re_plains_hex.getWidth());
      int[] re_empty_hex_pixelArray = re_empty_hex.getRGB(0, 0, re_empty_hex.getWidth(), re_empty_hex.getHeight(), null, 0, re_empty_hex.getWidth());

      //// Red outline
      //Color red = new Color("Red", 255,0,0);
      this.hexs.forEach((hex)->{
          Point point = layout.hexToPixel(hex);
          switch (hex.type){
            case "water_hex":{
              screen.movePixels(re_water_hex_pixelArray,this.gridPixelArray,(int)point.x,(int)point.y,re_water_hex.getWidth(),re_water_hex.getHeight());
              break;
            }
            case "plains_hex":{
              screen.movePixels(re_plains_hex_pixelArray,this.gridPixelArray,(int)point.x,(int)point.y,re_plains_hex.getWidth(),re_plains_hex.getHeight());
              break;
            }
            case "empty_hex":{
              screen.movePixels(re_empty_hex_pixelArray,this.gridPixelArray,(int)point.x,(int)point.y,re_empty_hex.getWidth(),re_empty_hex.getHeight());
              break;
            }
          }
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

  @Override
  public int compareTo(GameObject o) {
    if(this.getDrawPriority()==o.getDrawPriority()){
      return 0;
    }else if(this.getDrawPriority()<o.getDrawPriority()){
      return -1;
    }else{
      return 1;
    }
  }
}
