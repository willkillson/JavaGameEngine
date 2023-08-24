package game.entity;

import game.SpriteManager;
import graphics.Screen;
import graphics.hex.FractionalHex;
import graphics.hex.Hex;
import graphics.hex.Layout;
import graphics.hex.Orientation;
import graphics.hex.Point;
import graphics.vec.Vec2;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class WorldHexGrid implements GameObject {

    private Screen screen;
    private Orientation orientation;
    private Point size;
    private Point origin;
    private Layout layout;
    private ArrayList<HexObject> hexs;

    public String selectedHexType;
    private SpriteManager sm;

    // Graphics
    private boolean redraw; // Used for determining if the grid needs to be redrawn.
    private int[] gridPixelArray;

    public WorldHexGrid(Screen screen) {
        this.screen = screen;
        this.orientation = Orientation.layoutPointy();
        this.size = new Point(35.0, 35.);
        this.origin = new Point(screen.width / 2.0, screen.height / 2.0);
        this.layout = new Layout(orientation, size, origin);
        this.hexs = new ArrayList<HexObject>();
        this.redraw = true;
        this.gridPixelArray = new int[screen.height * screen.width];
        this.selectedHexType = "water_hex";
        this.sm = new SpriteManager();
    }

    // sm.scaledPA

    public void desintegrateHex(HexObject hex, ArrayList<GameObject> gameObjects) {
        Point point = layout.hexToPixel(hex);
        this.helperDesintegrate(
                sm.getScaledPA(hex.type),
                (int) point.x,
                (int) point.y,
                sm.getWidth(hex.type),
                sm.getHeight(hex.type),
                gameObjects);
    }

    public Point desintegrate1(int k, int arrayWidth, int height, int arrayHeight) {
        double randomx = 10;
        double randomy = 10;
        if ((k < arrayWidth / 2)) {
            randomx *= -1;
        }
        if ((height < arrayHeight / 2)) {
            randomy *= -1;
        }
        return new Point(randomx, randomy);
    }

    public Point desintegrate2(int k, int arrayWidth, int height, int arrayHeight) {
        double randomx = 3 * Math.random() + 1;
        double randomy = 3 * Math.random() + 1;

        if (Math.random() > 0.5) {
            if ((k < arrayWidth / 2)) {
                randomx *= -1;
            }
        }
        if (Math.random() > 0.5) {
            if ((height < arrayHeight / 2)) {
                randomy *= -1;
            }
        }
        return new Point(randomx, randomy);
    }

    public void helperDesintegrate(
            int[] sourcePixels,
            int x_pos,
            int y_pos,
            int arrayWidth,
            int arrayHeight,
            ArrayList<GameObject> gameObjects) {
        int height = 0;
        int width = 1920;
        int center_x_pos = x_pos - arrayWidth / 2;
        int center_y_pos = y_pos - arrayHeight / 2;
        for (int i = center_x_pos + center_y_pos * width; i < 1920 * 1080; i++) {
            if (i % width == 0) {
                if (height < arrayHeight) {
                    for (int k = 0;
                            k < arrayWidth;
                            k++) { // Loop f writing in the sourcePixels buffer to the destinationPixels buffer
                        try {
                            if (sourcePixels[k + height * arrayWidth]
                                    != 0) { // Prevent writing in empty pixels/alphachannel
                                if (i > 0) { // Prevent writing in negative indexs;
                                    if (k + center_x_pos > 0) { // prevent wrap around left
                                        if (k + center_x_pos < width) { // prevent wrap around right

                                            // Corner desintegrate
                                            // Point p = desintegrate1(k,arrayWidth, height, arrayHeight);
                                            // PerPixel desintegrate
                                            Point p = desintegrate2(k, arrayWidth, height, arrayHeight);

                                            gameObjects.add(new PixelUnit(
                                                    screen,
                                                    new Vec2(k + center_x_pos, height + center_y_pos),
                                                    new Vec2(p.x, p.y),
                                                    sourcePixels[k + height * arrayWidth]));
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                height++;
                i = i + arrayWidth;
            }
        }
    }

    public void createHex(Point position, String type) {
        FractionalHex fHex = this.layout.pixelToHex(position);
        HexObject roundedHex = (HexObject) new HexObject(layout.hexRound(fHex), type);
        roundedHex.type = this.selectedHexType;
        this.hexs.add(roundedHex);

        Point point = this.layout.hexToPixel(roundedHex);

        screen.movePixels(
                sm.getScaledPA(roundedHex.type),
                gridPixelArray,
                (int) point.x,
                (int) point.y,
                sm.getWidth(roundedHex.type),
                sm.getHeight(roundedHex.type));
    }

    public void deleteHex(Point position, ArrayList<GameObject> gameObjects) {
        FractionalHex fHex = this.layout.pixelToHex(position);
        Hex roundedHex = layout.hexRound(fHex);
        this.hexs.removeIf((hex) -> {
            if (hex.equals(roundedHex)) {
                this.desintegrateHex(hex, gameObjects);
            }
            return hex.equals(roundedHex);
        });
        Point point = this.layout.hexToPixel(roundedHex);
        screen.movePixels(
                sm.getScaledPA("empty_hex"),
                gridPixelArray,
                (int) point.x,
                (int) point.y,
                sm.getWidth("empty_hex"),
                sm.getHeight("empty_hex"));
    }

    public void changeSize(int amount) {
        this.size = new Point(this.size.x + amount, this.size.y + amount);
        System.out.println("Size: " + this.size.x);
        this.redraw = true;
    }

    public void changeOrientationPointy() {
        this.orientation = Orientation.layoutPointy();
        this.redraw = true;
    }

    public void changeOrientationFlat() {
        this.orientation = Orientation.layoutFlat();
        this.redraw = true;
    }

    public void saveMap(String mapName) {
        StackWalker walker = StackWalker.getInstance();
        Optional<String> methodName =
                walker.walk(frames -> frames.findFirst().map(StackWalker.StackFrame::getMethodName));
        System.out.println("TODO: " + methodName.get());

        JSONArray hexJsonArray = new JSONArray();

        this.hexs.forEach(hex -> {
            JSONObject newHexJson = new JSONObject();
            newHexJson.put("type", hex.type);
            newHexJson.put("q", hex.getQ());
            newHexJson.put("r", hex.getR());
            newHexJson.put("s", hex.getS());
            hexJsonArray.put(newHexJson);
        });

        // Write JSON file
        try (FileWriter file = new FileWriter("./assets/maps/" + mapName + ".json")) {
            // We can write any JSONArray or JSONObject instance to the file
            file.write(hexJsonArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapName) {
        StackWalker walker = StackWalker.getInstance();
        Optional<String> methodName =
                walker.walk(frames -> frames.findFirst().map(StackWalker.StackFrame::getMethodName));
        System.out.println("TODO: " + methodName.get());

        hexs = new ArrayList<HexObject>();

        try {
            FileReader reader = new FileReader("./assets/maps/" + mapName + ".json");
            // JSON parser object to parse read file
            JSONTokener jsonTokener = new JSONTokener(reader);
            while (jsonTokener.more()) {
                JSONArray jsonArray = (JSONArray) (jsonTokener.nextValue());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    Hex newHex = new Hex((int) jo.getInt("q"), (int) jo.getInt("r"), (int) jo.getInt("s"));
                    HexObject roundedHex = (HexObject) new HexObject(newHex, jo.getString("type"));
                    this.hexs.add(roundedHex);
                    this.redraw = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearFrame() {
        for (int i = 0; i < gridPixelArray.length; i++) {
            gridPixelArray[i] = 0;
        }
    }

    private void reCalculateHexs() {
        this.layout = new Layout(orientation, size, origin);
        sm.reScaleHexImages(size);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void compose() {
        if (this.redraw) {
            this.clearFrame();
            this.reCalculateHexs();
            this.hexs.forEach((hex) -> {
                Point point = layout.hexToPixel(hex);
                screen.movePixels(
                        sm.getScaledPA(hex.type),
                        gridPixelArray,
                        (int) point.x,
                        (int) point.y,
                        sm.getWidth(hex.type),
                        sm.getHeight(hex.type));
            });
            this.redraw = false;
        }
        // Copy all our grid pixels into our screen pixels buffer
        for (int i = 0; i < screen.pixels.length; i++) {
            screen.pixels[i] = this.gridPixelArray[i];
        }
    }

    @Override
    public int getDrawPriority() {
        return 0;
    }

    @Override
    public int compareTo(GameObject o) {
        if (this.getDrawPriority() == o.getDrawPriority()) {
            return 0;
        } else if (this.getDrawPriority() < o.getDrawPriority()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isDead() {
        return false;
    }
}
