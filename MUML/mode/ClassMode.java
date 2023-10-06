package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;

import frame.*;
import UMLComponent.*;
import UMLComponent.Port.*;
import UMLComponent.Port.PortButton;

public class ClassMode extends Mode {

	public ClassMode() {
		this.setDisplayName("class");
	}

	@Override
	public void mousePressed(MouseEvent e, Canvas canvasPanel) {
		Object source = e.getSource();
		if (source == canvasPanel) {
			Point p = e.getPoint();
			UMLShapeContainer us = new UMLClass();
			us.setSelected(false);
			us.setLocation(p);
			us.addMouseListener(canvasPanel);
			us.addMouseMotionListener(canvasPanel);
			canvasPanel.addUMLShapeContainer(us);
			canvasPanel.add(us);
			canvasPanel.setComponentZOrder(us, 0); // Newly generated class will be at the top
			
			int resizeports[] = Port.CORNER_LAYOUT;
			for (int i = 0; i < resizeports.length; i++) {
				PortButton rc = new PortButton(resizeports[i], us);
				us.addPort(rc);
				canvasPanel.add(rc);
			}
			resizeports = Port.NORMAL_LAYOUT;
			for (int i = 0; i < resizeports.length; i++) {
				PortButton rc = new PortButton(resizeports[i], us);
				us.addPort(rc);
				canvasPanel.add(rc);
			}
			canvasPanel.repaint();
		}
	}
}
