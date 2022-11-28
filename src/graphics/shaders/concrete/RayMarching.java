//TODO: ran into issues attempting to implement this, perhaps I need to work on the engine a little more.
//package graphics.shaders.concrete;
//
//import graphics.vec.Vec2;
//import graphics.vec.Vec3;
//import graphics.vec.Vec4;
//import graphics.shaders.FragmentShader;
//
//public class RayMarching extends FragmentShader {
//
//    int MAX_STEPS = 100;
//    double MAX_DIST = 100.0;
//    double SURF_DIST = 0.01;
//
//    public RayMarching(Vec2 resolution, Vec2 currentMousePosition) {
//        super(resolution, currentMousePosition);
//    }
//
//    @Override
//    public Vec4 frag(Vec2 fragCoord) {
//        // Fix aspect ratio so it is square.
//        Vec2 uv = fragCoord.min(0.5).mul(resolution);
//
//        Vec3 col = new Vec3(0);
//
//        Vec3 ro = new Vec3(0,1,0);
//        Vec3 rd = Vec3.normalize(new Vec3(uv.x,uv.y,1.0));
//
//        double d = rayMarch(ro,rd);
//        d /= 6.;
//        col = new Vec3(d);
//
//        Vec4 out = new Vec4(col,1.0);
//        return out;
//
//    }
//
//    private double rayMarch(Vec3 ro, Vec3 rd){
//        // dO -> distance from origin
//        double dO = 0.0;
//        for (int i = 0;i< MAX_STEPS;i++){
//            Vec3 p = ro.add(rd.mul(dO));
//            double ds = getDist(p);
//            dO += ds;
//            if(dO > MAX_DIST || ds < SURF_DIST){
//                break;
//            }
//        }
//        return dO;
//    }
//
//    private double getDist(Vec3 p){
//        Vec4 s = new Vec4(0,1,6,1);
//        double sphereDist = p.minus(s.xyz()).length() - s.w;
//        double planeDist = p.y;
//        double d = Math.min(sphereDist,planeDist);
//        return d;
//    }
//}
