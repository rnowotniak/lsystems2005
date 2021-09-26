import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class prop {
	public static void main(String[] args) throws Exception {
		Properties p = new Properties();
		p.setProperty("F", "FF+[+F-F-F]-[-F+F+F]");
		p.setProperty("f", "fff");
		p.setProperty("axiom", "f");
		p.store(new FileOutputStream(new File("test.prop")), null);
	}
}

