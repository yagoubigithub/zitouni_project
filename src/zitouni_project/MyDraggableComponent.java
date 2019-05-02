/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author Yagoubi
 */
public class MyDraggableComponent  extends JLabel {
    
     private volatile int screenX = 0;
  private volatile int screenY = 0;
  private volatile int myX = 0;
  private volatile int myY = 0;

  public MyDraggableComponent(String text) {
    setBorder(new LineBorder(Color.BLUE, 3));
    setBackground(Color.WHITE);
    setBounds(0, 0, 100, 50);
    setOpaque(false);
      setText(text);

    addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) { }

      @Override
      public void mousePressed(MouseEvent e) {
        screenX = e.getXOnScreen();
        screenY = e.getYOnScreen();

        myX = getX();
        myY = getY();
      }

      @Override
      public void mouseReleased(MouseEvent e) { }

      @Override
      public void mouseEntered(MouseEvent e) { }

      @Override
      public void mouseExited(MouseEvent e) { }

    });
    addMouseMotionListener(new MouseMotionListener() {

      @Override
      public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - screenX;
        int deltaY = e.getYOnScreen() - screenY;

        setLocation(myX + deltaX, myY + deltaY);
      }

      @Override
      public void mouseMoved(MouseEvent e) { }

    });
  }

}
