/*
 * LSystemApplet.java
 *
 * Created on 17 lipiec 2005, 22:50
 */


/* <applet code="LSystemApplet.class" width="800" height="600"></applet> */

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class LSystemApplet extends javax.swing.JApplet {
   
   final static String LSYSTEMS_LIST_FILE = "lsystems.txt";
   
   final static int CONFIG_PANEL_WIDTH = 200;
   
   private BufferedImage image;
   private Canvas canvas;
   private DefaultListModel lsystemsListModel;
   private DefaultComboBoxModel prodsComboModel;
   
   private HashMap lsystems;
   private LSystem currentLSystem;
   
   public void init() {
      
      image = new BufferedImage(getWidth() - CONFIG_PANEL_WIDTH,
      getHeight(), BufferedImage.TYPE_INT_RGB);
      canvas = new Canvas(image);
      canvas.setApplet(this);
      lsystems = new HashMap();
      lsystemsListModel = new DefaultListModel();
      prodsComboModel = new DefaultComboBoxModel(new Character[] {
         new Character('x'), new Character('y')});
         
         try {
            loadData();
         }
         catch (IOException ex) {
            showMessage("I/O Error: \n" + ex.getMessage());
         }
         
         initComponents();
         
         getContentPane().add(canvas, BorderLayout.CENTER);
   }
   
   
   private void showMessage(String s) {
      JOptionPane.showMessageDialog(this, s);
   }
   
   private void loadData() throws IOException {
      InputStream in = null;
      BufferedReader br = null;
      try {
         in = getClass().getResourceAsStream(LSYSTEMS_LIST_FILE);
         br = new BufferedReader(new InputStreamReader(in));
      } catch (NullPointerException ex) {
         throw new IOException("Missing " + LSYSTEMS_LIST_FILE);
      }
      
      lsystemsListModel.clear();
      
      try {
         String line;
         for (;;) {
            line = br.readLine();
            if (line == null) {
               break;
            }
            if (!line.equals("")) {
               loadData(line);
            }
         }
      }
      finally {
         in.close();
      }
   }
   
   private void loadData(String filename) throws IOException {
      InputStream in = null;
      try {
         in = getClass().getResourceAsStream(filename);
      } catch (NullPointerException ex) {
         throw new IOException("Missing " + filename);
      }
      
      LSystem lsys = new LSystem(in);
      
      /* XXX */
      if (false) {
         Color f = new Color(255, 134, 43);
         Color l = new Color(185, 255, 48);
         int n = 6;
         int sr = l.getRed() - f.getRed();
         int sg = l.getGreen() - f.getGreen();
         int sb = l.getBlue() - f.getBlue();
         sr /= (n - 1);
         sg /= (n - 1);
         sb /= (n - 1);
         Color ct[] = new Color[n];
         ct[0] = f;
         ct[n - 1] = l;
         for (int i = 1; i < n - 1; i++) {
            ct[i] = new Color(f.getRed() + sr * i,
               f.getGreen() + sg * i, f.getBlue() + sb * i);
         }
         lsys.setColorsTable(ct);
      }
      
      lsystems.put(lsys.getName(), lsys);
      lsystemsListModel.addElement(lsys.getName());
   }
   
   private void initComponents() {//GEN-BEGIN:initComponents
      java.awt.GridBagConstraints gridBagConstraints;

      configPanel = new javax.swing.JPanel();
      jPanel1 = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      jLabel1 = new javax.swing.JLabel();
      jLabel11 = new javax.swing.JLabel();
      drawButton = new javax.swing.JButton();
      axiomCombo = new JComboBox(prodsComboModel);
      jLabel4 = new javax.swing.JLabel();
      jLabel5 = new javax.swing.JLabel();
      angleSpinner = new javax.swing.JSpinner();
      jLabel6 = new javax.swing.JLabel();
      recursionCombo = new javax.swing.JComboBox();
      jLabel7 = new javax.swing.JLabel();
      startTextField = new javax.swing.JTextField();
      jLabel8 = new javax.swing.JLabel();
      jLabel9 = new javax.swing.JLabel();
      prodsList = new javax.swing.JTextArea();
      jLabel10 = new javax.swing.JLabel();
      jScrollPane1 = new javax.swing.JScrollPane();
      lsystemsList = new javax.swing.JList();
      jPanel2 = new javax.swing.JPanel();
      jLabel12 = new javax.swing.JLabel();
      scaleSpinner = new javax.swing.JSpinner();
      directionSpinner = new javax.swing.JSpinner();
      jLabel3 = new javax.swing.JLabel();
      factorSpinner = new javax.swing.JSpinner();

      addComponentListener(new java.awt.event.ComponentAdapter() {
         public void componentResized(java.awt.event.ComponentEvent evt) {
            appletResized(evt);
         }
      });

      configPanel.setLayout(new java.awt.GridBagLayout());

      configPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
      configPanel.setPreferredSize(new Dimension(CONFIG_PANEL_WIDTH, getHeight()));
      jPanel1.setLayout(new java.awt.GridBagLayout());

      jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
      jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
      jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel2.setText("Systems");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 0.1;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
      jPanel1.add(jLabel2, gridBagConstraints);

      jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
      jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel1.setText("Lindenmeyer's");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 0.1;
      jPanel1.add(jLabel1, gridBagConstraints);

      jLabel11.setFont(new java.awt.Font("Dialog", 1, 10));
      jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel11.setText("2005 (C) Robert Nowotniak");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      jPanel1.add(jLabel11, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.ipadx = 2;
      gridBagConstraints.weightx = 0.1;
      gridBagConstraints.insets = new java.awt.Insets(3, 3, 19, 3);
      configPanel.add(jPanel1, gridBagConstraints);

      drawButton.setText("RYSUJ");
      drawButton.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
      drawButton.setEnabled(false);
      drawButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            drawButtonActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.ipady = 11;
      gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
      configPanel.add(drawButton, gridBagConstraints);

      axiomCombo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            axiomComboActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
      configPanel.add(axiomCombo, gridBagConstraints);

      jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel4.setText("K\u0105t:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 4;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 8);
      configPanel.add(jLabel4, gridBagConstraints);

      jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel5.setText("Aksjomat:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 8);
      configPanel.add(jLabel5, gridBagConstraints);

      angleSpinner.setModel(new SpinnerNumberModel(0.0, -360.0, 360.0, 10.0));
      angleSpinner.setValue(new Double(0.0));
      angleSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            angleChanged(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 4;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
      configPanel.add(angleSpinner, gridBagConstraints);

      jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel6.setText("Rekurencja:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 6;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 8);
      configPanel.add(jLabel6, gridBagConstraints);

      recursionCombo.removeAllItems();
      for (int i = 1; i <= 13; i++) {
         recursionCombo.addItem(new Integer(i));
      }
      recursionCombo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            recursionComboActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 6;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
      configPanel.add(recursionCombo, gridBagConstraints);

      jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel7.setText("Pocz\u0105tek:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 7;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 8);
      configPanel.add(jLabel7, gridBagConstraints);

      startTextField.setEditable(false);
      startTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
      startTextField.setText("(0,0)");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 7;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
      configPanel.add(startTextField, gridBagConstraints);

      jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel8.setText("Kierunek:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 8;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 8);
      configPanel.add(jLabel8, gridBagConstraints);

      jLabel9.setText("Regu\u0142y produkcji:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 9;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
      configPanel.add(jLabel9, gridBagConstraints);

      prodsList.setLineWrap(true);
      prodsList.setRows(4);
      prodsList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
      prodsList.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            prodsListChanged(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 10;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
      configPanel.add(prodsList, gridBagConstraints);

      jLabel10.setText("LSystemy:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 11;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
      configPanel.add(jLabel10, gridBagConstraints);

      lsystemsList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
      lsystemsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
      lsystemsList.setModel(lsystemsListModel);
      lsystemsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
         public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            lsystemSelected(evt);
         }
      });
      lsystemsList.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            lsystemClicked(evt);
         }
      });

      jScrollPane1.setViewportView(lsystemsList);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 12;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weighty = 0.5;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
      configPanel.add(jScrollPane1, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 13;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.weighty = 0.1;
      configPanel.add(jPanel2, gridBagConstraints);

      jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel12.setText("Skala:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 3;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 9);
      configPanel.add(jLabel12, gridBagConstraints);

      scaleSpinner.setModel(new SpinnerNumberModel(1.0, 0.001, 100.0, 0.02));
      scaleSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            scaleChanged(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 3;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
      configPanel.add(scaleSpinner, gridBagConstraints);

      directionSpinner.setModel(new SpinnerNumberModel(0.0, -360.0, 360.0, 10.0));
      directionSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            directionChanged(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 8;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 4);
      configPanel.add(directionSpinner, gridBagConstraints);

      jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel3.setText("Czynnik:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 5;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 8);
      configPanel.add(jLabel3, gridBagConstraints);

      factorSpinner.setModel(new SpinnerNumberModel(1.0, 0.001, 100.0, 0.1));
      factorSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            factorChanged(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 5;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
      configPanel.add(factorSpinner, gridBagConstraints);

      getContentPane().add(configPanel, java.awt.BorderLayout.EAST);

   }//GEN-END:initComponents
   
   private void factorChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_factorChanged
      if (currentLSystem != null) {
         currentLSystem.setStandardFactor((((Number) factorSpinner.getValue()).doubleValue()));
         previewLSystem();
      }
   }//GEN-LAST:event_factorChanged
   
   private void directionChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_directionChanged
      if (currentLSystem != null) {
         currentLSystem.setBaseAngle(((Number) directionSpinner.getValue()).floatValue());
         previewLSystem();
      }
   }//GEN-LAST:event_directionChanged
   
   private void scaleChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_scaleChanged
      if (currentLSystem != null) {
         currentLSystem.setScaleFactor(((Number) scaleSpinner.getValue()).doubleValue());
         previewLSystem();
      }
   }//GEN-LAST:event_scaleChanged
   
   private void appletResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_appletResized
      image = new BufferedImage(getWidth() - CONFIG_PANEL_WIDTH,
      getHeight(), BufferedImage.TYPE_INT_RGB);
      canvas.setImage(image);
      repaint();
   }//GEN-LAST:event_appletResized
   
   private void prodsListChanged(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_prodsListChanged
      if (currentLSystem != null) {
         try {
            currentLSystem.parseRulesDescription(prodsList.getText());
         }
         catch (IOException ex) {
            showMessage("B³±d sk³adni w regule.\n" + ex.getMessage());
         }
         if (! currentLSystem.getProds().keySet().contains(
               prodsComboModel.getSelectedItem())) {
            prodsComboModel.removeAllElements();
            Iterator i = currentLSystem.getProds().keySet().iterator();
            while (i.hasNext()) {
               Character key = (Character) i.next();
               prodsComboModel.addElement(key);
            }
         }
      }
   }//GEN-LAST:event_prodsListChanged
   
   private void angleChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_angleChanged
      if (currentLSystem != null) {
         currentLSystem.setAngle(((Number) angleSpinner.getValue()).floatValue());
         previewLSystem();
      }
   }//GEN-LAST:event_angleChanged
   
   private void axiomComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_axiomComboActionPerformed
      if (currentLSystem != null) {
         currentLSystem.setAxiom((Character) axiomCombo.getSelectedItem());
         previewLSystem();
      }
   }//GEN-LAST:event_axiomComboActionPerformed
   
   private void recursionComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recursionComboActionPerformed
      currentLSystem.setLevel(((Integer) recursionCombo.getSelectedItem()).intValue());
   }//GEN-LAST:event_recursionComboActionPerformed
   
   private void lsystemClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lsystemClicked
      if (evt.getClickCount() == 2) {
         selectLSystem();
         drawLSystem();
      }
   }//GEN-LAST:event_lsystemClicked
   
   private void previewLSystem() {
      if (currentLSystem != null) {
         int oldlevel = currentLSystem.getLevel();
         /* set preview low recursion level */
         //currentLSystem.setLevel(3);
         drawLSystem();
         currentLSystem.setLevel(oldlevel);
      }
   }
   
   public void drawLSystem() {
      if (currentLSystem != null) {
         canvas.clear();
         Graphics2D g = image.createGraphics();
         canvas.reorientGraphics(g);
         currentLSystem.drawIt(g);
         canvas.repaint();
      }
   }
   
   private void selectLSystem() {
      if (lsystemsList.getSelectedIndex() != -1) {
         setActiveLSystem((String) lsystemsList.getSelectedValue());
      }
   }
   
   private void drawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawButtonActionPerformed
      drawLSystem();
   }//GEN-LAST:event_drawButtonActionPerformed
   
   private void lsystemSelected(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lsystemSelected
      if (!evt.getValueIsAdjusting()) {
         selectLSystem();
      }
   }//GEN-LAST:event_lsystemSelected
   
   public void updatePanel() {
      LSystem lsys = currentLSystem;
      scaleSpinner.setValue(new Float(lsys.getScaleFactor()));
      angleSpinner.setValue(new Float(lsys.getAngle()));
      recursionCombo.setSelectedItem(new Integer(lsys.getLevel()));
      startTextField.setText("("+lsys.getStart().x+","+lsys.getStart().y+")");
      directionSpinner.setValue(new Float(lsys.getBaseAngle()));
   }
   
   private void setActiveLSystem(String name) {
      LSystem lsys = (LSystem) lsystems.get(name);
      Character axiom = lsys.getAxiom();
      currentLSystem = lsys;
      drawButton.setEnabled(true);
      
      updatePanel();
      
      String prodsString = "";
      HashMap prods = lsys.getProds();
      prodsComboModel.removeAllElements();
      Iterator i = prods.keySet().iterator();
      while (i.hasNext()) {
         Character key = (Character) i.next();
         prodsString = prodsString + key + ": " + (String) prods.get(key) + "\n";
         prodsComboModel.addElement(key);
      }
      prodsList.setText(prodsString);
      
      axiomCombo.setSelectedItem(axiom);
   }
   
   
   public LSystem getCurrentLSystem() {
      return currentLSystem;
   }
   
   
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JSpinner angleSpinner;
   private javax.swing.JComboBox axiomCombo;
   private javax.swing.JPanel configPanel;
   private javax.swing.JSpinner directionSpinner;
   private javax.swing.JButton drawButton;
   private javax.swing.JSpinner factorSpinner;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JList lsystemsList;
   private javax.swing.JTextArea prodsList;
   private javax.swing.JComboBox recursionCombo;
   private javax.swing.JSpinner scaleSpinner;
   private javax.swing.JTextField startTextField;
   // End of variables declaration//GEN-END:variables
   
}

class Canvas extends JPanel {
   
   private BufferedImage image;
   private LSystemApplet applet;
   private boolean directionChanging;
   private Point firstPoint, secondPoint;
   
   static private int DIRECTION_INDICATOR_LENGTH = 100;
   
   public Canvas(BufferedImage i) {
      image = i;
      addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            Canvas.this.mouseClicked(e);
         }
         public void mousePressed(MouseEvent e) {
            Canvas.this.mousePressed(e);
         }
         public void mouseReleased(MouseEvent e) {
            Canvas.this.mouseReleased(e);
         }
      });
      addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Canvas.this.mouseDragged(e);
         }
      });
   }
   
   public void mousePressed(MouseEvent e) {
      firstPoint = e.getPoint();
      mouseClicked(e);
   }
   
   public void mouseDragged(MouseEvent e) {
      directionChanging = true;
      secondPoint = e.getPoint();
      repaint();
   }
   public void mouseReleased(MouseEvent e) {
      directionChanging = false;
      
      if (e.getPoint().equals(firstPoint)) {
         return;
      }
      
      double direction = Math.atan(
      1.0 * (e.getX() - firstPoint.x) / (firstPoint.y - e.getY()));
      direction = Math.toDegrees(direction);
      if (e.getY() > firstPoint.y) {
         direction += 180;
      }
      direction = -direction;
      
      LSystem lsys = applet.getCurrentLSystem();
      if (lsys != null) {
         lsys.setBaseAngle((float) direction);
         applet.updatePanel();
         applet.drawLSystem();
      }
      repaint();
   }
   
   public void mouseClicked(MouseEvent e) {
      if (applet != null) {
         LSystem lsys = applet.getCurrentLSystem();
         if (lsys != null) {
            lsys.setStart(globalToRelativePoint(e.getPoint()));
            lsys.setBaseAngle(0f);
            applet.updatePanel();
            applet.drawLSystem();
         }
      }
   }
   
   
   public void setApplet(LSystemApplet applet) {
      this.applet = applet;
   }
   
   public void paint(Graphics g) {
      g.drawImage(image, 0, 0, this);
      
      g.setColor(Color.WHITE);
      g.drawRect(0, 0, image.getWidth() -1 , image.getHeight() - 1);
      
      if (directionChanging) {
         drawDirectionIndicator(g);
      }
      
      g.setColor(Color.YELLOW);
      reorientGraphics(g);
      g.fillOval(-2, -2, 4, 4);
      
   }
   
   private void drawDirectionIndicator(Graphics g) {
      g.setColor(Color.WHITE);
      Point p1 = firstPoint;
      Point p2 = new Point();
      int x = secondPoint.x - firstPoint.x;
      int y = secondPoint.y - firstPoint.y;
      double d = Math.sqrt(x * x + y * y);
      int w = (int) (DIRECTION_INDICATOR_LENGTH * x / d);
      int h = (int) (DIRECTION_INDICATOR_LENGTH * y / d);
      g.drawLine(p1.x, p1.y, p1.x + w, p1.y + h);
   }
   
   private Point globalToRelativePoint(Point p) {
      Point result = new Point();
      result.x = p.x - image.getWidth() / 2;
      result.y = image.getHeight() * 5 / 6 - p.y;
      return result;
   }
   
   public void reorientGraphics(Graphics g) {
      LSystem lsys = applet.getCurrentLSystem();
      if (lsys != null) {
         Graphics2D g2d = (Graphics2D) g;
         g2d.scale(-1, 1);
         g2d.rotate(Math.toRadians(180));
         g2d.translate(lsys.getStart().x, lsys.getStart().y);
         g2d.translate(image.getWidth() / 2, -5 * image.getHeight() / 6);
      }
   }
   
   public void clear() {
      Graphics2D g = image.createGraphics();
      g.clearRect(0, 0, image.getWidth(), image.getHeight());
   }
   
   public java.awt.image.BufferedImage getImage() {
      return image;
   }
   
   public void setImage(java.awt.image.BufferedImage image) {
      this.image = image;
   }
   
}
