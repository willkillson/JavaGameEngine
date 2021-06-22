package game.Unit;

import graphics.Color;
import graphics.Screen;
import graphics.Vec2;

public class Grid implements GameObject {
  
  public int posX;//topLeftmost point of bound
  public int posY;//topLeftmost point of bound

  public int rows;
  public int columns;

  public int width;
  public int height;
  

  private Screen screen;

  public Grid(Screen screen){
    this.screen = screen;
    this.width = screen.width;
    this.height = screen.height;
    this.rows = 10;
    this.columns = 10;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void compose() {
    

    // // Rows
    // int rowPos = this.height/this.rows;
    // for(int i = 0;i<this.rows;i++){
    //   screen.plotLine(0, rowPos*i, this.width, rowPos*i, gray);
    // }

    // // Columns
    // int columnPos = this.width/this.columns;
    // for(int i = 0;i<this.rows;i++){
    //   screen.plotLine(columnPos*i, 0, columnPos*i, this.height, gray);
    // }

    int size = 100;
  
    Vec2 center1 = new Vec2(200 , 200 );
    Vec2 center2 = new Vec2(200+ size +size/2, 200+ Math.sqrt(3)*size/2 );
    Vec2 center3 = new Vec2(200  , 200 + Math.sqrt(3)*size);

    Color red = new Color("Red", 255,0,0);
    Color green = new Color("Green", 0,255,0);
    Color gray = new Color("Gray", 192,192,192);
    Color blue = new Color("Blue", 0,0,255);

    screen.putHexagon(center1, size, red);
    screen.putHexagon(center2, size, gray);
    screen.putHexagon(center3, size, green);

  }
}
