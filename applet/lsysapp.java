import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/* <applet code="LSystemApplet.class" width="800" height="600"></applet> */

public class LSystemApplet extends JApplet {
    
    BufferedImage image;
    
    LSystem lsystem;
    
    public void init()  {
        
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        
        lsystem = LSystem.getDemoLSystem(4);
        lsystem.drawIt(image.createGraphics());
    }
    
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
    
}

