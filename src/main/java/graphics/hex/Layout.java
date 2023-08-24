package graphics.hex;

import java.util.ArrayList;

// https://www.redblobgames.com/grids/hexagons/implementation.html#hex-distance

public class Layout {

    public final Orientation orientation;
    public final Point size;
    public final Point origin;

    public Layout(Orientation o, Point size, Point origin) {
        this.orientation = o;
        this.size = size;
        this.origin = origin;
    }

    public Point hexToPixel(Hex h) {
        double x = (orientation.f0 * h.getQ() + orientation.f1 * h.getR()) * this.size.x;
        double y = (orientation.f2 * h.getQ() + orientation.f3 * h.getR()) * this.size.y;
        return new Point(x + origin.x, y + origin.y);
    }

    public FractionalHex pixelToHex(Point p) {
        Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);

        double q = orientation.b0 * pt.x + orientation.b1 * pt.y;
        double r = orientation.b2 * pt.x + orientation.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }

    public Hex hexRound(FractionalHex h) {
        int q = (int) (Math.round(h.q));
        int r = (int) (Math.round(h.r));
        int s = (int) (Math.round(h.s));

        double q_diff = Math.abs(q - h.q);
        double r_diff = Math.abs(r - h.r);
        double s_diff = Math.abs(s - h.s);

        if (q_diff > r_diff && q_diff > s_diff) {
            q = -r - s;
        } else if (r_diff > s_diff) {
            r = -q - s;
        } else {
            s = -q - r;
        }
        return new Hex(q, r, s);
    }

    private Point hexCornerOffset(int corner) {
        double angle = 2.0 * Math.PI * (orientation.start_angle + corner) / 6;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }

    public ArrayList<Point> polygonCorners(Hex h) {
        ArrayList<Point> corners = new ArrayList<Point>();
        Point center = hexToPixel(h);
        for (int i = 0; i < 6; i++) {
            Point offset = hexCornerOffset(i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }
}
