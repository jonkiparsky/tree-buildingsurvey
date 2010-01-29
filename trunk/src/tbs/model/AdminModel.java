//TBS version 0.4
//Model: creates and maintains the logical structure underlying TBS

package tbs.model;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JComponent;

import tbs.TBSApplet;
import tbs.TBSGraphics;
import tbs.TBSUtils;
import tbs.controller.AdminController;
import tbs.controller.TBSController;
import tbs.model.admin.Student;
import tbs.properties.PropertyType;
import tbs.view.AdminView;
import tbs.view.TBSButtonType;
import tbs.view.TextEntryBox;
import tbs.view.prompt.Prompt;
import tbs.view.prompt.admin.AnalysisPrompt;
import tbs.view.prompt.admin.RadioQuestionReviewPrompt;
import tbs.view.prompt.admin.WrittenQuestionReviewPrompt;

public class AdminModel implements TBSModel
{
	private AdminView view;
	private TBSController controller;
	private List<TBSButtonType> buttons;
	private List<ModelElement> modelElements;
	private int MESerialNumber=0;
	private TBSApplet applet;
	private Prompt prompt;
	private WrittenQuestionReviewPrompt writtenQuestionReviewPrompt;
	private RadioQuestionReviewPrompt radioQuestionReviewPrompt;
	private AnalysisPrompt analysisPrompt;
	private TextEntryBox textEntryBox;
	private Map<PropertyType, Properties> propertiesMap;
	private List<Student> students;
	private Student selectedStudent;

	public AdminModel(TBSApplet app, Graphics2D g2,
			TreeMap<String, BufferedImage> organismNameToImage,
			List<String> students, Map<PropertyType, Properties> propertiesMap) {
		applet = app;
		this.propertiesMap = propertiesMap;
		buttons = TBSButtonType.getButtons(true);
		modelElements = new LinkedList<ModelElement>();
		createButtons(g2); // call before creating model elements
		createModelElements(g2, organismNameToImage);
		createStudents(g2, students);
		selectedStudent = this.students.get(0);
		String tree = selectedStudent.getTree();
		if(!TBSUtils.isStringEmpty(tree))
			loadTree(tree);
		writtenQuestionReviewPrompt = new WrittenQuestionReviewPrompt(this);
		/*
		 * Until Professor White says otherwise we will be eliminating the radio
		 * portion of the open-response
		 * radioQuestionReviewPrompt = new RadioQuestionReviewPrompt(this);
		 */
		view = new AdminView(this);
		controller = new AdminController(this, view);
	}

	public void changeSavedTree(int studentIndex){
		/*
		 * Make sure your don't re-calculate the selected student's
		 * information
		 */
		System.out.println("Selected Index:" + studentIndex);
		if(studentIndex != students.indexOf(selectedStudent)){
			selectedStudent = students.get(studentIndex);
			System.out.println("Selected Student:" + selectedStudent.getName());
			String tree = selectedStudent.getTree();
			resetModel();
			if(!TBSUtils.isStringEmpty(tree))
				loadTree(tree);
			writtenQuestionReviewPrompt = null;
			analysisPrompt = null;
		}
	}

	public void resetModel(){
		int size = modelElements.size();
		while(size > TBSGraphics.numOfOrganisms+1){
			removeFromTree(modelElements.get(size-1));
			size = modelElements.size();
		}
		List<Node> inTreeElements = inTreeElements();
		for(Node n : inTreeElements)
			removeFromTree(n);
	}

	public void removeFromTree(ModelElement m){
		if(m == null)
			return;
		if(m instanceof Node){
			Node n = (Node) m;
			unlink(n);
			if(n instanceof OrganismNode){
				n.setInTree(false);
				((OrganismNode) n).resetPosition();
				return;
			}
		}else{
			Connection c = (Connection) m;
			c.getFrom().getConnectedTo().remove(c.getTo());
			c.getTo().getConnectedFrom().remove(c.getFrom());
		}
		modelElements.remove(m);
	}

	/**
	 * Unlink had to live in Model when connections were
	 * one-way. Now, this simply calls the Node-based two-way unlink.
	 */
	public void unlink(Node n)
	{
		List<Connection> connections = getConnectionsByNode(n);
		for(Connection c : connections){
			c.getFrom().getConnectedTo().remove(c.getTo());
			c.getTo().getConnectedFrom().remove(c.getFrom());
			modelElements.remove(c);
		}
	}

	public void setModelElements(List<ModelElement> newList){
		modelElements = newList;
	}

	public List<Student> getStudents(){
		return students;
	}

	/**
	 * Returns a handle for the View associated with this Model
	 */
	public JComponent getView() {
		return view;
	}

	/**
	 * Returns a handle for the Controller associated with this Model.
	 */
	public TBSController getController() {
		return controller;
	}

	/**
	 * Returns a handle for the Applet.
	 */
	public TBSApplet getApplet() {
		return applet;
	}	

	/**
	 * Returns a serial number for a model element. Serial numbers start
	 * at 0 and simply increment; they are unique within a tree, but not
	 * outside it.
	 */
	public int getSerial()
	{
		int sn = MESerialNumber;
		System.out.println(MESerialNumber);
		MESerialNumber++;
		return sn;
	}

	/**
	 * Adds a ModelElement to the ArrayList of items this Model knows
	 * about.
	 */
	public void addElement(ModelElement m) {
		modelElements.add(m);
	}	

	/**
	 * returns the ith ModelElement in the list.
	 */
	public ModelElement getElement(int i) {
		return modelElements.get(i);
	}

	public void removeElement(int i) {
		modelElements.remove(i);
	}

	public int findIndexByElement(ModelElement m){
		/*
		 * For OrganismNodes we can just use serialId
		 * because they are the first loaded into the List and never actually removed from the
		 * List.
		 */		
		if(m instanceof OrganismNode)
			return (m.getId());
		else
			return modelElements.indexOf(m);
	}

	//findIndexById method that is called when a saved tree is not being loaded
	public int findIndexById(Integer id){
		return findIndexById(id, null);
	}

	public int findIndexById(Integer id, List<ModelElement> parsedElements){
		if(id <= TBSGraphics.numOfOrganisms)
			return (id);
		/*
		 * As you can see we can begin searching the List of ModelElement
		 * objects from where the number of loaded organisms ends since these
		 * items (with the exception of the immortalEmptyNode can be removed
		 * and thus have their serialId changed/out of order.
		 */
		List<ModelElement> elements;
		if(parsedElements != null)
			elements = parsedElements;
		else
			elements = modelElements;
		for(int i=(TBSGraphics.numOfOrganisms-1);i<elements.size();i++){
			if(elements.get(i).getId().equals(id))
				return i;
		}
		return -1;
	}	

	/**
	 * Returns the ModelElement with a given serial number
	 * This method relies on the fact that objects are added in serial#
	 * order, and remain sorted, although they may be deleted. If this
	 * assumption ceases to be true, this method will fail. 
	 */
	public ModelElement getElementBySN(int sn)
	{
		ModelElement me;
		int checknum = sn;
		List<ModelElement> model = modelElements;
		do 
		{
			me = (ModelElement)model.get(checknum);
			if (me.getId() == sn)
				return me;
			checknum--;
		} while (checknum >= me.getId());
		return null;
	}

	/**
	 * returns the complete List of Model Elements.
	 */
	public List<ModelElement> getElements() {
		return modelElements;
	}

	/**
	 * Assigns value me to the ith member of the list. 
	 */
	public void setElement(int i, ModelElement me) {
		modelElements.set(i, me);
	}

	public void createButtons(Graphics2D g2)
	{
		Dimension buttonDimensions = TBSGraphics.get2DStringBounds(g2,buttons);
		TBSGraphics.buttonsWidth = buttonDimensions.width + 
		TBSGraphics.buttonsXPadding * 2;
		TBSGraphics.buttonsHeight = buttonDimensions.height + 
		TBSGraphics.buttonsYPadding * 2;
	}

	// called during setup to create organism nodes
	protected void createModelElements(Graphics2D g2, 
			TreeMap<String, BufferedImage> organismNameToImage) {
		EmptyNode.g2 = g2;
		for(Map.Entry<String, BufferedImage> e : organismNameToImage.entrySet()) {
			addElement(new OrganismNode( getSerial(), e.getKey(), 
					new Point(), e.getValue()));
		}

		TBSGraphics.emptyNodeLeftX = 0;
		TBSGraphics.emptyNodeUpperY = 0;
	}

	protected void createStudents(Graphics2D g2, List<String>  studentStringArrays) {
		int currentY = 0;
		TBSGraphics.studentNodeHeight = 0;
		students = new LinkedList<Student>();
		int lines = 0;
		for(String studentStringArray : studentStringArrays){
			Student temp = new Student(g2, studentStringArray);
			students.add(temp);
			if(temp.getNodeName().size() > lines) 
				lines = temp.getNodeName().size();
		}
		TBSGraphics.studentNodeHeight = lines * TBSGraphics.textHeight;
		TBSGraphics.studentNodeHeight += TBSGraphics.paddingWidth * 2;
		for(Student s : students) {
			s.setAnchorPoint(new Point(0, currentY));
			currentY += TBSGraphics.studentNodeHeight + TBSGraphics.ySpacing;
		}
	}	

	/**
	 * PrintConnections() prints out a list of all connections in each
	 * model element. 
	 * Connection to a Node (toConnection) is indicated by ->
	 * Trace connection from a Node (fromConnection) indicated by <-
	 * Written for testing connections; functionality may not have
	 * survived rewrite of connections methodology.
	 */
	public void printConnections()
	{
		Node n;
		for(ModelElement m : modelElements){
			if(m instanceof Node){
				n = (Node) m;
				for(Node to : n.getConnectedTo())
					System.out.println(n.getName()+" -> "+to.getName());
				for(Node from : n.getConnectedFrom())
					System.out.println(from.getName()+" -> "+n.getName());
			}
		}
	}

	public boolean hasConnections(){
		for(int i=TBSGraphics.numOfOrganisms;i<modelElements.size(); i++){
			if(modelElements.get(i) instanceof Connection)
				return true;
		}
		return false;
	}

	public boolean hasEmptyNodes(){
		for(int i=(TBSGraphics.numOfOrganisms-1);i<modelElements.size();i++){
			if(modelElements.get(i) instanceof EmptyNode)
				return true;
		}
		return false;
	}

	/**
	 * Returns the list of active elements
	 */	
	public List<Node> inTreeElements(){
		List<Node> inTreeElements = new LinkedList<Node>();
		for(ModelElement m : modelElements){
			if(m instanceof Node){
				Node n = (Node) m;
				if(n.isInTree())
					inTreeElements.add(n);
			}
		}
		return inTreeElements;
	}

	public Properties getProperties(PropertyType pt) {
		return propertiesMap.get(pt);
	}

	public List<Connection> getConnectionsByNode(Node n){
		List<Connection> connections = new LinkedList<Connection>();
		Connection c;
		for (ModelElement me: modelElements)
		{
			if(me instanceof Connection){
				c = (Connection) me;
				if(c.hasNode(n)){
					connections.add(c);
				}
			}
		}
		return connections;
	}

	/**
	 * Take a list of strings extracted from a file by
	 * the perl script contained within the website, and recreate the stored tree.
	 * Two passes: first pass recreates nodes, second makes connections. 
	 */
	public void loadTree(String tree)
	{
		List<ModelElement> savedTree = modelElements;
		String[] treeItems = tree.split("#");
		try{
			for(String item : treeItems)
			{
				String data[] = item.split(":");
				if (data[0].equals("O"))
					loadOrganismNode(data, savedTree);
				else if (data[0].equals("E")){
					loadEmptyNode(data, savedTree);
				}else if (data[0].equals("C"))
					savedTree.add(loadConnection(data, savedTree));
				else
				{
					System.out.println("Problem in loadTree");
					break;
				}
			}

			// Sort the local list
			Collections.sort(savedTree, TBSGraphics.elementIdComparator);
			modelElements = savedTree;
			MESerialNumber = savedTree.size()+1;
			System.out.println("loadTree: end");
		}catch(NumberFormatException e){
			System.out.println(new StringBuffer("There was an error parsing saved tree for ")
			.append(selectedStudent.getName()).append(". ")
			.append("This tree has been reset.").toString());
		}
	}

	/**
	 * Load an OrganismNode. Might be possible to combine this with
	 * loadEmptyNode(). 
	 * This does not create any new OrganismNodes; it simply resets the
	 * old ones and sets their values where we want them. This saves
	 * reloading the image files, but it means we have to always use the
	 * same set of organisms. 
	 */
	public void loadOrganismNode(String[] data, List<ModelElement> tempTree) throws NumberFormatException {
		int id=0,x=0,y=0;
		boolean inTree = Boolean.parseBoolean(data[5]);
		if(inTree){
			try{
				id = Integer.parseInt(data[1]);
				x = Integer.parseInt(data[3]);
				y = Integer.parseInt(data[4]);
			}catch(NumberFormatException e){
				System.out.println(new StringBuffer("StudentModel:loadOrganismNode:Error parsing organism data (id:")
				.append(data[1]).append(",x:").append(data[3]).append("y:").append(data[4]).append(")").toString());
				throw e;
			}
			int elementIndex = findIndexById(id, tempTree);
			OrganismNode node = (OrganismNode) tempTree.get(elementIndex);
			Point pt = new Point(x,y);
			node.setAnchorPoint(pt);
			node.setInTree(inTree);
			tempTree.set(elementIndex, node);
		}
	}

	/**
	 * Load an EmptyNode. Might be possible to combine this with
	 * loadOrganismNode().
	 */
	public void loadEmptyNode(String[] data, List<ModelElement> tempTree) throws NumberFormatException {
		int id=0,x=0,y=0;
		boolean inTree = Boolean.parseBoolean(data[5]);
		if(inTree){
			String name = data[2];
			try{
				id = Integer.parseInt(data[1]);
				x = Integer.parseInt(data[3]);
				y = Integer.parseInt(data[4]);
			}catch(NumberFormatException e){
				System.out.println("StudentModel:loadEmptyNode:Error parsing empty node (" +
						name + ") data (id:" + data[1] +
						",x:" + data[3] + "y:" + data[4]+")");
				throw e;
			}
			Point pt = new Point(x,y);
			EmptyNode node = new EmptyNode(id, pt);
			node.rename(name);
			node.setInTree(inTree);
			tempTree.add(node);
		}
	}

	public ModelElement loadConnection(String[] data, List<ModelElement> parsedElements) 
	throws NumberFormatException {
		int id=0,from=0,to=0;
		try{
			id = Integer.parseInt(data[1]);
			from = Integer.parseInt(data[2]);
			to = Integer.parseInt(data[3]);
		}catch(NumberFormatException e){
			System.out.println(new StringBuffer("StudentModel:loadConnection:Error parsing connection data (id:").append(data[1])
					.append(",from id:").append(data[2]).append("to id:").append(data[3]).append(")").toString());
			throw e;
		}
		int fromIndex = findIndexById(from, parsedElements);
		int toIndex = findIndexById(to, parsedElements);

		Node fromNode = (Node) parsedElements.get(fromIndex);
		Node toNode = (Node) parsedElements.get(toIndex);
		Connection conn = new Connection(id, fromNode, toNode);
		return (ModelElement) conn;
	}

	public String exportTree(){
		StringBuffer export = new StringBuffer();
		for(ModelElement m : modelElements)
			export.append(m.dump()).append("#");
		return export.toString();

	}

	public Prompt getPrompt() {
		return prompt;
	}

	public void clearPrompt() {
		this.prompt = null;
	}

	public void promptUser(Prompt prompt) {
		this.prompt = prompt;
		view.refreshGraphics();
	}

	public void questionReview() {
		if(writtenQuestionReviewPrompt == null)
			writtenQuestionReviewPrompt = new WrittenQuestionReviewPrompt(this);
		else
			writtenQuestionReviewPrompt.setFinished(false);
		this.prompt = writtenQuestionReviewPrompt;
		view.refreshGraphics();
	}

	public void analyze(){
		if(analysisPrompt == null)
			analysisPrompt = new AnalysisPrompt(this);
		else
			analysisPrompt.setFinished(false);
		this.prompt = analysisPrompt;
		view.refreshGraphics();
	}

	public TextEntryBox getTextEntryBox() {
		return textEntryBox;
	}

	public void setTextEntryBox(TextEntryBox textEntryBox) {
		this.textEntryBox = textEntryBox;
	}

	public List<TBSButtonType> getButtons() {
		return buttons;
	}

	public Boolean isButtonActive(TBSButtonType b) {return false;}

	public Student getStudent() {return selectedStudent;}

}
