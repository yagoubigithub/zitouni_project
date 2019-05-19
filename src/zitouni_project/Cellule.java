/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 *
 * @author Yagoubi
 */
public class Cellule extends JLabel {

    public Cellule(String text) {
         setBorder(new LineBorder(Color.BLACK, 1));
    setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 50));
    setOpaque(false);
      setText(text);
        setHorizontalAlignment(SwingConstants.CENTER);
setVerticalAlignment(SwingConstants.CENTER);
    }
    
    
    
}
