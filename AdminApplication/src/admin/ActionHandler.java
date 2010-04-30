package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import admin.StudentDataColumns.ColumnDataHandler;

public class ActionHandler extends JPanel {
	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0x60655B840361BFA4L;

	public Action exitAction;
	public Action printAction;
	public Action exportAction;
	public AdminApplication parent;
	private List<JMenuItem> hullItems;
	private List<JMenuItem> collisionItems;
	private List<JMenu> optimalItems;

	public class ExitAction extends AbstractAction {

		private static final long serialVersionUID = 1740545338294704279L;

		public ExitAction() {
			super("Exit");
		}

		// @0verride
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	public class PrintAction extends AbstractAction {

		private static final long serialVersionUID = 1740545322294704279L;

		public PrintAction() {
			super("Print Graph");
		}

		// @0verride
		public void actionPerformed(ActionEvent arg0) {
			parent.printGraphInfo();
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(AdminApplication.treeView);
			if (printJob.printDialog()) {
				try {
					printJob.print();
				} catch (PrinterException pe) {
					System.out.println("Error printing: " + pe);
				}
			}
		}
	}

	public class NamesAction extends AbstractAction {

		private static final long serialVersionUID = 3382645405034163126L;

		public NamesAction() {
			super();
		}

		// @0verride
		public void actionPerformed(ActionEvent e) {
			AdminApplication.toggleShowNames();
			JMenuItem item = (JMenuItem) e.getSource();
			item.setText("Display Names"
					+ (AdminApplication.showNames ? " \u2713" : ""));
		}
	}

	public class HullAction extends AbstractAction {

		private static final long serialVersionUID = 3382645405034163126L;
		private int hullIndex;

		public HullAction(int hullIndex) {
			super();
			this.hullIndex = hullIndex;
		}

		// @0verride
		public void actionPerformed(ActionEvent e) {
			AdminApplication.getCurrentGraph().displaySubDropDownItem(
					SubDropDownType.HULL, hullIndex);
			redrawBooleans();
		}
	}

	public class CollisionAction extends AbstractAction {

		private static final long serialVersionUID = 3382645405034163126L;
		private int collisionIndex;

		public CollisionAction(int collisionIndex) {
			super();
			this.collisionIndex = collisionIndex;
		}

		// @0verride
		public void actionPerformed(ActionEvent e) {
			AdminApplication.getCurrentGraph().displaySubDropDownItem(
					SubDropDownType.COLLISION, collisionIndex);
			redrawBooleans();
		}
	}

	public class OptimalAction extends AbstractAction {

		private static final long serialVersionUID = 3382645405034163126L;
		private int optimalIndex;
		private boolean quick;

		public OptimalAction(int optimalIndex, boolean quick) {
			super();
			this.optimalIndex = optimalIndex;
			this.quick = quick;
		}

		// @0verride
		public void actionPerformed(ActionEvent e) {
			OptimalHulls oh = AdminApplication.getCurrentGraph()
					.getOptimalHulls(true).get(optimalIndex);
			if (!oh.getDisplay()) {
				AdminApplication.getCurrentGraph().displaySubDropDownItem(
						SubDropDownType.OPTIMAL_HULL, optimalIndex);
				redrawBooleans();
			}
			if (quick)
				oh.fullOptimization();
			else
				oh.startOptimization();

		}
	}

	public class ColumnDisplayAction extends AbstractAction {

		private static final long serialVersionUID = -251547965416261110L;
		private ColumnDataHandler cdh;

		public ColumnDisplayAction(ColumnDataHandler cdh) {
			super(cdh.getName() + (cdh.isVisible() ? " \u2713" : ""));
			this.cdh = cdh;
		}

		// @0verride
		public void actionPerformed(ActionEvent e) {
			cdh.toggleVisible();
			JMenuItem item = (JMenuItem) e.getSource();
			item.setText(cdh.getName() + (cdh.isVisible() ? " \u2713" : ""));
			AdminApplication.parent.studentDataTableFrame.refreshTable();
		}
	}

	public class ExportAction extends AbstractAction {

		private static final long serialVersionUID = -5425238186912620684L;

		private JFileChooser fc;

		public ExportAction() {
			super("Export Data");
			fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		// @0verride
		public void actionPerformed(ActionEvent arg0) {
			int returnVal = fc.showSaveDialog(parent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				parent.createExportFile(file.getPath());
				System.out.println("Exporting data to: " + file.getPath());
			} else
				System.out.println("Export Command cancelled by user.");
		}
	}

	public ActionHandler() {
		exitAction = new ExitAction();
		printAction = new PrintAction();
		exportAction = new ExportAction();
		hullItems = new LinkedList<JMenuItem>();
		collisionItems = new LinkedList<JMenuItem>();
		optimalItems = new LinkedList<JMenu>();

	}

	public void setParent(AdminApplication parent) {
		this.parent = parent;
	}

	public JMenuBar createMenuBar() {
		hullItems.clear();
		collisionItems.clear();
		optimalItems.clear();
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenuItem printItem;
		JMenuItem exitItem;

		// Create the menu bar.
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Menu");
		menuBar.add(fileMenu);

		// a group of JMenuItems
		printItem = new JMenuItem(printAction);
		exitItem = new JMenuItem(exitAction);
		fileMenu.add(printItem);
		// View Open-Responses
		final JMenuItem openResponse = new JMenuItem("View Open-Responses");
		ActionListener openResponseListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				boolean visible = AdminApplication.parent.questionDisplayFrame
						.isVisible();
				AdminApplication.parent.questionDisplayFrame
						.setVisible(!visible);
			}
		};
		openResponse.addActionListener(openResponseListener);
		fileMenu.add(openResponse);
		// Data Import
		final JMenuItem dataImport = new JMenuItem("Import Data");
		final JDialog dataImporter = getDataImporter();
		ActionListener dataImportListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				dataImporter.setVisible(true);
			}
		};
		dataImport.addActionListener(dataImportListener);
		fileMenu.add(dataImport);
		// Reset Data
		final JMenuItem dataReset = new JMenuItem("Reset Data");
		ActionListener dataResetListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AdminApplication.clearStudentData();
				parent.setCurrentGraph(0);
			}
		};
		dataReset.addActionListener(dataResetListener);
		fileMenu.add(dataReset);

		JMenuItem names = new JMenuItem("Display Vertex Info"
				+ (AdminApplication.showNames ? " \u2713" : ""));
		names.addActionListener(new NamesAction());
		fileMenu.add(names);
		Graph tempGraph = AdminApplication.getCurrentGraph();
		List<ConvexHull> groups = tempGraph.getHulls(true);
		if (!groups.isEmpty()) {
			final JMenuItem colorEditorItem = new JMenuItem("Group Colors");
			final JDialog colorEditor = getColorEditor();
			ActionListener colorEditorListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					colorEditor.setVisible(true);
				}
			};
			colorEditorItem.addActionListener(colorEditorListener);
			fileMenu.add(colorEditorItem);
			JMenu groupMenu = new JMenu("Groups");
			for (int i = 0; i < groups.size(); i++) {
				ConvexHull tempCH = groups.get(i);
				JMenuItem menuItem = new JMenuItem(tempCH.toString());
				menuItem.addActionListener(new HullAction(i));
				groupMenu.add(menuItem);
				hullItems.add(menuItem);
			}
			fileMenu.add(groupMenu);
			List<HullCollision> collisions = tempGraph.getHullCollisions(true);
			if (!collisions.isEmpty()) {
				JMenu collisionMenu = new JMenu("Group Collisions");
				JMenu optimalMenu = new JMenu("Optimal Groups");
				for (int i = 0; i < collisions.size(); i++) {
					HullCollision tempHC = collisions.get(i);
					JMenuItem HCItem = new JMenuItem(tempHC.toString());
					HCItem.addActionListener(new CollisionAction(i));
					collisionMenu.add(HCItem);
					collisionItems.add(HCItem);
					final JMenu optimalSubMenu = new JMenu(tempHC.toString());
					JMenuItem quickItem = new JMenuItem("Show");
					quickItem.addActionListener(new OptimalAction(i, true));
					optimalSubMenu.add(quickItem);
					JMenuItem iterativeItem = new JMenuItem("Watch");
					iterativeItem
							.addActionListener(new OptimalAction(i, false));
					optimalSubMenu.add(iterativeItem);
					optimalMenu.add(optimalSubMenu);
					optimalItems.add(optimalSubMenu);
				}
				fileMenu.add(collisionMenu);
				fileMenu.add(optimalMenu);
			}
			JMenuItem deselect = new JMenuItem("Clear Selections");
			ActionListener deselectListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					AdminApplication.getCurrentGraph().deselectAllItems();
					redrawBooleans();
				}
			};
			deselect.addActionListener(deselectListener);
			fileMenu.add(deselect);
		}
		fileMenu.add(exitItem);

		return menuBar;
	}

	public JMenuBar getDataMenuBar(StudentDataColumns studentDataColumns) {
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenu showColumnsMenu;
		JMenuItem exportItem;
		JMenuItem exitItem;

		// Create the menu bar.
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Menu");
		showColumnsMenu = new JMenu("Show Columns");
		int index = 0;
		for (ColumnDataHandler cdh : studentDataColumns.columnDataHandlers) {
			if (cdh.isAlwaysVisible())
				continue;
			showColumnsMenu.add(new ColumnDisplayAction(cdh));
			index++;
		}
		menuBar.add(fileMenu);
		menuBar.add(showColumnsMenu);

		// View Shortest-Path Table
		JMenuItem shortestPath = new JMenuItem("View Shortest-Path");
		ActionListener shortestPathListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AdminApplication.parent.shortestPathTableFrame
						.setVisible(!AdminApplication.parent.shortestPathTableFrame
								.isVisible());
			}
		};
		shortestPath.addActionListener(shortestPathListener);
		// Export Table Into CSV
		exportItem = new JMenuItem(exportAction);
		// Exit Application
		exitItem = new JMenuItem(exitAction);
		fileMenu.add(shortestPath);
		fileMenu.add(exportItem);
		fileMenu.add(exitItem);

		return menuBar;
	}

	public JDialog getColorEditor() {
		JDialog colorDialog = new JDialog(parent, "Edit Group Colors", true);
		colorDialog.setSize(500, 550);
		JPanel colorEditor = new JPanel(new BorderLayout());

		// Set up the banner at the top of the window
		final JLabel groupLabel = new JLabel();
		groupLabel.setForeground(Color.WHITE);
		groupLabel.setBackground(Color.blue);
		groupLabel.setOpaque(true);
		groupLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		groupLabel.setPreferredSize(new Dimension(100, 65));

		JPanel groupPanel = new JPanel(new BorderLayout());
		groupPanel.add(groupLabel, BorderLayout.CENTER);
		groupPanel
				.setBorder(BorderFactory.createTitledBorder("Selected Group"));

		Set<String> groups = AdminApplication.getColorChooser().keySet();
		final JComboBox groupSelection = new JComboBox();
		groupSelection.setSize(100, 40);
		String selectedGroup = null;
		for (String group : groups) {
			groupSelection.addItem(group);
			if (selectedGroup == null)
				selectedGroup = group;
		}
		groupSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedGroup = ((JComboBox) e.getSource())
						.getSelectedItem().toString();
				groupLabel.setText(selectedGroup);
				groupLabel.setBackground(AdminApplication.getColorChooser()
						.get(selectedGroup));
			}
		});
		groupLabel.setText(selectedGroup);
		groupLabel.setHorizontalAlignment(JLabel.CENTER);
		groupLabel.setBackground(AdminApplication.getColorChooser().get(
				selectedGroup));

		// Set up color chooser for setting text color
		final JColorChooser tcc = new JColorChooser(colorEditor.getForeground());
		ChangeListener colorEditorListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				Color newColor = tcc.getColor();
				groupLabel.setBackground(newColor);
				Set<String> groups = AdminApplication.getColorChooser()
						.keySet();
				int index = 0;
				int selectedIndex = groupSelection.getSelectedIndex();
				String selectedGroup = "";
				for (String group : groups) {
					if (index == selectedIndex) {
						selectedGroup = group;
						break;
					}
					index++;
				}
				AdminApplication.getColorChooser().put(selectedGroup, newColor);
			}
		};
		tcc.getSelectionModel().addChangeListener(colorEditorListener);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Group Color"));

		JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		selectionPanel.add(groupSelection);
		selectionPanel.setBorder(BorderFactory
				.createTitledBorder("Select Group"));

		colorEditor.add(selectionPanel, BorderLayout.PAGE_START);
		colorEditor.add(groupPanel, BorderLayout.CENTER);
		colorEditor.add(tcc, BorderLayout.PAGE_END);
		colorDialog.getContentPane().add(colorEditor);
		colorDialog.setLocationRelativeTo(AdminApplication.treeView);
		return colorDialog;
	}

	public JDialog getDataImporter() {
		final JDialog importDialog = new JDialog(parent, "Import Data", true);
		importDialog.setSize(525, 525);
		// URL Import
		JPanel urlImport = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JPanel errorPanel = new JPanel();
		errorPanel.setSize(475, 200);
		final JLabel errorLabel = new JLabel();
		errorLabel.setBorder(BorderFactory.createTitledBorder("Error"));
		errorPanel.add(errorLabel);
		errorPanel.setVisible(false);
		JPanel urlPanel = new JPanel();
		final JTextField urlField = new JTextField(30);
		urlField.setText("https://www.securebio.umb.edu/cgi-bin/TreeSurvey.pl");
		urlPanel.add(urlField);
		urlPanel.setBorder(BorderFactory.createTitledBorder("Enter URL"));
		JPanel passPanel = new JPanel();
		final JPasswordField passwordField = new JPasswordField(20);
		passPanel.add(passwordField);
		passPanel.setBorder(BorderFactory
				.createTitledBorder("Enter Admin Password"));
		final JButton importURLButton = new JButton("Import");
		importURLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String urlText = urlField.getText();
				URL url;
				char[] pass = passwordField.getPassword();
				try {
					url = new URL(urlText);
					errorPanel.setVisible(false);
				} catch (Exception ex) {
					passwordField.setText("");
					errorLabel.setText(Common.wrapText(errorLabel, 475,
							new String[] { ex.toString() }));
					errorPanel.setVisible(true);
					return;
				}
				if (!Common.isPasswordCorrect(pass)) {
					passwordField.setText("");
					errorLabel.setText("Invalid Password");
					errorPanel.setVisible(true);
					return;
				}
				String error = AdminApplication.loadStudentsFromURL(url, String
						.valueOf(pass));
				if (error != null) {
					errorLabel.setText(Common.wrapText(errorLabel, 475,
							new String[] { error }));
					errorPanel.setVisible(true);
				} else {
					errorPanel.setVisible(false);
					parent.setCurrentGraph(0);
					importDialog.setVisible(false);
				}
				passwordField.setText("");
			}
		});
		urlImport.add(urlPanel);
		urlImport.add(passPanel);
		urlImport.add(importURLButton);

		// Student File Import
		JPanel fileImport = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JComboBox fileTypeSelection = new JComboBox();
		fileTypeSelection.setSize(100, 40);
		fileTypeSelection.addItem("HTML View source");
		fileTypeSelection.addItem("Deployable Applet Directory");
		fileTypeSelection.addItem("Test Trees");
		fileTypeSelection.setSelectedIndex(0);
		JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		selectionPanel.add(fileTypeSelection);
		selectionPanel.setBorder(BorderFactory
				.createTitledBorder("Select Group"));
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser
				.setBorder(BorderFactory.createTitledBorder("Select Folder"));
		fileChooser.setControlButtonsAreShown(false);
		final JButton importFileButton = new JButton("Import Files From Folder");
		importFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String error = null;
				int selectedFileType = fileTypeSelection.getSelectedIndex();
				switch (selectedFileType) {
				case 0:
					error = AdminApplication
							.loadTreesFromHTMLSource(fileChooser
									.getSelectedFile().getPath());
					break;
				case 1:
					error = AdminApplication
							.loadStudentsFromDeployableFolder(fileChooser
									.getSelectedFile());
					break;
				case 2:
					error = AdminApplication.loadTestTrees(fileChooser
							.getSelectedFile().getPath());
					break;
				}
				if (error != null) {
					errorLabel.setText(Common.wrapText(errorLabel, 475,
							new String[] { error }));
					errorPanel.setVisible(true);
				} else {
					errorPanel.setVisible(false);
					parent.setCurrentGraph(0);
					importDialog.setVisible(false);
				}
			}
		});
		fileTypeSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedFileType = fileTypeSelection.getSelectedIndex();
				switch (selectedFileType) {
				case 0:
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					break;
				case 1:
					fileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					break;
				case 2:
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					break;
				}

			}
		});
		fileImport.add(fileTypeSelection);
		fileImport.add(fileChooser);
		fileImport.add(importFileButton);

		// DB Import
		JPanel dbImport = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel unamePanel = new JPanel();
		unamePanel.setSize(475, 40);
		final JTextField unameField = new JTextField(30);
		unamePanel.add(unameField);
		unamePanel.setBorder(BorderFactory.createTitledBorder("Username"));
		JPanel pwordPanel = new JPanel();
		pwordPanel.setSize(475, 40);
		final JPasswordField pwordField = new JPasswordField(20);
		pwordPanel.add(pwordField);
		pwordPanel.setBorder(BorderFactory.createTitledBorder("Password"));
		final JButton importDBButton = new JButton("Import");
		importDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = unameField.getText();
				char[] pass = pwordField.getPassword();
				String pw = new String(pass);
				if (Common.isStringEmpty(user)) {
					errorLabel.setText("Username is required");
					errorPanel.setVisible(true);
					pwordField.setText("");
					return;
				}
				if (Common.isStringEmpty(pw)) {
					errorLabel.setText("Password is required");
					errorPanel.setVisible(true);
					return;
				}
				String error = AdminApplication.loadTreesFromDB(user, pw);
				if (error != null) {
					errorLabel.setText(Common.wrapText(errorLabel, 475,
							new String[] { error }));
					errorPanel.setVisible(true);
				} else {
					errorPanel.setVisible(false);
					parent.setCurrentGraph(0);
					importDialog.setVisible(false);
				}
				pwordField.setText("");
				unameField.setText("");
			}
		});
		dbImport.add(unamePanel);
		dbImport.add(pwordPanel);
		dbImport.add(importDBButton);

		JTabbedPane importTabs = new JTabbedPane();
		importTabs.add(urlImport, "URL");
		importTabs.add(fileImport, "File");
		importTabs.add(dbImport, "Database");
		ChangeListener tabChangeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				errorLabel.setText("");
				errorPanel.setVisible(false);
			}
		};
		importTabs.addChangeListener(tabChangeListener);
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		masterPanel.add(errorPanel);
		masterPanel.add(importTabs);
		importDialog.getContentPane().add(masterPanel);
		importDialog.setLocationRelativeTo(AdminApplication.treeView);
		importDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				errorLabel.setText("");
				errorPanel.setVisible(false);
			}
		});
		return importDialog;
	}

	public void redrawBooleans() {
		if (!hullItems.isEmpty()) {
			List<ConvexHull> groups = AdminApplication.getCurrentGraph()
					.getHulls(true);
			for (int i = 0; i < groups.size(); i++)
				hullItems.get(i).setText(groups.get(i).toString());
			if (!collisionItems.isEmpty()) {
				List<HullCollision> collisions = AdminApplication
						.getCurrentGraph().getHullCollisions(true);
				for (int i = 0; i < collisions.size(); i++)
					collisionItems.get(i).setText(collisions.get(i).toString());
				List<OptimalHulls> optimals = AdminApplication
						.getCurrentGraph().getOptimalHulls(true);
				for (int i = 0; i < optimals.size(); i++)
					optimalItems.get(i).setText(optimals.get(i).toString());
			}
		}
	}

}
