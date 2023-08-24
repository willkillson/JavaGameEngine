package graphics;

public class Color {

    String colorName;
    public int r;
    public int g;
    public int b;
    public int rgb;

    public Color(String colorName, int r, int g, int b) {
        this.colorName = colorName;

        if (r <= 0) {
            this.r = 0;
        } else if (r >= 255) {
            this.r = 255;
        } else {
            this.r = r;
        }

        if (g <= 0) {
            this.g = 0;
        } else if (g >= 255) {
            this.g = 255;
        } else {
            this.g = g;
        }

        if (b <= 0) {
            this.b = 0;
        } else if (b >= 255) {
            this.b = 255;
        } else {
            this.b = b;
        }

        this.rgb = r << 8;
        this.rgb = this.rgb + g << 8;
        this.rgb = this.rgb + b;
    }
}
