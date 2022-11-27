//package graphics.shaders.concrete;
//
//import graphics.shaders.FragmentShader;
//import graphics.vec.Vec2;
//import graphics.vec.Vec4;
//
//public class ColorDemo extends FragmentShader {
//    public ColorDemo(Vec2 resolution, Vec2 currentMousePosition) {
//        super(resolution, currentMousePosition);
//    }
//
//    @Override
//    public Vec4 frag(Vec2 fragCoord) {
//        Vec2 uv = fragCoord.div(resolution);
//        uv = uv.min(0.5);
//        return new Vec4(uv.x, uv.y, 0,0);
//    }
//}
