//TBS version 0.4
//Model: creates and maintains the logical structure underlying TBS

package tbs.model;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JComponent;

import tbs.TBSApplet;
import tbs.TBSGraphics;
import tbs.TBSUtils;
import tbs.controller.StudentController;
import tbs.controller.StudentControllerTest;
import tbs.controller.TBSController;
import tbs.model.admin.Student;
import tbs.model.history.Add;
import tbs.model.history.Command;
import tbs.model.history.Delete;
import tbs.model.history.Drag;
import tbs.model.history.Link;
import tbs.model.history.Unlink;
import tbs.properties.PropertyType;
import tbs.view.OpenQuestionButtonType;
import tbs.view.StudentView;
import tbs.view.TBSButtonType;
import tbs.view.TextEntryBox;
import tbs.view.prompt.Prompt;
import tbs.view.prompt.student.HelpPrompt;
import tbs.view.prompt.student.RadioQuestionPrompt;
import tbs.view.prompt.student.WelcomePrompt;
import tbs.view.prompt.student.WrittenQuestionPrompt;

public class StudentModel implements TBSModel
{
	private StudentView view;
	private TBSController controller;
	private List<TBSButtonType> buttons;
	private List<ModelElement> modelElements;
	private ModelElement selectedModelElement;
	private List<ModelElement> selectedTwoWay;
	private EmptyNode immortalEmptyNode;
	private Stack<Command> history;
	private int MESerialNumber=0;
	private TBSApplet applet;
	private Prompt prompt;
	private WrittenQuestionPrompt writtenQuestionPrompt;
	private RadioQuestionPrompt radioQuestionPrompt;
	private HelpPrompt helpPrompt;
	private TextEntryBox textEntryBox;
	private Map<PropertyType,Properties> propertiesMap;
	private Map<TBSButtonType, Boolean> buttonStates;
	private Student student;
	private StudentControllerTest sct;

	public StudentModel(TBSApplet app, Graphics2D g2,
			TreeMap<String, BufferedImage> organismNameToImage,
			String studentString, Map<PropertyType, Properties> propertiesMap) {
		applet = app;
		this.propertiesMap = propertiesMap;
		buttons = TBSButtonType.getButtons(false);
		modelElements = new LinkedList<ModelElement>();
		selectedModelElement = null;
		selectedTwoWay = null;
		createButtons(g2); // call before creating model elements
		createModelElements(g2, organismNameToImage);
		buttonStates = new HashMap<TBSButtonType, Boolean>();
		for(TBSButtonType b : buttons)
			buttonStates.put(b, b.isActiveWhenCreated());
		student = new Student(g2, studentString);
		String tree = student.getTree();
		if(!TBSUtils.isStringEmpty(tree)){
			loadTree(tree);
			int inTreeElementCount = inTreeElements().size();
			if(inTreeElementCount > 0){
				buttonStates.put(TBSButtonType.DELETE, true);
				buttonStates.put(TBSButtonType.CLEAR, true);
				if(inTreeElementCount > 1){
					buttonStates.put(TBSButtonType.LINK, true);
					if(hasEmptyNodes())
						buttonStates.put(TBSButtonType.LABEL, true);
					if(hasConnections())
						buttonStates.put(TBSButtonType.UNLINK, true);
				}
			}
		}
		view = new StudentView(this);
		controller = new StudentController(this, view);
		history = new Stack<Command>();
		prompt = new WelcomePrompt(this);
		helpPrompt = new HelpPrompt(this);
		writtenQuestionPrompt = new WrittenQuestionPrompt(this);
		/*
		 * Until Professor White says otherwise we will be eliminating the radio
		 * portion of the open-response
		 * radioQuestionPrompt = new RadioQuestionPrompt(this);
		 */
		sct = null;
	}

	public void setModelElements(List<ModelElement> newList){
		modelElements = newList;
	}

	public void resetModel(){
		while(modelElements.size() > TBSGraphics.numOfOrganisms+1)
			removeFromTree(modelElements.get(modelElements.size()-1));
		List<Node> inTreeElements = inTreeElements();
		for(Node n : inTreeElements){
			removeFromTree(n);
		}
		MESerialNumber = modelElements.size();
		history = new Stack<Command>();
		buttonStates.put(TBSButtonType.UNDO, false);
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

	public ModelElement getSelectedModelElement() {
		return selectedModelElement;
	}

	public void setSelectedModelElement(ModelElement selectedModelElement) {
		this.selectedModelElement = selectedModelElement;
	}

	public List<ModelElement> getSelectedTwoWay() {
		return selectedTwoWay;
	}

	public void setSelectedTwoWay(List<ModelElement> selectedTwoWay) {
		this.selectedTwoWay = selectedTwoWay;
	}

	/**
	 * Assigns value me to the ith member of the list. 
	 */
	public void setElement(int i, ModelElement me) {
		modelElements.set(i, me);
	}

	public EmptyNode getImmortalEmptyNode() {
		return immortalEmptyNode;
	}

	public Stack<Command> getHistory() {
		return history;
	}

	public void setHistory(Stack<Command> history) {
		this.history = history;
	}

	public void addActionToHistory(Command c){
		if(history.isEmpty())
			buttonStates.put(TBSButtonType.UNDO, true);
		history.push(c);
		System.out.println(new StringBuffer("Added action(").append(c.toString())
				.append(") to history.").toString());
	}

	public Command removeActionFromHistory(){
		Command c = history.pop();
		if(c instanceof Unlink)
			buttonStates.put(TBSButtonType.UNLINK, true);
		if(history.isEmpty() || history.size() == 0)
			buttonStates.put(TBSButtonType.UNDO, false);
		return c;
	}

	public void createButtons(Graphics2D g2)
	{
		Dimension buttonDimensions = TBSGraphics.get2DStringBounds(g2,buttons);
		TBSGraphics.buttonsWidth = buttonDimensions.width + 
		TBSGraphics.buttonsXPadding * 2;
		TBSGraphics.buttonsHeight = buttonDimensions.height + 
		TBSGraphics.buttonsYPadding * 2;

		buttonDimensions = TBSGraphics.getStringBounds(g2,"Questions");
		Dimension checkDimension = TBSGraphics.getStringBounds(g2, " \u2713");
		TBSGraphics.questionButtonsWidth = buttonDimensions.width + checkDimension.width +
		TBSGraphics.buttonsXPadding * 2;
	}

	// called during setup to create organism nodes
	protected void createModelElements(Graphics2D g2, 
			TreeMap<String, BufferedImage> organismNameToImage) {
		EmptyNode.g2 = g2;
		int currentY = TBSGraphics.buttonsHeight + 10;
		Dimension stringDimensions = TBSGraphics.get2DStringBounds(g2, organismNameToImage.keySet());
		Dimension imageDimensions = TBSGraphics.get2DImageBounds(g2, organismNameToImage.values());
		TBSGraphics.organismNodeWidth = stringDimensions.width + imageDimensions.width + 
		TBSGraphics.paddingWidth * 2;
		if(stringDimensions.height > imageDimensions.height)
			TBSGraphics.organismNodeHeight = stringDimensions.height;
		else
			TBSGraphics.organismNodeHeight = imageDimensions.height;
		for(Map.Entry<String, BufferedImage> e : organismNameToImage.entrySet()) {
			addElement(new OrganismNode( getSerial(), e.getKey(), 
					new Point(0, currentY), e.getValue()));
			currentY += TBSGraphics.organismNodeHeight + TBSGraphics.ySpacing;
		}

		//create left-side empty node
		TBSGraphics.immortalNodeLabelWidth = (int) TBSGraphics.getStringBounds(g2, TBSGraphics.immortalNodeLabel).getWidth();
		TBSGraphics.emptyNodeLeftX = (TBSGraphics.organismNodeWidth - (TBSGraphics.emptyNodeWidth + TBSGraphics.immortalNodeLabelWidth)) / 2;
		TBSGraphics.emptyNodeUpperY = currentY + ((TBSGraphics.organismNodeHeight - TBSGraphics.emptyNodeHeight)/2);

		immortalEmptyNode = new EmptyNode(getSerial());
		addElement(immortalEmptyNode);
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

	public void addToTree(Node n)
	{
		Node newNode;
		if (n.equals(immortalEmptyNode)) {
			newNode = new EmptyNode(getSerial(), n.getAnchorPoint());
			modelElements.add(newNode);
			n.setAnchorPoint(new Point(TBSGraphics.emptyNodeLeftX, TBSGraphics.emptyNodeUpperY));
			buttonStates.put(TBSButtonType.LABEL, true);
		} else {
			n.setInTree(true);
			newNode = n;
		}
		if(history.peek() instanceof Drag)
			removeActionFromHistory();
		try{
			addActionToHistory(new Add((Node) newNode.clone()));
		}catch(CloneNotSupportedException c){
			System.out.println("Unable to add action to history.");
		}
		buttonStates.put(TBSButtonType.DELETE, true);
		if(inTreeElements().size() > 1)
			buttonStates.put(TBSButtonType.LINK, true);
		buttonStates.put(TBSButtonType.CLEAR, true);
	}

	public void addConnection(Node from, Node to){
		addConnection(from, to, -1);
	}

	public void addConnection(Node from, Node to, int id)
	{
		Connection newConn = new Connection(id == -1 ? getSerial() : id, from, to);
		if(id == -1)
			modelElements.add(newConn);
		else{
			if(id < modelElements.size())
				modelElements.add(id, newConn);
			else{
				modelElements.add(newConn);
				Collections.sort(modelElements, TBSGraphics.elementIdComparator);
			}
		}
		from.addConnectionTo(to);
		to.addConnectionFrom(from);
		if(!controller.getButtonClicked().equals(TBSButtonType.UNDO)){
			try{
				addActionToHistory(new Link((Connection) newConn.clone()));
			}catch(CloneNotSupportedException c){
				System.out.println("Unable to add action to history.");
			}
		}
		buttonStates.put(TBSButtonType.UNLINK, true);
	}

	public Properties getProperties(PropertyType pt) {
		return propertiesMap.get(pt);
	}

	public List<Connection> getConnectionsByNode(Node n){
		Unlink unlink = new Unlink();
		List<Connection> connections = new LinkedList<Connection>();
		Connection c;


		for (ModelElement me: modelElements)
		{
			if(me instanceof Connection){
				c = (Connection) me;
				if(c.hasNode(n)){
					connections.add(c);
					try{
						unlink.addConnection((Connection) c.clone());
					}catch(CloneNotSupportedException e){
						System.out.println("Unable to create connection clone.");
					}
				}
			}
		}
		if(controller.getButtonClicked().equals(TBSButtonType.UNLINK)){
			addActionToHistory(unlink);
		}
		return connections;
	}

	/**
	 * Unlink had to live in Model when connections were
	 * one-way. Now, this simply calls the Node-based two-way unlink.
	 */
	public void unlink(Node n)
	{
		List<Connection> connections = getConnectionsByNode(n);
		if(!history.isEmpty()){
			Command comm = history.peek();
			if(comm instanceof Delete)
				((Delete) comm).setElementConnections(connections);
		}
		for(Connection c : connections){
			c.getFrom().getConnectedTo().remove(c.getTo());
			c.getTo().getConnectedFrom().remove(c.getFrom());
			modelElements.remove(c);
		}
				updateButtonStatesAfterRemove();
	
	}

	public void removeFromTree(ModelElement m){
		if(m == null)
			return;
		if(m.equals(immortalEmptyNode)){
			immortalEmptyNode.setAnchorPoint(new Point(TBSGraphics.emptyNodeLeftX,
					TBSGraphics.emptyNodeUpperY));
			return;
		}
		if(m instanceof Node){
			Node n = (Node) m;
			Command c = null;
			if(!history.isEmpty())
				c = history.peek();
			try{
				if(controller.getButtonClicked().equals(TBSButtonType.DELETE)){
					if(c != null && c instanceof Drag){
						Node copy = (Node) n.clone();
						copy.setAnchorPoint(((Drag) c).getPointBefore());
						removeActionFromHistory();
						System.out.println("Invalid drag move removed from history.");
						addActionToHistory(new Delete(copy));
					}else
						addActionToHistory(new Delete((Node) n.clone()));
				}
			}catch(CloneNotSupportedException e){
				System.out.println("Unable to add action to history.");
			}
			unlink(n);
			if(n instanceof OrganismNode){
				n.setInTree(false);
				((OrganismNode) n).resetPosition();
				updateButtonStatesAfterRemove();
				return;
			}
		}else{
			Connection c = (Connection) m;
			c.getFrom().getConnectedTo().remove(c.getTo());
			c.getTo().getConnectedFrom().remove(c.getFrom());
			if(controller.getButtonClicked().equals(TBSButtonType.DELETE)){
				try{
					if(selectedTwoWay != null){
						Command command = history.peek();
						if(command instanceof Delete && ((Delete) command).getTwoWayConnection() != null)
							((Delete) command).addConnection((Connection) c.clone());
						else{
							addActionToHistory(new Delete());
							((Delete) history.peek()).addConnection((Connection) c.clone());
						}
					}else{
						addActionToHistory(new Delete((Connection) c.clone()));
					}
				}catch(CloneNotSupportedException e){
					System.out.println("Unable to add action to history.");
				}
			}
		}
		modelElements.remove(m);
		updateButtonStatesAfterRemove();
	}

	public void updateButtonStatesAfterRemove(){
		if(!hasConnections())
			buttonStates.put(TBSButtonType.UNLINK, false);
		List<Node> inTree = inTreeElements();
		if(inTree.size() == 0){
			buttonStates.put(TBSButtonType.LINK, false);
			buttonStates.put(TBSButtonType.DELETE, false);
			buttonStates.put(TBSButtonType.LABEL, false);
			buttonStates.put(TBSButtonType.CLEAR, false);
		}else if(inTree.size() < 2)
			buttonStates.put(TBSButtonType.LINK, false);
		else if(!hasEmptyNodes())
			buttonStates.put(TBSButtonType.LABEL, false);
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
			MESerialNumber = savedTree.size();
			System.out.println("loadTree: end");
		}catch(NumberFormatException e){
			System.out.println("There was an error parsing your saved tree. Your tree has been reset.");
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
	public void loadEmptyNode(String[] data, List<ModelElement> tempTree) throws NumberFormatException
	{
		String name = data[2];
		int id=0,x=0,y=0;
		boolean inTree = Boolean.parseBoolean(data[5]);
		if(inTree){
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
			System.out.println(new StringBuffer("StudentModel:loadConnection:Error parsing connection data (id:")
			.append(data[1]).append(",from id:").append(data[2]).append("to id:").append(data[3]).append(")").toString());
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

	public void viewOpenResponse(OpenQuestionButtonType currentQuestion) {
		if(currentQuestion.isRadio()){
			radioQuestionPrompt.setCurrentQuestion(currentQuestion);
			radioQuestionPrompt.setFinished(false);
			this.prompt = radioQuestionPrompt;
		}else{
			writtenQuestionPrompt.setCurrentQuestion(currentQuestion);
			writtenQuestionPrompt.setFinished(false);
			this.prompt = writtenQuestionPrompt;
		}
		view.refreshGraphics();
	}

	public void viewPrompt(Prompt prompt){
		this.prompt = prompt;
		view.refreshGraphics();
	}

	public void helpUser() {
		helpPrompt.setFinished(false);
		this.prompt = helpPrompt;
		view.refreshGraphics();
	}

	public Student getStudent(){
		return student;
	}


	public Map<TBSButtonType, Boolean> getButtonStates() {
		return buttonStates;
	}

	public Boolean isButtonActive(TBSButtonType button){
		return buttonStates.get(button);
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

	public StudentControllerTest getStudentControllerTest() {
		return sct;
	}

	public void setStudentControllerTest(StudentControllerTest sct) {
		this.sct = sct;
	}

	public List<String> incompletedItems(){
		List<String> incompletedItems = new LinkedList<String>();
		if(inTreeElements().isEmpty())
			incompletedItems.add("the tree");
		for(OpenQuestionButtonType q : OpenQuestionButtonType.values()){
			if(!student.getResponse(q).isCompleted())
				incompletedItems.add(q.getAdminText());
		}
		return incompletedItems;
	}

	public String unusedOrganisms(){
		StringBuffer unusedString = new StringBuffer();
		StringBuffer unusedStartString = new StringBuffer();
		for(int i=0;i<TBSGraphics.numOfOrganisms;i++){
			OrganismNode o = (OrganismNode) modelElements.get(i);
			if(!o.isInTree())
				unusedString.append("\t").append(o.getName()).append("\n");
		}
		if(unusedString.length() > 0)
			return unusedStartString.append(unusedString).append("\n").toString();
		return "";
	}

	public String surveyStatus(){
		StringBuffer statusString = new StringBuffer("");
		List<String> incompletedItems = incompletedItems();
		if(incompletedItems.isEmpty()){
			return "";
		}else{
			if(incompletedItems.size() == 1){
				statusString.append("Currently you still need to complete ");
				statusString.append(incompletedItems.remove(0)).append(". ");
			}else if(incompletedItems.size() <= OpenQuestionButtonType.values().length+1){
				statusString.append("Currently you still need to complete ");
				statusString.append(incompletedItems.remove(0));
				String statusEnd = incompletedItems.remove(incompletedItems.size()-1);
				for(String s : incompletedItems)
					statusString.append(", ").append(s);
				statusString.append(" & " + statusEnd + ". ");
			}
		}
		return statusString.toString();
	}

}