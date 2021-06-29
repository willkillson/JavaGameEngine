package game.entity;

import java.util.ArrayList;
import java.util.Optional;

import graphics.Vec2;
import graphics.hex.FractionalHex;
import graphics.hex.Hex;
import graphics.hex.Layout;
import graphics.hex.Orientation;
import graphics.hex.Point;

//https://github.com/stleary/JSON-java
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import game.gamegfx.PixelManager;
import game.gamegfx.Screen;
import game.gamegfx.Screen;
import game.gamegfx.SpriteManager;
import game.gamegfx.Screen.LayerType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HexGrid implements Entity { 
 
  private Orientation orientation;
  private Point size;
  private Point origin;
  private Layout layout;
  private ArrayList<HexObject> hexs;

  //public String selectedHexType;
  // private PixelManager pm;

  // Graphics
  //private SpriteManager sm;
  private boolean redraw; // Used for determining if the grid needs to be redrawn.


  public HexGrid(
    Point size, 
    Orientation orientation, 
    Point origin){

    this.orientation = orientation;
    this.size = size;
    this.origin = origin;
    this.layout = new Layout(orientation,size,origin);
    this.hexs = new ArrayList<HexObject>();
    this.redraw = true;
    //this.selectedHexType = "water_hex";
    //this.pm = new PixelManager();
    //this.sm = new SpriteManager(this.size);
  }

  //sm.scaledPA

  public void desintegrateHex(HexObject hex, ArrayList<Entity> pixelObjects){
    // Point point = layout.hexToPixel(hex);
    // this.helperDesintegrate(
    //   sm.getSPA(hex.spriteName), 
    //   (int)point.x, 
    //   (int)point.y,
    //   sm.getWidth(hex.spriteType), 
    //   sm.getHeight(hex.spriteType),
    //   pixelObjects);
  }

  public Point desintegrate1(int k, int arrayWidth, int height, int arrayHeight){
    double randomx = 10;
    double randomy = 10;
    if((k<arrayWidth/2)){
      randomx*= -1;
    }
    if((height<arrayHeight/2)){
      randomy*= -1;
    }
    return new Point(randomx, randomy);
  }

  public Point desintegrate2(int k, int arrayWidth, int height, int arrayHeight){
    double randomx = 3*Math.random()+1;
    double randomy = 3*Math.random()+1;

    if(Math.random()>0.5){
      if((k<arrayWidth/2)){
        randomx*= -1;
      }
    }
    if(Math.random()>0.5){
      if((height<arrayHeight/2)){
        randomy*= -1;
      }
    }
    return new Point(randomx, randomy);
  }

  public void helperDesintegrate(int[] sourcePixels, int x_pos, int y_pos, int arrayWidth, int arrayHeight, ArrayList<Entity> pixelObjects){
    // int height = 0;
    // int width = 1920;
    // int center_x_pos =  x_pos - arrayWidth/2;
    // int center_y_pos =  y_pos - arrayHeight/2;
    // for(int i = center_x_pos + center_y_pos * width;i<1920*1080;i++){
    //     if(i% width==0){
    //         if(height<arrayHeight){ 
    //             for(int k = 0;k< arrayWidth;k++){   // Loop f writing in the sourcePixels buffer to the destinationPixels buffer
    //                 try{
    //                     if(sourcePixels[k+height*arrayWidth]!=0){ // Prevent writing in empty pixels/alphachannel
    //                         if(i>0){    // Prevent writing in negative indexs;
    //                             if(k+center_x_pos>0){   // prevent wrap around left
    //                                 if(k+center_x_pos<width){   // prevent wrap around right
                                      
    //                                   // Corner desintegrate
    //                                   //Point p = desintegrate1(k,arrayWidth, height, arrayHeight);
    //                                   // PerPixel desintegrate
    //                                   Point p = desintegrate1(k,arrayWidth, height, arrayHeight);
                     
    //                                   pixelObjects.add(
    //                                     new PixelEntity(screen,new Vec2(k+center_x_pos,height+center_y_pos), 
    //                                     new Vec2(p.x,p.y), 
    //                                     sourcePixels[k+height*arrayWidth]));
    //                                 }
    //                             }
    //                         }
    //                     }
    //                 }catch(Exception e){
    //                     e.printStackTrace();
    //                 }
    //             }
    //         }
    //         height++;
    //         i= i+arrayWidth;
    //     }
    // }
  }

  public void createHex(Point position, String spriteName, SpriteManager sm, Screen screenLayers){

    LayerType lt = sm.layerTypeMap.get(spriteName);
    FractionalHex fHex = this.layout.pixelToHex(position);
    HexObject roundedHex = (HexObject) new HexObject(layout.hexRound(fHex),lt,spriteName);
    this.hexs.add(roundedHex);

    
    int[] layer = screenLayers.gitLayer(lt);
    int[] spa = sm.getSPA(spriteName);

    Point point = layout.hexToPixel(roundedHex);
    int width = sm.getWidth(roundedHex.layerType);
    int height = sm.getHeight(roundedHex.layerType);
    screenLayers.movePixels(spa, layer, (int)point.x, (int)point.y, width, height);
    screenLayers.updateLayer(lt);

    //Point point = this.layout.hexToPixel(roundedHex);
    // screen.movePixels(
    //   sm.getSPA(roundedHex.spriteName), 
    //   sm.getWPA(roundedHex.type),
    //   (int)point.x,
    //   (int)point.y,
    //   pm.getWidth(roundedHex.type), 
    //   pm.getHeight(roundedHex.type));

  }

  public void deleteHex(Point position, ArrayList<Entity> pixelObjects){

    FractionalHex fHex = this.layout.pixelToHex(position);
    Hex roundedHex = layout.hexRound(fHex);
    this.hexs.removeIf((hex)->{
        if(hex.equals(roundedHex)){
          this.desintegrateHex(hex,pixelObjects);
        }
        return hex.equals(roundedHex);
    });
    Point point = this.layout.hexToPixel(roundedHex);

    // screen.movePixels(
    //   pm.getScaledPA("empty_hex"),
    //   pm.getWPA("empty_hex"),
    //   (int)point.x,
    //   (int)point.y,
    //   pm.getWidth("empty_hex"),
    //   pm.getHeight("empty_hex"));
  }

  public void changeSize(int amount){
    this.size = new Point(this.size.x + amount, this.size.y + amount);
    System.out.println("Size: " +this.size.x);
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

    // JSONArray hexJsonArray = new JSONArray();

    // this.hexs.forEach(hex->{
    //   JSONObject newHexJson = new JSONObject();
    //   newHexJson.put("type", hex.type);
    //   newHexJson.put("q", hex.getQ());
    //   newHexJson.put("r", hex.getR());
    //   newHexJson.put("s", hex.getS());
    //   hexJsonArray.put(newHexJson);
    // });

    // //Write JSON file
    // try (FileWriter file = new FileWriter("./assets/maps/"+mapName+".json")) {
    //   //We can write any JSONArray or JSONObject instance to the file
    //   file.write(hexJsonArray.toString()); 
    //   file.flush();

    // } catch (IOException e) {
    //     e.printStackTrace();
    // }
  }

  public void loadMap(String mapName){
    // StackWalker walker = StackWalker.getInstance();
    // Optional<String> methodName = walker.walk(frames->
    //   frames.findFirst()
    //   .map(StackWalker.StackFrame::getMethodName));
    // System.out.println("TODO: "+ methodName.get());

    // hexs = new ArrayList<HexEntity>();

    // try {
    //     FileReader reader = new FileReader("./assets/maps/"+mapName+".json");
    //     //JSON parser object to parse read file
    //     JSONTokener jsonTokener = new JSONTokener(reader);
    //     while(jsonTokener.more()){
    //       JSONArray jsonArray = (JSONArray)(jsonTokener.nextValue());
    //       for(int i = 0;i< jsonArray.length();i++){
    //         JSONObject jo = jsonArray.getJSONObject(i);

    //         Hex newHex = new Hex((int)jo.getInt("q"),(int)jo.getInt("r"),(int)jo.getInt("s"));
    //         HexEntity roundedHex = (HexEntity) new HexEntity(newHex,jo.getString("type"));
    //         this.hexs.add(roundedHex);
    //         this.redraw = true;

    //       }          
    //     }
    // } catch (FileNotFoundException e) {
    //     e.printStackTrace();
    // }


  }

  // private void clearFrame(){
  //   for(int i = 0;i<gridPixelArray.length;i++){
  //     gridPixelArray[i] = 0;
  //   }
  // }

  // private void reCalculateHexs(){
  //   // this.layout = new Layout(orientation,size,origin);
  //   // sm.calculateHexs(size);
  // }


  @Override
  public void update() {
    // TODO Auto-generated method stub
  }

  @Override
  public int getDrawPriority() {
    return 0;
  }

  @Override
  public boolean isDead() {
    return false;
  }

  @Override
  public void compose(Screen screenLayers, SpriteManager sm) {
    if(this.redraw){
      System.out.println("redrawing");

      //sm.calculateHexs(size);


          //Point point = this.layout.hexToPixel(roundedHex);
    // screen.movePixels(
    //   sm.getSPA(roundedHex.spriteName), 
    //   sm.getWPA(roundedHex.type),
    //   (int)point.x,
    //   (int)point.y,
    //   pm.getWidth(roundedHex.type), 
    //   pm.getHeight(roundedHex.type));

      // this.hexs.forEach(hex->{
      //   LayerType lt = sm.layerTypeMap.get(hex.spriteName);
      //   int[] layer = screenLayers.gitLayer(lt);
      //   int[] spa = sm.getSPA(hex.spriteName);

      //   Point point = layout.hexToPixel(hex);
      //   int width = sm.getWidth(hex.layerType);
      //   int height = sm.getHeight(hex.layerType);
      //   screenLayers.movePixels(spa, layer, (int)point.x, (int)point.y, width, height);
      //   screenLayers.updateLayer(lt);
      // });
      

      //TODO refactor this code so the sprite manager uses the Sprite.java class
      // pm.clearWorkingPixelBuffer("plains_hex");
      // pm.clearWorkingPixelBuffer("water_hex");
      // pm.clearWorkingPixelBuffer("empty_hex");
      // pm.clearWorkingPixelBuffer("player_yellow");
      
      //this.reCalculateHexs();
      // this.hexs.forEach((hex)->{
      //     Point point = layout.hexToPixel(hex);
      //     screen.movePixels(
      //       pm.getScaledPA(hex.type),
      //       pm.getWPA(hex.type),
      //       (int)point.x,
      //       (int)point.y,
      //       pm.getWidth(hex.type),
      //       pm.getHeight(hex.type));
      // });
      this.redraw = false;
    }

    //TODO refactor this code so the sprite manager uses the Sprite.java class
    // Copy all our grid pixels into our screen pixels buffer

    
    
    
    // this.pm.writeWPAtoScreen(screen, "water_hex");
    // this.pm.writeWPAtoScreen(screen, "plains_hex");
    // this.pm.writeWPAtoScreen(screen, "empty_hex");
    // this.pm.writeWPAtoScreen(screen, "player_yellow");
    
  }
}
