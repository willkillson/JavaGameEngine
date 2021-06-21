package input;

import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.event.MouseInputListener;


public class InputHandler implements MouseInputListener{

  public boolean isDragging;
  public Stack<InputEvent> eventQue;

  public InputHandler(){
    this.isDragging = false;
    this.eventQue = new Stack<>();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    InputEvent newEvent = new InputEvent("mouseClicked", e.getX(), e.getY());
    eventQue.add(newEvent);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    InputEvent newEvent = new InputEvent("mousePressed", e.getX(), e.getY());
    eventQue.add(newEvent);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if(isDragging==true){
      InputEvent newEvent = new InputEvent("mouseReleased", e.getX(), e.getY());
      eventQue.add(newEvent);
      isDragging = false;
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // InputEvent newEvent = new InputEvent("mousePressed", e.getX(), e.getY());
    // eventQue.add(newEvent);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // InputEvent newEvent = new InputEvent("mousePressed", e.getX(), e.getY());
    // eventQue.add(newEvent);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if(isDragging==false){
      InputEvent newEvent = new InputEvent("mousePressed", e.getX(), e.getY());
      eventQue.add(newEvent);
      isDragging = true;
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // InputEvent newEvent = new InputEvent("mousePressed", e.getX(), e.getY());
    // eventQue.add(newEvent);
  }
  
}
