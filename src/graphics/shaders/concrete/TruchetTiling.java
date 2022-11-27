package graphics.shaders.concrete;

import graphics.Color;
import graphics.Screen;
import graphics.shaders.FragmentShader;
import graphics.vec.Vec2;
import graphics.vec.Vec3;
import graphics.vec.Vec4;

import java.util.Date;

import static java.lang.Math.*;

public class TruchetTiling extends FragmentShader {
    private Date date = new Date();

    public TruchetTiling(Screen screen, int threadNumber, Vec2 resolution, Vec2 currentMousePosition) {
        super(screen, threadNumber, resolution, currentMousePosition);
    }


    @Override
    public Vec4 frag(Vec2 fragCoord) {

        Vec2 uv = fragCoord.div(resolution);
        uv = uv.add(new Vec2(-10, -10));
        Vec3 col = new Vec3(0);
        uv = rot(uv, PI*(double)date.getTime()/100000);

        uv = uv.mul(100);
        Vec2 gv = uv.fractional().min(0.5);
        Vec2 id = new Vec2(Math.floor(uv.x), Math.floor(uv.y));
        // returns a random number [0,1]
        double n = hash21(id);

        double width = 0.2;
        //// Random Flipping
        if(n <.5){
            gv.x *= -1;
        }
        double d = abs(abs(gv.x+gv.y)-0.5);
        double mask = 1-smoothStep(0.01,-0.01, d-width);

//        col = col.add(id.mul(.1));
        col = col.add(mask);

//        col = col.add(n);


        //// Draws boxes around
//        if ((gv.x >.48)||(gv.y>.48)){
//            col = col.add(new Vec3(1,0,0));
//        }

//        col = col.add(gv);
        return new Vec4(col,1.0);
    }

    @Override
    public void run() {
        if(threadNumber==1){
            for(int i = 0;i< screen.width/2;i++){
                for(int j = 0;j< screen.height/2;j++){
                    Vec4 out = frag(new Vec2(i,j));
                    screen.putPixel(
                            i,
                            j,
                            new Color("Shader",
                                    (int)(out.x*255),
                                    (int)(out.y*255),
                                    (int)(out.z*255)));
                }
            }
        }else if(threadNumber==2){
            for(int i = screen.width/2;i< screen.width;i++){
                for(int j = 0;j< screen.height/2;j++){
                    Vec4 out = frag(new Vec2(i,j));
                    screen.putPixel(
                            i,
                            j,
                            new Color("Shader",
                                    (int)(out.x*255),
                                    (int)(out.y*255),
                                    (int)(out.z*255)));
                }
            }
        }else if(threadNumber==3){
            for(int i = 0;i< screen.width;i++){
                for(int j = screen.height/2;j< screen.height;j++){
                    Vec4 out = frag(new Vec2(i,j));
                    screen.putPixel(
                            i,
                            j,
                            new Color("Shader",
                                    (int)(out.x*255),
                                    (int)(out.y*255),
                                    (int)(out.z*255)));
                }
            }
        }else if(threadNumber==4){
            for(int i = screen.width/2;i< screen.width;i++){
                for(int j = screen.height/2;j< screen.height;j++){
                    Vec4 out = frag(new Vec2(i,j));
                    screen.putPixel(
                            i,
                            j,
                            new Color("Shader",
                                    (int)(out.x*255),
                                    (int)(out.y*255),
                                    (int)(out.z*255)));
                }
            }
        }

    }

    double hash21(Vec2 p){
        p = p.mul(new Vec2(234.34, 435.345)).fractional();
        p = p.add(dot(p,p.add(34.23)));
        return fractional(p.x*p.y);
    }

    double dot(Vec2 v1, Vec2 v2){
        return v1.x * v2.x + v2.y * v2.y;
    }

    Vec2 rot(Vec2 uv, double angle){
        double xprime = uv.x*cos(angle) - uv.y*sin(angle);
        double yprime = uv.x*Math.sin(angle) + uv.y * cos(angle);
        return new Vec2(xprime,yprime);

    }

    public double fractional(double value) {
        double num = value;
        long iPart = (long)num;
        double fPart =  num - iPart;
        return fPart;
    }

}
