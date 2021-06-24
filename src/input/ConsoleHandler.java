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
    switch(input){
        case "hello":{
            System.out.println("---> Hello cutie! I hope your day is fine, because you are so fine!");
            break;
        }
        case "help":{
          System.out.println("---> Example Commands:");
          System.out.println("---> load water_hex");
          System.out.println("---> load plains_hex");
          break;
        }
        case "load water_hex":{
            System.out.println("---> Loading water hex");
            this.eventQue.add(new InputEvent("load", "water_hex"));
            break;
        }
        case "load plains_hex":{
          System.out.println("---> Loading plains hex");
          this.eventQue.add(new InputEvent("load", "plains_hex"));
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
