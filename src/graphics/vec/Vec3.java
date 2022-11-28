package graphics.vec;

public class Vec3 {

    public double x;
    public double y;
    public double z;

    public Vec3(double x,
                double y,
                double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(double value){
        this.x = value;
        this.y = value;
        this.z = value;
    }

    public Vec3(Vec3 vec3){
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;
    }

    public Vec3 minus(Vec3 value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x -= value.x;
        newVec.y -= value.y;
        newVec.z -= value.z;
        return newVec;
    }


    public Vec3 mul(double value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x *= value;
        newVec.y *= value;
        newVec.z *= value;
        return newVec;
    }

    public Vec3 mul(Vec2 value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x *= value.x;
        newVec.y *= value.y;
        return newVec;
    }

    public Vec3 mul(Vec3 value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x *= value.x;
        newVec.y *= value.y;
        newVec.z *= value.z;
        return newVec;
    }

    public Vec3 add(double value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x += value;
        newVec.y += value;
        newVec.z += value;
        return newVec;
    }

    public Vec3 add(Vec2 value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x += value.x;
        newVec.y += value.y;
        return newVec;
    }

    public Vec3 add(Vec3 value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x += value.x;
        newVec.y += value.y;
        newVec.z += value.z;
        return newVec;
    }

    public Vec3 div(double value){
        Vec3 newVec = new Vec3(this.x,this.y,this.z);
        newVec.x /= value;
        newVec.y /= value;
        newVec.z /= value;
        return newVec;
    }




}
