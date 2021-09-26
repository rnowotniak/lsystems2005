/*
 * Copyright (C) 2005   Robert Nowotniak <robert@nowotniak.com>
 *
 */

import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

public class LSystem {
   
   private Character axiom;
   private float totalLength;
   private float angle;
   private float baseAngle;
   private Point start;
   private int level;
   private HashMap prods;
   private String fractalName;
   
   private Graphics2D g2d;
   private Stack stateStack;
   private float stepLength;
   private double scaleFactor;
   private double standardFactor;
   
   private Color[] colorsTable;
   private int currentColor;
   
   
   static public void main(String[] args) throws Exception {
      // XXX test
      LSystem l = new LSystem(new File("data/Roslinka3.txt"));
      System.out.println(l.axiom);
   }
   
   public LSystem() {
      stateStack = new Stack();
      stepLength = 100;
      scaleFactor = 1.0;
      standardFactor = 1.0;
      
      /* Set some default colors */
      currentColor = 0;
      colorsTable = new Color[] {
         Color.RED,
         Color.ORANGE,
         Color.YELLOW,
         Color.GREEN,
         Color.CYAN,
         Color.BLUE,
         Color.MAGENTA
      };
   }
   
   public LSystem(InputStream in) throws IOException {
      this();
      Properties p = new Properties();
      p.load(in);
      
      try  {
         
         axiom = new Character(p.getProperty("axiom", "F").charAt(0));
         standardFactor = Double.parseDouble(p.getProperty("factor", "1.0"));
         scaleFactor = Double.parseDouble(p.getProperty("scale", "1.0f"));
         angle = Float.parseFloat(p.getProperty("angle", "90"));
         baseAngle = Float.parseFloat(p.getProperty("baseAngle", "0"));
         level = Integer.parseInt(p.getProperty("level", "1"));
         fractalName = p.getProperty("name", "lsystem");
         
         /* set start point */
         {
            String s = p.getProperty("start", "0,0");
            String xString = s.replaceAll("\\s*,.*", "");
            String yString = s.replaceAll(".*,\\s*", "");
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);
            start = new Point(x, y);
         }
         
         /* set productions hash map */
         {
            prods = new HashMap();
            for (Enumeration e = p.propertyNames(); e.hasMoreElements(); ) {
               String key = (String) e.nextElement();
               if (key.length() == 1) {
                  prods.put(new Character(key.charAt(0)), p.getProperty(key, ""));
               }
            }
         }
         
      } catch (NumberFormatException ex) {
         throw new IOException("Bad number format: " + ex.getMessage());
      }
   }
   
   public LSystem(File file) throws IOException {
      this(new FileInputStream(file));
   }
   
   
   public void drawIt(Graphics2D g2d) {
      this.g2d = g2d;
      
      g2d.setPaint(Color.GREEN);
      g2d.rotate(Math.toRadians(baseAngle));
      
      String firstRule = (String) prods.get(axiom);
      if (firstRule == null) {
         return;
      }
      String rule = new String(firstRule);
      
      for (int i = 1; i < level; i++) {
         rule = processRule(rule);
      }
      
      //scaleFactor = computeScale(firstRule);
      g2d.scale(scaleFactor, scaleFactor);
      draw(rule);
   }
   
   /*
    * Algorytm szacujacy rozmiar fraktala (JUZ NIE UZYWANE)
    *
    * tylko czasem dobrze to dzialalo
    * nie widzialem jeszcze w zadnym programie dobrego algorytmu do tego
    *
    * poza tym brakuje tu juz uwzglednienia nowych komend np: ( )
    *
    */
   private double computeScale(String firstRule) {
      double result;
      
      AffineTransform tx = new AffineTransform();
      Point p = new Point();
      double lastY = 0, maxY = 0;
      for (int i = 0; i < firstRule.length(); i++) {
         char c = firstRule.charAt(i);
         if (c == 'F' || c == 'f') {
            tx.translate(0, stepLength);
            p = transformPoint(new Point(), tx);
            if (Math.abs(p.y) > maxY) {
               maxY = Math.abs(p.y);
            }
         } else if (c == '+') {
            tx.rotate(Math.toRadians(angle));
         } else if (c == '-') {
            tx.rotate(Math.toRadians(-angle));
         } else if (c == '[') {
            stateStack.push(new AffineTransform(tx));
         } else if (c == ']') {
            tx = (AffineTransform) stateStack.pop();
         } else {
            /* XXX */
         }
         
         p = transformPoint(new Point(), tx);
         lastY = Math.abs(p.y);
      }
      
      result = totalLength / maxY;
      result *= Math.pow(stepLength / lastY, level - 1);
      return result;
   }
   
   static public double distanceFromPoint(Point p1, Point p2) {
      double result;
      double a = p2.x - p1.x;
      double b = p2.y - p1.y;
      result = Math.sqrt(a * a + b * b);
      return result;
   }
   
   private Point transformPoint(Point p, AffineTransform tx) {
      Point result;
      double coefs[] = new double[6];
      double x2, y2;
      
      tx.getMatrix(coefs);
      x2 = coefs[0] * p.x + coefs[2] * p.y + coefs[4];
      y2 = coefs[1] * p.x + coefs[3] * p.y + coefs[5];
      
      result = new Point((int) x2, (int) y2);
      return result;
   }
   
   private String processRule(String rule) {
      StringBuffer result = new StringBuffer();
      
      for (int i = 0; i < rule.length(); i++) {
         Character c = new Character(rule.charAt(i));
         String rep = (String) prods.get(c);
         if (rep != null) {
            result.append(rep);
         } else  {
            result.append(c);
         }
      }
      
      return result.toString();
   }
   
   private void draw(String rule) {
      float stepLength = this.stepLength;
      
      for (int i = 0; i < rule.length(); i++) {
         char c = rule.charAt(i);
         if (c == 'F') {
            g2d.setColor(colorsTable[currentColor]);
            g2d.drawLine(0, 0, 0, (int) stepLength);
            g2d.translate(0, stepLength);
         } else if (c == 'f') {
            g2d.translate(0, stepLength);
         } else if (c == '+') {
            g2d.rotate(Math.toRadians(angle));
         } else if (c == '-') {
            g2d.rotate(-Math.toRadians(angle));
         } else if (c == '[') {
            stateStack.push(g2d.getTransform());
         } else if (c == ']') {
            AffineTransform tx =
              (AffineTransform) stateStack.pop();
            g2d.setTransform(tx);
         } else if (c == '(') {
            stateStack.push(g2d.getTransform());
            stepLength *= standardFactor;
         } else if (c == ')') {
            AffineTransform tx =
             (AffineTransform) stateStack.pop();
            g2d.setTransform(tx);
            stepLength /= standardFactor;
         } else if (c == '*') {
            stepLength *= standardFactor;
         } else if (c == '/') {
            stepLength *= standardFactor;
         } else if (c == '<') {
            if (currentColor == 0) {
               currentColor= colorsTable.length - 1;
            } else {
               currentColor--;
            }
         } else if (c == '>') {
            if (currentColor == colorsTable.length - 1) {
               currentColor = 0;
            } else {
               currentColor++;
            }
         } else {
            /* XXX */
         }
      }
   }
   
   
   public void parseRulesDescription(String text) throws IOException {
      String lines[] = text.split("\n");
      prods.clear();
      
      for (int i = 0; i < lines.length; i++) {
         int state = 0;
         Character prodName = null;
         String prodValue = "";
         for (int j = 0; j < lines[i].length() && state != 3; j++) {
            char c = lines[i].charAt(j);
            if (c == ' ' || c == '\t') {
               continue;
            }
            switch (state) {
               case 0:
                  /* seeking for production name */
                  if (c == ':') {
                     throw new IOException(
                       "No production name given on line: " + lines[i]);
                  }
                  else {
                     prodName = new Character(c);
                     state = 1;
                  }
                  break;
               case 1:
                  /* seeking for colon */
                  if (c == ':') {
                     state = 2;
                  }
                  else {
                     throw new IOException(
                        "Production name can be only one character long: "
                        + lines[i]);
                  }
                  break;
               case 2:
                  /* seeking for production value */
                  prodValue = lines[i].substring(j);
                  state = 3;
                  break;
            }
         }
         if (state < 2) {
            throw new IOException("Rule doesn't match <key>:<value>\n" + lines[i]);
         }
         else {
            prods.put(prodName, prodValue);
         }
      }
   }
   
   
   public String getName() {
      return fractalName;
   }
   
   
   public java.lang.Character getAxiom() {
      return axiom;
   }
   
   public void setAxiom(java.lang.Character axiom) {
      this.axiom = axiom;
   }
   
   public float getTotalLength() {
      return totalLength;
   }
   
   public void setTotalLength(float totalLength) {
      this.totalLength = totalLength;
   }
   
   public float getAngle() {
      return angle;
   }
   
   public void setAngle(float angle) {
      this.angle = angle;
   }
   
   public float getBaseAngle() {
      return baseAngle;
   }
   
   public void setBaseAngle(float baseAngle) {
      this.baseAngle = baseAngle;
   }
   
   public java.awt.Point getStart() {
      return start;
   }
   
   public void setStart(java.awt.Point start) {
      this.start = start;
   }
   
   public int getLevel() {
      return level;
   }
   
   public void setLevel(int level) {
      this.level = level;
   }
   
   public java.util.HashMap getProds() {
      return prods;
   }
   
   public void setProds(java.util.HashMap prods) {
      this.prods = prods;
   }
   
   public java.lang.String getFractalName() {
      return fractalName;
   }
   
   public void setFractalName(java.lang.String fractalName) {
      this.fractalName = fractalName;
   }
   
   
   public double getScaleFactor() {
      return scaleFactor;
   }
   
   
   public void setScaleFactor(double scaleFactor) {
      this.scaleFactor = scaleFactor;
   }
   
   public java.awt.Color[] getColorsTable() {
      return this.colorsTable;
   }
   
   public void setColorsTable(java.awt.Color[] colorsTable) {
      this.colorsTable = colorsTable;
   }
   
   
   public double getStandardFactor() {
      return standardFactor;
   }
   
   public void setStandardFactor(double standardFactor) {
      this.standardFactor = standardFactor;
   }
   
}


