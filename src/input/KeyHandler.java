package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    System.out.println(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // TODO Auto-generated method stub
    System.out.println(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
    System.out.println(e);
  }
  
}