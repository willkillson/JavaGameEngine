package input;

import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.event.MouseInputListener;

public class MouseHandler implements MouseInputListener {
    public boolean isDragging;
    public Stack<InputEvent> eventQue;

    public MouseHandler(Stack<InputEvent> inputEventQue) {
        this.isDragging = false;
        this.eventQue = inputEventQue;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // InputEvent newEvent = new InputEvent("mouseClicked", e.getX(), e.getY());
        // eventQue.add(newEvent);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        InputEvent newEvent = new InputEvent("mousePressed", e.getX(), e.getY());
        newEvent.keyText = Integer.toString(e.getButton());
        eventQue.add(newEvent);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        InputEvent newEvent = new InputEvent("mouseReleased", e.getX(), e.getY());
        eventQue.add(newEvent);
        isDragging = false;
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
        InputEvent newEvent = new InputEvent("mouseMoved", e.getX(), e.getY());
        eventQue.add(newEvent);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        InputEvent newEvent = new InputEvent("mouseMoved", e.getX(), e.getY());
        eventQue.add(newEvent);
    }
}
