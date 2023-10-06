package UMLComponent;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.*;

import frame.MainFrame;

public class UMLUseCase extends BasicObject implements MouseInputListener {
	private JTextField className;
	private Font displayFont = new Font("Serial", Font.BOLD, 18);

	public UMLUseCase() {
		super();
		className = new JTextField("CaseA");
		this.setName(className.getText());
		className.setBackground(new Color(245, 245, 245));
		className.setFont(displayFont);
		className.setHorizontalAlignment(JTextField.CENTER);
		className.setEditable(false);
		className.setOpaque(false);

		this.setBackground(new Color(220, 220, 220));
		this.setLayout(null);
		this.add(className);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setOpaque(false);
		this.setBorder(null);
		this.setSize(200, 110);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(220, 220, 220));
		g.fillOval(0, 0, this.getWidth(), this.getHeight());
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_BEVEL, 1.0f, null, 0.0f));
		g2d.setColor(Color.BLACK);
		g2d.drawOval(1, 1, this.getWidth()-2, this.getHeight()-2);
		
		className.setBounds(40, this.getHeight() / 2 - 50 / 2,
				this.getWidth() - 40 * 2, 50);
		super.paint(g);
	}

	@Override
	public String getName() {
		if(className != null)
			return className.getText();
		return super.getName();
	}

	@Override
	public String getTypeName() {
		return "UML_USECASE";
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
			className.setEditable(false);
			className.setFocusable(false);
		}
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
