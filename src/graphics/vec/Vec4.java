package graphics.vec;

public class Vec4 {

    public double x;
    public double y;
    public double z;
    public double w;

    public Vec4(double x,
                double y,
                double z,
                double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4(Vec2 v2){
        this.x = v2.x;
        this.y = v2.y;
        this.z = 0.0;
        this.w = 1.0;
    }

    public Vec4(Vec3 v3,
                double w){
        this.x = v3.x;
        this.y = v3.y;
        this.z = v3.z;
        this.w = w;
    }

    public Vec3 xyz(){
        return new Vec3(this.x,this.y,this.z);
    }


    public Vec4 add(Vec3 value){
        Vec4 newVec = new Vec4(value.x, value.y, value.z,1.0);
        newVec.x += value.x;
        newVec.y += value.y;
        newVec.z += value.z;
        return newVec;
    }
}
