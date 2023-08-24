package game;

import game.entity.HexObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameState {

    Map<String, ArrayList<HexObject>> players;

    public GameState() {

        this.players = new HashMap<String, ArrayList<HexObject>>();
    }
}
