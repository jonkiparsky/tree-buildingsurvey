package admin;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

import admin.dao.AdminJdbcDao;

public class AdminApplication extends JFrame {
	private static final long serialVersionUID = 8731793917007308006L;
	public static ActionHandler actionHandler = null;
	public static TreeView treeView = null;
	private static TreeController treeController = null;
	public static TreeMap<String, Graph> studentNameToTree = null;
	public static ArrayList<Graph> graphs;
	private static ArrayList<Vertex> commonVertices = null; //organism nodes
	private static Map<String, Color> groupColors;
	private static ArrayList<VertexInfo> commonImages = null; //this simplifies things
	private static int currentGraphIndex = 0;
	public static AdminMultiWindow parent;
	public static boolean showNames = false;
	
	AdminApplication() {
    	super("AdminApplication");
		actionHandler = new ActionHandler();
		treeView = new TreeView();
		treeController = new TreeController();
		initCommonVertices();
		clearStudentData();
		loadTreesFromDirectory();
		try{
			loadTreesFromDB();
		}catch(Exception e){
			System.out.println("Error loading students from database: " + e);
			System.out.println("Database Connection could not be made.  Loading Student tree from local file.");
			String filePath = new String("trees/studentTrees");
			try{
				BufferedReader reader = new BufferedReader(new 
						InputStreamReader(new FileInputStream(filePath)));
				loadTreesFromParamTags(reader);
			}catch(FileNotFoundException fnfe){
				System.out.println("Could not find file: " + filePath);
			}
		}
		treeMapToArrayList();
		add(new JScrollPane(treeView));
		addMouseListener(treeController);
		setPreferredSize(new Dimension(928, 762));
		setJMenuBar(actionHandler.createMenuBar());
	}
    
    	/**
		* Create the GUI and show it.  For thread safety, this method 
		*should be invoked from the event-dispatching thread.
		*/
    private static void createAndShowGUI() {
        //Create and set up the window.
    	parent = new AdminMultiWindow();
    	treeView.setParent(parent.adminApplicationFrame);
    	actionHandler.setParent(parent.adminApplicationFrame);
    	parent.adminApplicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	parent.adminApplicationFrame.pack();
    	parent.adminApplicationFrame.setVisible(true);
    	parent.studentDataTableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	parent.studentDataTableFrame.pack();
    	parent.studentDataTableFrame.setVisible(true);
    	parent.questionDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	parent.questionDisplayFrame.pack();
    	parent.questionDisplayFrame.setVisible(true);
    	parent.shortestPathTableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	parent.shortestPathTableFrame.pack();
    	parent.shortestPathTableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
        
    public void setCurrentGraph(int index) {
    	currentGraphIndex = index;
		currentGraphIndex %= graphs.size();
		parent.questionDisplayFrame.setAnswersText(graphs.get(currentGraphIndex).getAnswers());
		setJMenuBar(actionHandler.createMenuBar());
		validate();
		repaint();
	    parent.shortestPathTableFrame.refreshTable();
	    treeView.paintComponent();
    }
    
    public void printGraphInfo() {
    	System.out.println(graphs.get(currentGraphIndex).getInfo());
    }
    
    public static Graph getCurrentGraph(){
    	if(currentGraphIndex < graphs.size())
    		return graphs.get(currentGraphIndex);
    	else{
    		currentGraphIndex = 0;
    		return new Graph("");
    	}
	}
    
    public static int getCurrentGraphIndex() {
    	return currentGraphIndex;
    }
    
    public static void toggleShowNames() {showNames = !showNames;}
 
    public static ArrayList<Vertex> getCommonVertices() {
    	return commonVertices;
    }
    
    public static String[] getCommonVertexNames() {
    	String[] returnVal = new String[commonVertices.size()];
    	int index = 0;
    	for(Vertex v: commonVertices) {
    		returnVal[index] = v.getName();
    		index++;
    	}
    	return returnVal;
    }
    
    public static int getVertexIndexByName(String name) {
    	int index = 0;
    	for(Vertex v: commonVertices) {
    		if(v.getName().equals(name)) return index;
    		index++;
    	}
    	return -1;
    }

    public void drawCurrentGraph(Graphics g) {
    	Graph graph = getCurrentGraph();	
		String studentName = graph.getStudentName();
    	g.setColor(Common.studentNameColor);
    	g.drawString(studentName, 5, 20);
    	Point size = graph.getLowerRight(g);
    	Dimension newSize = new Dimension(size.x, size.y);
    	treeView.setPreferredSize(newSize);
    	treeView.scrollRectToVisible(new Rectangle(0, 0, size.x, size.y));
    	treeView.revalidate();
    	treeView.repaint();
    	//System.out.println(size);
    	graph.render(g, new Point(0,0));
    	if(Common.screenPrintMode)
    		drawScreenPrintText((Graphics2D) g, size.y, graph);
    	
    }
    
    private void drawScreenPrintText(Graphics2D g2, int y, Graph g){
    	int width = treeView.getWidth();
    	int textHeight = Common.getStringBounds(g2,"QOgj").height;
		int yVal = Common.padding.height;
		yVal = y == 0 ? (3 * textHeight) : (y + textHeight);
		
		Common.drawCenteredString(g2, "Written Questions", Common.padding.width, yVal, width,
				textHeight + 4, Color.BLACK);
		yVal += textHeight + Common.padding.height;
		int i = 1;
		List<String> writtenAnswers = g.getAnswers();
		while(i < 3){
			for(String s : Common.breakStringByLineWidth(g2,
					i + ") " + 
					Common.questions[i-1],
					width - Common.padding.width * 2)){
				Common.drawCenteredString(g2, s, Common.padding.width, yVal, 0,
						textHeight + 4, Color.BLACK);
				yVal += textHeight + Common.padding.height;
			}
			yVal += textHeight + Common.padding.height;
			if(writtenAnswers.size() >= i){
				String answer = writtenAnswers.get(i-1);
				if(Common.isStringEmpty(answer))
					answer = "NO RESPONSE";
				for(String s : Common.breakStringByLineWidth(g2,answer,
						width - Common.padding.width * 2)){
					Common.drawCenteredString(g2, s, Common.padding.width, yVal, 0,
							textHeight + 4, Color.BLACK);
					yVal += textHeight + Common.padding.height;
				}
			}else{
				Common.drawCenteredString(g2, "NO RESPONSE", Common.padding.width, yVal, 0,
						textHeight + 4, Color.BLACK);
				yVal += textHeight + Common.padding.height;
			}
			yVal += textHeight + Common.padding.height;
			i++;
		}
	}
    
    private static void initCommonVertices() {
    	TreeMap<String, VertexInfo> organismNameToImage = 
				loadVerticesFromDirectory();
		commonVertices = new ArrayList<Vertex>();
		commonImages = new ArrayList<VertexInfo>();
		for(Map.Entry<String, VertexInfo> e : organismNameToImage.entrySet()) {
    		commonVertices.add(new Vertex(e.getValue(), new Point(0,0)));
            commonImages.add(e.getValue());
    	}
    }

    public static TreeMap<String, VertexInfo> loadVerticesFromDirectory() {
        TreeMap<String, VertexInfo> organismNameToImage = 
				new TreeMap<String, VertexInfo>();
        groupColors = new HashMap<String, Color>();
        try {
        	// read names of organisms and image file names from list.txt
        	BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("images/list.txt")));
        	String line = null;
        	List<String> parseLine = null;
        	String name = null;
        	StringBuffer imgFname = null;
        	List<String> types = null;
        	BufferedImage img = null;
        	int index = 0;
        	while ((line = reader.readLine()) != null) {
        		// load image from files, and map organism name to image
        		parseLine = Arrays.asList(line.split(","));
        		name = parseLine.get(0);
        		imgFname = new StringBuffer("images/").append(parseLine.get(1));
        		types = parseLine.subList(2, parseLine.size());
        		for(String type : types){
        			if(!groupColors.containsKey(type)){
        				groupColors.put(type, Common.defualtGroupColors[index]);
        				index++;
        			}
        		}
        		// System.out.println(organismName + " " + organismImageFilename);
        		InputStream imageis= new FileInputStream(imgFname.toString());
        		img = ImageIO.read(imageis);
        		organismNameToImage.put(name, new VertexInfo(name, types, img));
        		imageis.close();
        	}
        	reader.close();
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        return organismNameToImage;
    }
    
	public static void loadTreesFromDirectory() {
		try {
			String filePath = new String("trees/testTrees");
			BufferedReader reader = new BufferedReader(new 
					InputStreamReader(new FileInputStream(filePath)));
    		String linein = reader.readLine();
        	while(linein != null) {
        		String studentName = linein;
        		System.out.println("STUDENT: " + studentName);
        		linein = reader.readLine();
        		Graph graph = new Graph(studentName);
        		graph.setType(Graph.GraphType.Test);
        		studentNameToTree.put(new String("0_TEST_" + studentName),
        				updateGraphTree(linein, graph));
        		linein = reader.readLine();
        	}
        	reader.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	public static void loadTreesFromDB() throws Exception {
		AdminJdbcDao dao = new AdminJdbcDao();
		List<String[]> studentSurveys = dao.loadSurveys();
		List<String[]> students = dao.loadStudents();
		for(String[] studentSurvey : studentSurveys)
			loadStudent(studentSurvey);
		
		//Update directionality information
		for(String[] student : students){
			String name = student[0];
			String section = student[1].substring(8,10);
			int iSection = Integer.parseInt(section);
			if(studentNameToTree.containsKey(name))
				studentNameToTree.get(name).setDirectional(iSection % 2 == 0);
		}
	}
	
	public static String loadStudentsFromURL(URL url, String password){
		try{
			String passwd = URLEncoder.encode(password, "UTF-8");//lab09acce55
			String adminVal = URLEncoder.encode("true", "UTF-8");

			//https://www.securebio.umb.edu/cgi-bin/TreeSurvey.pl
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream());
			out.write("Passwd=" + passwd + "&AdminValue=" + adminVal);
			out.close();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							connection.getInputStream()));

			loadTreesFromParamTags(in);
		}catch(Exception e){
			return e.toString();
		}
		return null;
	}
	
	public static void loadTreesFromParamTags(BufferedReader reader) {
		
		try {
			String linein = reader.readLine();
			while(linein != null) {
				if(linein.startsWith("<param name=\"Student")){
					String[] paramParse = linein.split("\" value=\"");
					String studentData = paramParse[1];
					String[] studentDataItems = 
						studentData.split(Pattern.quote("+="));
					loadStudent(studentDataItems);
				}
				linein = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadStudent(String[] studentDataItems){
		String studentName = studentDataItems[0];
		String treeData = studentDataItems[2];
		ArrayList<String> answers = new ArrayList<String>();
		answers.add(studentDataItems[3]);
		answers.add(studentDataItems[4]);
		String section = studentDataItems[6].substring(8,10);
		int iSection = Integer.parseInt(section);
		boolean directional = iSection % 2 == 0;
		Graph graph = new Graph(studentName);
		graph.setAnswers(answers);
		graph.setDirectional(directional);
		studentNameToTree.put(studentName, updateGraphTree(treeData, graph));
	}
	
	public static Graph updateGraphTree(String treeData, Graph graph){
		String[] treeItems = treeData.split("#");
		for(String elements: treeItems){   //load vertices
			String[] attributes = elements.split(":");
			if(attributes.length < 6) continue;
			String type = attributes[0];
			int id = Integer.parseInt(attributes[1]);
			String elementName = attributes[2];
			int x = Integer.parseInt(attributes[3]);
			int y = Integer.parseInt(attributes[4]);
			boolean inTree = Boolean.parseBoolean(attributes[5]);
			if("O".equals(type)) 
			{
				if(inTree) 
				{
					graph.addVertex(id, new Vertex(commonImages.get(id), 
							new Point(x, y)));
				}
			}
			if("E".equals(type)) 
			{
				// only empty nodes in tree (exclude immortalNode)
				if(inTree) 
				{
					graph.addVertex(id, new Vertex(
							new VertexInfo(elementName), new Point(x, y)));
				}	
			}
		}
		for(String elements: treeItems) 
		{
			// load connections
			String[] attributes = elements.split(":");
			String type = attributes[0];
			if(!"C".equals(type)) continue;
			int id1 = Integer.parseInt(attributes[2]);
			int id2 = Integer.parseInt(attributes[3]);
			Vertex v1 = graph.getVertexByID(id1);
			Vertex v2 = graph.getVertexByID(id2);
			graph.addEdge(new Edge(v1, v2));
		}
		graph.loadHulls();
		return graph;
	}
	
	public static void clearStudentData(){
		studentNameToTree = new TreeMap<String, Graph>();
		graphs = new ArrayList<Graph>();
	}
	

	//convert treeMap to ArrayList

	private static void treeMapToArrayList()
	{
		for(Map.Entry<String,Graph> e : studentNameToTree.entrySet()){
			Graph temp = e.getValue();
			temp.initRelations();
			graphs.add(temp);
		}
	}
	
	public void createExportFile(String filename){
		FileWriter ryt;
		BufferedWriter out;
		StringBuffer line;
		try {
			ryt = new FileWriter(filename + ".csv");
			out = new BufferedWriter(ryt);
			TableModel model = parent.studentDataTableFrame.table.getModel();
			int colCount = model.getColumnCount();
			int rowCount = model.getRowCount();
			line = new StringBuffer();
			for(int col=0;col<colCount;col++)
				line.append(model.getColumnName(col)).append(",");
			out.write(line.deleteCharAt(line.lastIndexOf(",")).append("\n").toString());
			for(int row=0;row<rowCount;row++){
				line = new StringBuffer();
				for(int col=0;col<colCount;col++)
					line.append(model.getValueAt(row, col)).append(",");
				out.write(line.deleteCharAt(line.lastIndexOf(",")).append("\n").toString());
			}
			out.close();
		} catch (IOException e) {
			System.out.println("Could not export data file:" + e);
		}		
	}
	
	public static Map<String, Color> getColorChooser(){
		return groupColors;
	}
	
	public static Color getGroupColor(String group){
		Color returnVal = Color.BLACK;
		if(groupColors.containsKey(group))
			returnVal = groupColors.get(group);
		return returnVal;
	}
    
}
