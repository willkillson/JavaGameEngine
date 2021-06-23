package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

public class KeyHandler implements KeyListener{

  public Stack<InputEvent> eventQue;

  public KeyHandler(Stack<InputEvent> inputEventQue){
    this.eventQue = inputEventQue;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //System.out.println(e);
    this.eventQue.add(new InputEvent("keyPressed", e.getKeyText(e.getKeyCode())));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
  }
  
}
