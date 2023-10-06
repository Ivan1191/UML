package frame;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import mode.*;
import include.*;

public class MainFrame extends JFrame {
	private static Hashtable<String, ImageIcon> imgTable = loadImage();
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane;
	private boolean AddTab = true;

	public MainFrame() {
		
		Canvas canvasPanel;
		canvasPanel = new Canvas();
		canvasPanel.setName("tab1");
		
		// No work
		Workspace.getInstance().addCanvas(canvasPanel);
		Workspace.getInstance().setSelectedCanvas(canvasPanel);
		MainFrame.setKeyboardShortCut(canvasPanel);
		
		CanvasModeChooser.getInstance().setCanvas(canvasPanel);
		this.setTitle("UMLEditor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 650);
		this.setLayout(new BorderLayout());
		// canvasPanel.setModeChooser(canvasModeChooser);
		this.add(CanvasModeChooser.getInstance(), BorderLayout.WEST);
		
		
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
		JScrollPane jp = new JScrollPane(canvasPanel, v, h);		
		
		
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab(canvasPanel.getName(), jp);
		
		// Set the component at the index position as component
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, // Return the number of pane 
				new ButtonTabComponent(tabbedPane));
		
		// the page that can add new tab
		this.tabbedPane.addTab("", this.getCanvasBlockDefaultPage());
		
		AddButtonTabComponent tabcomponent = new AddButtonTabComponent(tabbedPane);
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tabcomponent);
		
		tabbedPane.addChangeListener(canvasTabListener); // Triggered after switching pagination
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				null, tabcomponent.getPane());
		splitPane.setDividerLocation(400);
		this.add(splitPane, BorderLayout.CENTER);
		this.setJMenuBar(createMenuBar());
		this.setVisible(true);
	}

	private final ChangeListener canvasTabListener = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			// Get the index of the selected tab
			int selectedIndex = tabbedPane.getSelectedIndex();
			if (selectedIndex < 0)
				return;
			
			// Given a component based on the selected index
			Component cp = tabbedPane.getComponentAt(selectedIndex);
			
			if (cp instanceof JScrollPane) {
				// c is a canvas that the pagination specify
				Component c = ((JScrollPane) cp).getViewport().getView();
				if (c instanceof Canvas) {
					// Set the selected canvas to the workspace object
					Workspace.getInstance().setSelectedCanvas((Canvas) c);
					// Reallocate mode to chooser in this canvas
					CanvasModeChooser.getInstance().setCanvas((Canvas) c);
					Canvas cc = (Canvas) c;
					if (Workspace.getInstance().getSelectedCanvas() != null)
						CanvasModeChooser.getInstance()
										.setMode(cc.getCanvasMode());
					//ModelExplorer.getInstance().changeFoucsCanvas((Canvas) c);
				}
			}
		}
	};

	public static void setKeyboardShortCut(Canvas canvas) {
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "DELETE");
		
		canvas.getActionMap().put("DELETE", new AbstractAction() {
			Canvas canvas;

			public AbstractAction init(Canvas canvas) {
				this.canvas = canvas;
				return this;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				SelectMode.removeSelectedUMLShape(canvas);
			}
		}.init(canvas));
		
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
		
		canvas.getActionMap().put("UP", new AbstractAction() {
			Canvas canvas;

			public AbstractAction init(Canvas canvas) {
				this.canvas = canvas;
				return this;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				SelectMode.moveSelectedUMLShape(0, -1, canvas);
			}
		}.init(canvas));
		
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
		canvas.getActionMap().put("DOWN", new AbstractAction() {
			Canvas canvas;

			public AbstractAction init(Canvas canvas) {
				this.canvas = canvas;
				return this;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				SelectMode.moveSelectedUMLShape(0, 1, canvas);
			}
		}.init(canvas));
		
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
		canvas.getActionMap().put("LEFT", new AbstractAction() {
			Canvas canvas;

			public AbstractAction init(Canvas canvas) {
				this.canvas = canvas;
				return this;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				SelectMode.moveSelectedUMLShape(-1, 0, canvas);
			}
		}.init(canvas));
		
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
		canvas.getActionMap().put("RIGHT", new AbstractAction() {
			Canvas canvas;

			public AbstractAction init(Canvas canvas) {
				this.canvas = canvas;
				return this;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				SelectMode.moveSelectedUMLShape(1, 0, canvas);
			}
		}.init(canvas));
	}

	private JMenuBar createMenuBar() {
		
		JMenuBar ret = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuItem;
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"Dealing with Files");
		ret.add(menu);
		menuItem = new JMenuItem("New UML",
				(ImageIcon) imgTable.get("Add-file"));
		menuItem.setMnemonic(KeyEvent.VK_U);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Save",
				(ImageIcon) imgTable.get("System-Save"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// samplePanel.groupSelectedObject();
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Save as",
				(ImageIcon) imgTable.get("System-Save-as"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// samplePanel.groupSelectedObject();
			}
		});
		menu.add(menuItem);
		menu = new JMenu("Edit");
		menuItem = new JMenuItem("Group",
				(ImageIcon) imgTable.get("Basic-Link"));
		menuItem.setToolTipText("Group selected UMLShape");
		menuItem.setMnemonic(KeyEvent.VK_G);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GroupMode.groupSelectedUMLShape(Workspace.getInstance()
						.getSelectedCanvas());
				// samplePanel.groupSelectedObject();
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("UnGroup",
				(ImageIcon) imgTable.get("Delete-link"));
		menuItem.setToolTipText("Ungroup selected UMLGroup");
		menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GroupMode.ungroupSelectedUMLShape(Workspace.getInstance()
						.getSelectedCanvas());
				// samplePanel.ungroupSelectedObject();
			}
		});
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Change Name",
				(ImageIcon) imgTable.get("Edition-Pencil"));
		menuItem.setToolTipText("Change selected Object Name");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectMode.changeNameSelectedUMLShape(Workspace.getInstance()
						.getSelectedCanvas());
				// samplePanel.changeObjectName();
			}
		});
		menu.add(menuItem);
		ret.add(menu);
		return ret;
	}

	// ¶×¤J¹Ï¤ù
	private static Hashtable<String, ImageIcon> loadImage() {
		Hashtable<String, ImageIcon> imgTable = new Hashtable<String, ImageIcon>();
		String fileIdx[] = { "Add-file", "Edition-Pencil", "System-Save",
				"System-Save-as", "Basic-Link", "Delete-link" };
		String fileName[] = { "Adds-Add-file-icon.png",
				"Image-Edition-Tools-Pencil-icon.png", "System-Save-icon.png",
				"System-Save-as-icon.png", "Very-Basic-Link-icon.png",
				"Adds-Delete-link-icon.png" };
		Image img;
		try {
			for (int i = 0; i < fileName.length; i++) {
				img = ImageIO.read(MainFrame.class.getResource("images/"
						+ fileName[i]));
				imgTable.put(fileIdx[i], new ImageIcon(img));
			}
		} catch (Exception e) {
			System.out.println("Err" + e.getMessage());
		}
		return imgTable;
	}

	public JPanel getCanvasBlockDefaultPage() {
		JPanel page = new JPanel();
		page.add(new JLabel("AUTHOR: ALEX"));
		return page;
	}
}
