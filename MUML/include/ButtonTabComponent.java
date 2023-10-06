package include;

import javax.swing.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Component to be used as tabComponent; Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to
 */

public class ButtonTabComponent extends JPanel {
	protected final JTabbedPane pane;
	private final TabButton button;

	public ButtonTabComponent(final JTabbedPane pane) {
		// unset default FlowLayout's gaps
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
		this.pane = pane;
		setOpaque(false); // make the ground of JLabel and button be transparent

		// make JLabel read titles from JTabbedPane
		JLabel label = new JLabel() {
			public String getText() {
				// Returns the option index of the specified component
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				if (i != -1) {
					return pane.getTitleAt(i);
				}
				return null;
			}
		};

		add(label);
		// add more space between the name label and the cancel button
		label.setBorder(BorderFactory.createEmptyBorder(-1, 0, 0, 5));
		// tab button
		this.button = new TabButton();
		add(this.button);
		// add more space to the top of the component
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	public void removeTabButton() {
		this.remove(button);
	}

	public void addTabButton(JButton button) {
		removeTabButton();
		this.add(button);
	}

	private class TabButton extends JButton implements ActionListener {
		public TabButton() {
			int size = 18;
			setPreferredSize(new Dimension(size, size));
			setToolTipText("close this tab");
			// Make the button looks the same
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			// Making nice rollover effect
			// we use the same listener for all buttons
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			int i = pane.indexOfTabComponent(ButtonTabComponent.this);
			// close this tab
			if (i != -1) {
				int option = JOptionPane.showConfirmDialog(null, "Close "
						+ pane.getTitleAt(i) + " ?");
				if (option == 0) {
					pane.remove(i);
				}
			}
		}

		// paint the cross
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.WHITE);
			}
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
					- delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
					- delta - 1);
			g2.dispose();
		}
	}

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			// When component belongs to the instance of the class, return true
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true); // highlight the button
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false); 
			}
		}
	};
}
