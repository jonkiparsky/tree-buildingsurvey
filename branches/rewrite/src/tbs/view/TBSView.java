//TBS Version 0.4
//TBSView: one logic for converting Model to a visual representation

package tbs.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.Timer;

import tbs.TBSGraphics;
import tbs.TBSPrompt;
import tbs.TBSUtils;
import tbs.model.Connection;
import tbs.model.EmptyNode;
import tbs.model.ModelElement;
import tbs.model.Node;
import tbs.model.OrganismNode;
import tbs.model.TBSModel;

/**
* TBSView contains the logic for rendering the information contained in
* the data model.
**/
public class TBSView extends JComponent {

	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0xBB7D0BF0A83E3AF6L; 
	
	// This connection follows the mouse
	private Line2D connInProgress;
	private String screenString;
	private JScrollBar verticalBar;
	private int yOffset = 0; // start of viewable tree area
	private Cursor cursor;
	private Boolean hasArrows;
	
	//Tooltip information
	private String tooltipString;
	private Point tooltipLocation;
	private Timer timer;
	private ActionListener hider = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			timer.stop();
			tooltipString = null;
		}
	};
	
	private TBSModel model;
	public TBSView(TBSModel m) {
        model = m;
        hasArrows = true;
        connInProgress = null;
    	screenString = null;
    	cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    	verticalBar = new JScrollBar(JScrollBar.VERTICAL, 0, 100, 0, 200);
		setLayout(new BorderLayout());
 		add(verticalBar, BorderLayout.EAST);
 		timer = new Timer(1000, hider);
	}
	
	public JScrollBar getVerticalBar() {
		return verticalBar;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	// sets the start of viewable tree area
	public void setYOffset(int yo) {
		yOffset = yo;
	}
	
	public void setAppletCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	
	public void updateTooltip(String name, Point location){
		tooltipString = name;
		tooltipLocation = location;
	}
	
	public boolean isTooltipRunning(){
		return timer.isRunning();
	}
	
	/**
	* Calls up a Swing-based string text entry box and returns the
	* submitted String. 
	* Avoiding Swing, so we're not using this. 
	*/
	public String promptUserForString(String message) {
		return (String) JOptionPane.showInputDialog(message);
	}
	
	/**
	* Calls up a Swing-based yes/no/cancel dialog box, returns the user's
	* selection. Avoiding this, since it's Swing. 
	*/
	public int promptUserForYesNoCancel(String message) {
		return JOptionPane.showConfirmDialog(null, message);
	}
	
	/**
	* Displays the button bar.
	*/
	public void renderButtons(Graphics g)
	{
		TBSButtonType buttonClicked = model.getController().getButtonClicked();
		if(buttonClicked == null)
			buttonClicked = TBSButtonType.SELECT;
		Graphics2D g2 = (Graphics2D) g;
		Rectangle buttonRect = new Rectangle(0,0,TBSGraphics.buttonsWidth, TBSGraphics.buttonsHeight);
		int upperY = TBSGraphics.buttonsHeight - TBSGraphics.buttonsYPadding;
		for(TBSButtonType b: TBSButtonType.values()) {
			if(b.equals(buttonClicked) ||
					(!buttonClicked.isMode() && b.equals(TBSButtonType.SELECT)))
				TBSGraphics.renderButtonBackground(g2, buttonRect, true);
			else
				TBSGraphics.renderButtonBackground(g2, buttonRect, false);
			g2.setColor(Color.gray);
			g2.draw(buttonRect);
			if(model.isButtonActive(b))
				TBSGraphics.drawCenteredString(g2, b.toString(),
						buttonRect.x, upperY, buttonRect.width, 0);
			else
				TBSGraphics.drawCenteredString(g2, b.toString(),
						buttonRect.x, upperY, buttonRect.width, 0, Color.GRAY);
			buttonRect.setLocation(buttonRect.x + TBSGraphics.buttonsWidth, buttonRect.y);
		}
		
		buttonRect.setLocation(buttonRect.x + TBSGraphics.spaceBeforeQuestionButtons, buttonRect.y);
		TBSGraphics.questionButtonsStart = buttonRect.x;
		buttonRect.setSize(new Dimension(TBSGraphics.questionButtonsWidth, buttonRect.height));
		
		TBSPrompt prompt = model.getPrompt();
		for(TBSQuestionButtonType q: TBSQuestionButtonType.values()) {
			if((prompt != null) &&  q.equals(prompt.getCurrentQuestion()))
				TBSGraphics.renderButtonBackground(g2, buttonRect, true);
			else
				TBSGraphics.renderButtonBackground(g2, buttonRect, false);
			g2.setColor(Color.gray);
			g2.draw(buttonRect);
			TBSGraphics.drawCenteredString(g2, q.toString(),
					buttonRect.x, upperY, buttonRect.width, 0);
			buttonRect.setLocation(buttonRect.x + TBSGraphics.questionButtonsWidth, buttonRect.y);
		}
	}

	/**
	* draws a modelElement
	*/
	public void renderModelElement(Graphics g, ModelElement me) {
		Graphics2D g2 = (Graphics2D) g;
		if(me instanceof OrganismNode) 
			renderOrganismNode(g2, (OrganismNode) me);
		else if (me instanceof EmptyNode)
		{
			EmptyNode en = (EmptyNode) me;
			String name = en.getName();
			if(name == null)
				name = "";
			// make empty nodes light purple (like Prof. White's node.gif)
			g2.setColor(TBSGraphics.emptyNodeColor);
			Rectangle yAdjust = en.getRectangle();
			yAdjust.setLocation(yAdjust.x, yAdjust.y - yOffset);
			if(me != model.getImmortalEmptyNode()) 
				g2.fill(yAdjust);
			else{
				int stringAreaLeftX = TBSGraphics.emptyNodeLeftX + TBSGraphics.emptyNodeWidth + TBSGraphics.paddingWidth;
				TBSGraphics.drawCenteredString(g2, TBSGraphics.immortalNodeLabel,
						stringAreaLeftX, TBSGraphics.emptyNodeUpperY,
						TBSGraphics.immortalNodeLabelWidth, TBSGraphics.emptyNodeHeight,
						TBSGraphics.emptyNodeColor);
				g2.fill(en.getRectangle());
			}
			TBSGraphics.drawCenteredString(g2, name, en.getX(),
					en.getY() - yOffset, en.getWidth(), en.getHeight());
		}else if(me instanceof Connection){
			Connection c = (Connection) me;
			Line2D conn = TBSUtils.getConnectionBounds(c.getFrom() , 
					c.getTo());
			conn = scrollAdjust(conn);
			g2.setColor(TBSGraphics.connectionColor);
			g2.setStroke(new BasicStroke(3));
			g2.draw(conn);
			if(hasArrows){
				g2.draw(getArrowHead(conn, 0.75 * Math.PI));
				g2.draw(getArrowHead(conn, 1.25 * Math.PI));
			}
		}
	}
	
	public void renderOrganismNode(Graphics2D g2, OrganismNode on) {
		Color stringColor = TBSGraphics.organismStringColor;
		int stringWidth = 0;
		int imageWidth = 0;
		int imageStartX = 0;
		stringWidth = (int) TBSGraphics.getStringBounds(g2, on.getName()).getWidth();
		imageWidth = on.getImage().getWidth();
		// center image and text
		int imageXOffset = (TBSGraphics.organismNodeWidth - imageWidth - stringWidth) / 2;
		imageStartX = on.getDefaultPoint().x + imageXOffset;
		if(on.isInTree()) {
			stringColor = TBSGraphics.organismBoxColor;
			g2.drawImage(on.getImage(), on.getX(), on.getY() - yOffset, null);
		} else {
			// organism is being dragged for possible addition to tree
			if(on.getX() > 0) {
				g2.drawImage(on.getImage(), on.getX(), on.getY(), null);
				return;
			}
		}
		int stringAreaLeftX = imageStartX + imageWidth + TBSGraphics.paddingWidth;
		int stringAreaWidth = stringWidth;
		int stringAreaUpperY = on.getDefaultPoint().y;
		int stringAreaHeight = TBSGraphics.organismNodeHeight;
		g2.setColor(on.isInTree() ? TBSGraphics.organismStringColor : TBSGraphics.organismBoxColor);
		g2.fillRect(on.getDefaultPoint().x, on.getDefaultPoint().y, on.getDefaultWidth(), on.getDefaultHeight());
		TBSGraphics.drawCenteredString(g2, on.getName(), stringAreaLeftX, stringAreaUpperY, stringAreaWidth, stringAreaHeight, stringColor);
		g2.drawImage(on.getImage(), imageStartX, on.getDefaultPoint().y, null);
	}
	
	public void renderSelectedModelElement(Graphics g, ModelElement me){
		if(me == null)
			return;
		Graphics2D selectedGraphics = (Graphics2D) g;
		if(me instanceof Node){
			Node n = (Node) me;
			double y = n.getY() - 1.5;
			if(n.isInTree()) y -= yOffset;
			selectedGraphics.setColor(TBSGraphics.selectedNodeBorderColor);
			selectedGraphics.setStroke(new BasicStroke(TBSGraphics.selectedNodeBorderThickness));
			selectedGraphics.draw(new Rectangle2D.Double(n.getX()-1.5,
					y,
					n.getWidth() + TBSGraphics.selectedNodeBorderThickness,
					n.getHeight() + TBSGraphics.selectedNodeBorderThickness));
		}else{
			Connection c = (Connection) me;
			Line2D conn = TBSUtils.getConnectionBounds(c.getFrom() , 
					c.getTo());
			conn = scrollAdjust(conn);
			selectedGraphics.setColor(TBSGraphics.connectionSelectedColor);
			selectedGraphics.setStroke(new BasicStroke(3));
			selectedGraphics.draw(conn);
			if(hasArrows){
				selectedGraphics.draw(getArrowHead(conn, 0.75 * Math.PI));
				selectedGraphics.draw(getArrowHead(conn, 1.25 * Math.PI));
			}
		}
			
	}
	
	/**
	* Establish this connection as the one to update and set. 	
	*/
	public void setConnInProgress(Line2D conn) {
		connInProgress = conn;
	}

	
	/**
	* Redraw the screen.
	*/
	public void refreshGraphics() {
		repaint();	
	}
	
	/**
	* Draw the arrowhead at the end of a connection.
	*/
	public Line2D getArrowHead(Line2D conn, double angle) {
		double dx = TBSUtils.dx(conn);
		double dy = TBSUtils.dy(conn);
		double dArrowX = Math.round(dx * Math.cos(angle) + dy * Math.sin(angle));
		double dArrowY = Math.round(dy * Math.cos(angle) - dx * Math.sin(angle));
		double arrowLength = Math.sqrt(dx * dx + dy * dy);
		dArrowX /= arrowLength * TBSGraphics.arrowLength;
		dArrowY /= arrowLength * TBSGraphics.arrowLength;
		int arrowX = (int) Math.round(dArrowX);
		int arrowY = (int) Math.round(dArrowY);
		return new Line2D.Double(
				conn.getP2().getX(),
				conn.getP2().getY(),
				conn.getP2().getX() + arrowX,
				conn.getP2().getY() + arrowY);
	}
	
	public Line2D scrollAdjust(Line2D l) {
		double y1 = l.getY1() - yOffset;
		double y2 = l.getY2() - yOffset;
		return new Line2D.Double(l.getX1(), y1, l.getX2(), y2);
	}

	/**
	* Set status string.
	*/
	public void setScreenString(String s) {
		screenString = s;
	}

	/**
	* Draw the statusString. 	
	*/
	public void renderScreenString(Graphics2D g2) {
        int xVal = TBSGraphics.LINE_OF_DEATH + 20;
        int yVal = TBSGraphics.buttonsHeight;
        int yStep = TBSGraphics.buttonsHeight;
		if(screenString == null) 
			return;
		int width = model.getApplet().getWidth() - xVal;
		List<String> lines = TBSGraphics.breakStringByLineWidth(g2, screenString, width);
		for(String line : lines) {
			TBSGraphics.drawCenteredString(g2, line, xVal, yVal, 0, yStep, Color.CYAN);
			yVal += yStep;
		}
	}
	
	public void renderTooltip(Graphics2D g2){
		int xVal = tooltipLocation.x;
        int yVal = tooltipLocation.y;
        yVal += yOffset;
        yVal -= TBSGraphics.organismNodeHeight;
        TBSGraphics.setFont(g2, TBSGraphics.tooltipFont);
        xVal -= TBSGraphics.getStringBounds(g2, tooltipString).width/2;
		TBSGraphics.drawCenteredString(g2, tooltipString, xVal, yVal, 0,
				TBSGraphics.buttonsHeight, Color.CYAN, TBSGraphics.tooltipFont);
		TBSGraphics.setFont(g2);
	}

	/**
	* How to paint the screen (using view's graphics)
	*/
	public void paintComponent() {
		paintComponent(getGraphics());
	}

	/**
	* How to paint the screen.
	*/
	// this is what the applet calls to refresh the screen
	public void paintComponent(Graphics g) {
		TBSPrompt prompt = model.getPrompt();
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.fillRect(0, 0, model.getApplet().getWidth(), model.getApplet().getHeight());
		refreshGraphics();
		if(prompt == null){
			for(ModelElement m : model.getElements())
				renderModelElement(g, m);
			ModelElement selected = model.getSelectedModelElement();
			List<ModelElement> selectedTwoWay = model.getSelectedTwoWay();
			if(selected == null){
				Node draggedNode = model.getController().getDraggedNode();
				if(draggedNode != null)
					selected = draggedNode;
			}
			if(selectedTwoWay != null){
				for(ModelElement m : selectedTwoWay)
					renderSelectedModelElement(g,m);
			}else if(selected != null)
				renderSelectedModelElement(g,selected);
			if(connInProgress != null){
				g2.setColor(TBSGraphics.connectionColor);
				g2.setStroke(new BasicStroke(3));
				g2.draw(scrollAdjust(connInProgress));
				if(hasArrows){
					g2.draw(getArrowHead(scrollAdjust(connInProgress), 0.75 * Math.PI));
					g2.draw(getArrowHead(scrollAdjust(connInProgress), 1.25 * Math.PI));
				}
			}
			g2.setStroke(new BasicStroke());
		}else
			prompt.paintComponent(g2);
		renderButtons(g2);
		renderScreenString(g2);
		setCursor(cursor);
		if(tooltipString != null){
			renderTooltip(g2);
			if(!timer.isRunning())
				timer.start();
		}
	}	
	
	public void setHasArrows(Boolean hasArrows) {
		this.hasArrows = hasArrows;
	}
}
