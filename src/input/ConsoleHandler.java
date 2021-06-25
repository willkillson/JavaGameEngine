package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

public class ConsoleHandler implements KeyListener{

  public Stack<InputEvent> eventQue;
  String input = "";

  public ConsoleHandler(Stack<input.InputEvent> inputEventQue){
    this.eventQue = inputEventQue;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == 10){
      //return encountered
      this.processConsoleCommand();
    }else{
      if(e.getKeyCode()==16){
        
      }else{
        input += e.getKeyChar();
      }
    }
  }

  private void processConsoleCommand(){

    String[] arrayStrings = input.split("\\.");
    switch(arrayStrings[0]){
        case "help":{
          System.out.println("---> Example Commands:");
          System.out.println("---> select.water_hex");
          System.out.println("---> select.plains_hex");
          System.out.println("---> select.empty_hex");
          System.out.println("---> map.load.map_name");
          System.out.println("---> map.save.map_name");
          break;
        }
        case "map":{
          if(arrayStrings.length == 3){
            switch(arrayStrings[1]){
              case "load":{
                System.out.println("---> Loading map:" + arrayStrings[2]);
                this.eventQue.add(new InputEvent("load", arrayStrings[2]));
                break;
              }
              case "save":{
                System.out.println("---> Saving map:" + arrayStrings[2]);
                this.eventQue.add(new InputEvent("save", arrayStrings[2]));
                break;
              }
            }
          }
          break;
        }
        case "select":{
          if(arrayStrings.length == 2){
            System.out.println("---> Selecting: "+arrayStrings[1]);
            this.eventQue.add(new InputEvent("select", arrayStrings[1]));
          }
          break;
        }
        default:{
          System.out.println("Error: " + input);
          break;
        }
    }
    input = "";
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }
  
}
