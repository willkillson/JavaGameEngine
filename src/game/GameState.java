package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.entity.HexObject;

public class GameState {

  Map<String, ArrayList<HexObject>> players;

  public GameState(){

    this.players = new HashMap<String, ArrayList<HexObject>>();

  }
  
}
