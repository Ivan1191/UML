package frame;

import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import mode.*;
import java.awt.event.*;

public class CanvasModeChooser extends JPanel implements ModeChooser,
		ItemListener {
	public static CanvasModeChooser cmc = null;
	private Canvas canvas = null;
	private ButtonGroup listGroup;
	private final Mode mode[] = { new SelectMode(), new AssociationMode(),
			new GeneralMode(), new CompositionMode(), new ClassMode(),
			new UsecaseMode() };
	private final ModeButton[] listButton = new ModeButton[mode.length];

	public static CanvasModeChooser getInstance() {
		if (cmc == null)
			cmc = new CanvasModeChooser(null);
		return cmc;
	}

	private CanvasModeChooser(Canvas canvas) {
		this.canvas = canvas;
		this.listGroup = new ButtonGroup();
		this.setLayout(new GridLayout(mode.length, 1));
		for (int i = 0; i < mode.length; i++) {
			ModeButton button = new ModeButton(mode[i]);
			listButton[i] = button;
			String buttonName = mode[i].getDisplayName();
			ImageIcon selectedImg = null, unselectedImg = null;
			try {
				unselectedImg = new ImageIcon(ImageIO.read(this.getClass()
						.getResource("images/" + buttonName + "0.png")));
				
				selectedImg = new ImageIcon(ImageIO.read(this.getClass()
						.getResource("images/" + buttonName + "1.png")));
				
				if(i==0) {
					
					Image image = selectedImg.getImage(); 
					Image newImg = image.getScaledInstance(87, 87, java.awt.Image.SCALE_AREA_AVERAGING);
					selectedImg = new ImageIcon(newImg);
					
					image = unselectedImg.getImage(); 
					newImg = image.getScaledInstance(87, 87, java.awt.Image.SCALE_AREA_AVERAGING);
					unselectedImg = new ImageIcon(newImg);
					
				}
				
			} catch (Exception ee) {
				System.out.println(ee.getMessage());
			}
			button.addItemListener(this);
			button.setSelectedIcon(selectedImg);
			button.setIcon(unselectedImg);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setContentAreaFilled(false); // Make the area outside the picture transparent
			button.setToolTipText(buttonName);
			if (i == 0)
				button.setSelected(true);
			this.add(button);
			this.listGroup.add(button); 
		}
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	// Initialize which mode is selected function of each canvas
	public void setMode(Mode mode) {
		if (mode == null) {
			listButton[0].setSelected(true);
		} else {
			for (int i = 0; i < listButton.length; i++) {
				if (listButton[i].getMode() == mode)
					listButton[i].setSelected(true);
			}
		}
	}

	@Override
	public Mode getMode() {
		return ((ModeButton.getButtonModel) (listGroup.getSelection()))
				.getMode();
	}

	public void itemStateChanged(ItemEvent e) {
		Object item = e.getItem();
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (item instanceof ModeChooser) {
				ModeChooser gmi = (ModeChooser) item;
				if (canvas != null) {
					// Pass the mouse commands of selected mode into the canvas
					this.canvas.setCanvasMode(gmi.getMode()); 
				}
			}
		}
	}
}
