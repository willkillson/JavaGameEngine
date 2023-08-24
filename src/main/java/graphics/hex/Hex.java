package graphics.hex;

public class Hex {
  private int q;
  private int r;
  private int s;

  /** https://www.redblobgames.com/grids/hexagons/implementation.html
   * 
   * @param q
   * @param r
   * @param s
   */
  public Hex(int q, int r, int s){
    assert(q+r+s==0);
    this.q = q;
    this.r = r;
    this.s = s;
  }

  public int getQ(){
    return this.q;
  }

  public int getR(){
    return this.r;
  }

  public int getS(){
    return this.s;
  }

  public boolean equals(Hex hex){
    return this.q == hex.q && this.r == hex.r && this.s == hex.s;
  }

  public Hex add(Hex hex){
    return new Hex(this.q+hex.q, this.r+hex.r, this.s+hex.s);
  }

  public Hex subtract(Hex hex){
    return new Hex(this.q-hex.q, this.r-hex.r, this.s-hex.s);
  }

  public Hex multiply(Hex hex){
    return new Hex(this.q*hex.q, this.r*hex.r, this.s*hex.s);
  }

  // 1.3
  //////

  public int hexLength(){
    return ((Math.abs(this.q) + Math.abs(this.r) + Math.abs(this.s)) /2);
  }

  public int hexDistance(Hex hex){
    return this.subtract(hex).hexLength();
  }

  // 1.3.1
  ////////

  private final Hex[] hexDirections(){
    Hex[] hexList = new Hex[6];
    hexList[0] = new Hex(1,0,-1);
    hexList[1] = new Hex(1,-1,0);
    hexList[2] = new Hex(0,-1,1);
    hexList[3] = new Hex(-1,0,1);
    hexList[4] = new Hex(-1,1,0);
    hexList[5] = new Hex(0,1,-1);
    return hexList;
  }

  public Hex hexDirection(int direction){
    assert(0<= direction && direction <6);
    return this.hexDirections()[direction];
  }

  public Hex hexNeighbor(int direction){
    return this.add(this.hexDirection(direction));
  }

  // Overriding equals() to compare two Complex objects
  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Hex)){
      return false;
    }
    Hex hex = (Hex)o;
    return hex.q== this.q && hex.r == this.r && hex.s == this.s;
  }
    

}
