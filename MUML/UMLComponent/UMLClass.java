package UMLComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputListener;

import frame.MainFrame;

public class UMLClass extends BasicObject implements MouseInputListener {
	public JTextField className;
	private Font displayFont = new Font("Serial", Font.BOLD, 18);
	public UMLClass() {
		super();
		this.setBackground(new Color(220, 220, 220));
		this.setLayout(new BorderLayout());
		className = new JTextField("ClassA");
		this.setName(className.getText());
		className.setBackground(new Color(245, 245, 245));
		className.setFont(displayFont);
		className.setHorizontalAlignment(JTextField.CENTER);
		className.setEditable(true);	

		add(className, BorderLayout.NORTH);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBorder(new LineBorder(Color.BLACK, 2));
	}

	@Override
	public String getName() {
		if(className != null)
			return className.getText();
		return super.getName();
	}

	@Override
	public String getTypeName() {
		return "UML_CLASS";
	}

	@Override
	public void setName(String s) {
		super.setName(s);
		className.setText(s);
	}

	@Override
	public void setSelected(boolean b) {
		super.setSelected(b);
		if (b == true) {
			className.setEditable(true);
			className.setFocusable(true);
		} else {
			className.setFocusable(false);
			className.setEditable(false);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		className.setBounds(5, 20 / 2, this.getWidth() - 5 * 2, 50);
		super.paint(g);
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		className.addMouseListener(l);
	}
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		className.addMouseMotionListener(l);
	}
}
