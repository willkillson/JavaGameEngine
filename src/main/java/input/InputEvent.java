package input;

import graphics.vec.Vec2;

public class InputEvent {
    public String name;
    public String keyText;
    public Vec2 position;
    public int position_x;
    public int position_y;

    public InputEvent(String name, int position_x, int position_y) {
        this.name = name;
        this.position_x = position_x;
        this.position_y = position_y;
        this.position = new Vec2(position_x, position_y);
        this.keyText = "";
    }

    public InputEvent(String name, String keyText) {
        this.name = name;
        this.keyText = keyText;
    }
}
