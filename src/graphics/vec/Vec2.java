package graphics.vec;


public class Vec2 {
    public double x;
    public double y;

    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2 min(Vec2 value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x -= value.x;
        newV2.y -= value.y;
        return newV2;
    }

    public Vec3 xyx(){
        return new Vec3(this.x,this.y,this.x);
    }

    public Vec2 min(double value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x -= value;
        newV2.y -= value;
        return newV2;
    }

    public Vec2 add(double value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x += value;
        newV2.y += value;
        return newV2;
    }

    public Vec2 add(Vec2 value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x += value.x;
        newV2.y += value.y;
        return newV2;
    }

    public void sub(double value){
//        Vec2 newV2 = new Vec2(this.x, this.y);
        this.x -= value;
        this.y -= value;
    }


    public Vec2 div(double value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x /= value;
        newV2.y /= value;
        return newV2;
    }

    public Vec2 div(Vec2 value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x /= value.x;
        newV2.y /= value.y;
        return newV2;
    }

    public Vec2 mul(double value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x *= value;
        newV2.y *= value;
        return newV2;
    }

    public Vec2 mul(Vec2 value){
        Vec2 newV2 = new Vec2(this.x, this.y);
        newV2.x *= value.x;
        newV2.y *= value.y;
        return newV2;
    }

    public double length(){
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));
    }

    /**
     * https://stackoverflow.com/questions/343584/how-do-i-get-whole-and-fractional-parts-from-double-in-jsp-java
     * @return
     */
    public Vec2 fractional() {
        double numX = this.x;
        double numY = this.y;
        long iPartX = (long)numX;
        long iPartY = (long)numY;
        double fPartX =  numX - iPartX;
        double fPartY =  numY - iPartY;
        return new Vec2(fPartX, fPartY);
    }

    public Vec2 whole(){
        double numX = this.x;
        double numY = this.y;
        long iPartX = (long)numX;
        long iPartY = (long)numY;
        return new Vec2(iPartX, iPartY);
    }





}
