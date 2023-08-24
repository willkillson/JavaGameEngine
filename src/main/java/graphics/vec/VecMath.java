package graphics.vec;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class VecMath {

    // Vec1 (AKA double)
    public static double fractional(double value) {
        double num = value;
        long iPart = (long) num;
        double fPart = num - iPart;
        return fPart;
    }

    /**
     * https://en.wikipedia.org/wiki/Smoothstep
     * @param edge0
     * @param edge1
     * @param x
     * @return
     */
    public static double smoothStep(double edge0, double edge1, double x) {
        if (x < edge0) return 0;

        if (x >= edge1) return 1;

        // Scale/bias into [0..1] range
        x = (x - edge0) / (edge1 - edge0);

        return x * x * (3 - 2 * x);
    }

    public static double GLSLSmoothStep(double edge0, double edge1, double x) {
        // Clamp x to the range [0, 1]
        x = Math.max(0, Math.min(1, (x - edge0) / (edge1 - edge0)));

        // Perform the smoothstep interpolation
        return x * x * (3 - 2 * x);
    }

    // Vec2

    /**
     * https://stackoverflow.com/questions/343584/how-do-i-get-whole-and-fractional-parts-from-double-in-jsp-java
     * @return
     */
    public static Vec2 fractional(Vec2 value) {
        double numX = value.x;
        double numY = value.y;
        long iPartX = (long) numX;
        long iPartY = (long) numY;
        double fPartX = numX - iPartX;
        double fPartY = numY - iPartY;
        return new Vec2(fPartX, fPartY);
    }

    public static double dot(Vec2 v1, Vec2 v2) {
        return v1.x * v2.x + v2.y * v2.y;
    }

    public static Vec2 rot(Vec2 uv, double angle) {
        double xprime = uv.x * cos(angle) - uv.y * sin(angle);
        double yprime = uv.x * Math.sin(angle) + uv.y * cos(angle);
        return new Vec2(xprime, yprime);
    }

    public static double distance(Vec2 p1, Vec2 p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    // Vec3

    public static Vec3 normalize(Vec3 value) {
        double magnitude = Math.sqrt(Math.pow(value.x, 2) + Math.pow(value.y, 2) + Math.pow(value.z, 2));
        Vec3 newVec = new Vec3(value);
        newVec = newVec.div(magnitude);
        return newVec;
    }

    public static double length(Vec3 value) {
        return Math.sqrt(Math.pow(value.x, 2) + Math.pow(value.y, 2) + Math.pow(value.z, 2));
    }

    public static double length(Vec2 value) {
        return Math.sqrt(Math.pow(value.x, 2) + Math.pow(value.y, 2));
    }

    // Vec4
}
