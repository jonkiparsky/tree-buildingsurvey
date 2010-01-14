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
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;

import javax.swing.JScrollBar;
import javax.swing.Timer;

import tbs.TBSGraphics;
import tbs.TBSUtils;
import tbs.controller.StudentController;
import tbs.model.Connection;
import tbs.model.EmptyNode;
import tbs.model.ModelElement;
import tbs.model.Node;
import tbs.model.OrganismNode;
import tbs.model.StudentModel;
import tbs.view.prompt.OpenQuestionPrompt;
import tbs.view.prompt.Prompt;
import tbs.view.prompt.SplashPrompt;

/**
* TBSView contains the logic for rendering the information contained in
* the data model.
**/
public class StudentView extends TBSView implements Printable {

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
	
	private StudentModel model;
	
	public StudentView(StudentModel m) {
        model = m;
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
		for(TBSButtonType b: model.getButtons()) {
			if(b.equals(buttonClicked) ||
					(!buttonClicked.isMode() && b.equals(TBSButtonType.SELECT)))
				TBSGraphics.renderButtonBackground(g2, buttonRect, true);
			else
				TBSGraphics.renderButtonBackground(g2, buttonRect, false);
			g2.setColor(Color.gray);
			g2.draw(buttonRect);
			if(!model.isButtonActive(b)){
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke(3));
				g2.draw(new Line2D.Double(buttonRect.x, buttonRect.y,
						buttonRect.x + buttonRect.width, buttonRect.y + buttonRect.height));
				g2.draw(new Line2D.Double(buttonRect.x, buttonRect.y+buttonRect.height,
						buttonRect.x + buttonRect.width, buttonRect.y));
				g2.setStroke(new BasicStroke());
			}
			TBSGraphics.drawCenteredString(g2, b.toString(),
					buttonRect.x, upperY, buttonRect.width, 0);
			buttonRect.setLocation(buttonRect.x + TBSGraphics.buttonsWidth, buttonRect.y);
		}
		
		buttonRect.setLocation(buttonRect.x + TBSGraphics.spaceBeforeQuestionButtons, buttonRect.y);
		TBSGraphics.questionButtonsStart = buttonRect.x;
		buttonRect.setSize(new Dimension(TBSGraphics.questionButtonsWidth, buttonRect.height));
		
		Prompt prompt = model.getPrompt();
		for(OpenQuestionButtonType q: OpenQuestionButtonType.values()) {
			if((prompt != null) && (prompt instanceof OpenQuestionPrompt)
					&&  q.equals(((OpenQuestionPrompt)prompt).getCurrentQuestion()))
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
	public void renderModelElement(Graphics2D g2, ModelElement me) {
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
			if(en.isBeingLabeled())
				model.getTextEntryBox().renderTextEntryBox(g2, yOffset);
			else{
				if(me != model.getImmortalEmptyNode()){
					g2.fill(yAdjust);
					TBSGraphics.drawCenteredString(g2, name, en.getX(),
							en.getY() - yOffset, en.getWidth(), en.getHeight());
				}else{
					int stringAreaLeftX = TBSGraphics.emptyNodeLeftX + TBSGraphics.emptyNodeWidth + TBSGraphics.paddingWidth;
					TBSGraphics.drawCenteredString(g2, TBSGraphics.immortalNodeLabel,
							stringAreaLeftX, TBSGraphics.emptyNodeUpperY,
							TBSGraphics.immortalNodeLabelWidth, TBSGraphics.emptyNodeHeight,
							TBSGraphics.emptyNodeColor);
					g2.fill(en.getRectangle());
				}
			}
			
		}else if(me instanceof Connection){
			Connection c = (Connection) me;
			Line2D conn = TBSUtils.getConnectionBounds(c.getFrom() , 
					c.getTo());
			conn = scrollAdjust(conn);
			g2.setColor(TBSGraphics.connectionColor);
			g2.setStroke(new BasicStroke(3));
			g2.draw(conn);
			if(model.hasArrows()){
				g2.draw(getArrowHead(conn, 0.75 * Math.PI));
				g2.draw(getArrowHead(conn, 1.25 * Math.PI));
			}
		}
	}
	
	public void renderOrganismNode(Graphics2D g2, OrganismNode on) {
		Color stringColor = on.isInTree() ? TBSGraphics.organismBoxColor : TBSGraphics.organismStringColor;
		int stringWidth = 0;
		int imageWidth = 0;
		int imageStartX = 0;
		stringWidth = (int) TBSGraphics.getStringBounds(g2, on.getName()).getWidth();
		imageWidth = on.getImage().getWidth();
		// center image and text
		int imageXOffset = (TBSGraphics.organismNodeWidth - imageWidth - stringWidth) / 2;
		imageStartX = on.getDefaultPoint().x + imageXOffset;
		int stringAreaLeftX = imageStartX + imageWidth + TBSGraphics.paddingWidth;
		int stringAreaWidth = stringWidth;
		int stringAreaUpperY = on.getDefaultPoint().y;
		int stringAreaHeight = TBSGraphics.organismNodeHeight;
		g2.setColor(on.isInTree() ? TBSGraphics.organismStringColor : TBSGraphics.organismBoxColor);
		if(!on.isInTree())
			g2.fillRect(on.getDefaultPoint().x, on.getDefaultPoint().y, on.getDefaultWidth(), on.getDefaultHeight());
		TBSGraphics.drawCenteredString(g2, on.getName(), stringAreaLeftX, stringAreaUpperY, stringAreaWidth, stringAreaHeight, stringColor);
		g2.drawImage(on.getImage(), imageStartX, on.getDefaultPoint().y, null);
		if(on.isInTree())
			g2.drawImage(on.getImage(), on.getX(), on.getY() - yOffset, null);
		else {
			// organism is being dragged for possible addition to tree
			if(on.getX() > 0) {
				g2.drawImage(on.getImage(), on.getX(), on.getY(), null);
				return;
			}
		}
	}
	
	public void renderSelectedModelElement(Graphics2D g2, ModelElement me){
		if(me == null)
			return;
		if(me instanceof Node){
			if(((Node) me).isBeingLabeled())
				return; // do not draw green box around node being labeled
			Node n = (Node) me;
			double y = n.getY() - 1.5;
			if(n.isInTree()) y -= yOffset;
			g2.setColor(TBSGraphics.selectedNodeBorderColor);
			g2.setStroke(new BasicStroke(TBSGraphics.selectedNodeBorderThickness));
			g2.draw(new Rectangle2D.Double(n.getX()-1.5,
					y,
					n.getWidth() + TBSGraphics.selectedNodeBorderThickness,
					n.getHeight() + TBSGraphics.selectedNodeBorderThickness));
		}else{
			Connection c = (Connection) me;
			Line2D conn = TBSUtils.getConnectionBounds(c.getFrom() , 
					c.getTo());
			conn = scrollAdjust(conn);
			g2.setColor(TBSGraphics.connectionSelectedColor);
			g2.setStroke(new BasicStroke(3));
			g2.draw(conn);
			if(model.hasArrows()){
				g2.draw(getArrowHead(conn, 0.75 * Math.PI));
				g2.draw(getArrowHead(conn, 1.25 * Math.PI));
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
		int width = model.getApplet().getWidth() - (xVal + TBSGraphics.buttonsWidth);
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
        g2.setFont(TBSGraphics.tooltipFont);
        xVal -= TBSGraphics.getStringBounds(g2, tooltipString).width/2;
		TBSGraphics.drawCenteredString(g2, tooltipString, xVal, yVal, 0,
				TBSGraphics.buttonsHeight, Color.CYAN, TBSGraphics.tooltipFont);
		g2.setFont(TBSGraphics.font);
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
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.setFont(TBSGraphics.font);
		Prompt prompt = model.getPrompt();
		g2.setColor(Color.black);
		g2.fillRect(0, 0, model.getApplet().getWidth(), model.getApplet().getHeight());
		refreshGraphics();
		if(prompt == null){
			for(ModelElement m : model.getElements())
				renderModelElement(g2, m);
			ModelElement selected = model.getSelectedModelElement();
			List<ModelElement> selectedTwoWay = model.getSelectedTwoWay();
			if(selected == null){
				Node draggedNode = ((StudentController) model.getController()).getDraggedNode();
				if(draggedNode != null)
					selected = draggedNode;
			}
			if(selectedTwoWay != null){
				for(ModelElement m : selectedTwoWay)
					renderSelectedModelElement(g2,m);
			}else if(selected != null)
				renderSelectedModelElement(g2,selected);
			if(connInProgress != null){
				g2.setColor(TBSGraphics.connectionColor);
				g2.setStroke(new BasicStroke(3));
				g2.draw(scrollAdjust(connInProgress));
				if(model.hasArrows()){
					g2.draw(getArrowHead(scrollAdjust(connInProgress), 0.75 * Math.PI));
					g2.draw(getArrowHead(scrollAdjust(connInProgress), 1.25 * Math.PI));
				}
			}
			g2.setStroke(new BasicStroke());
		}else
			prompt.paintComponent(g2);
		if(!(prompt instanceof SplashPrompt)){
			renderButtons(g2);
			renderScreenString(g2);
		}
		setCursor(cursor);
		if(tooltipString != null){
			renderTooltip(g2);
			if(!timer.isRunning())
				timer.start();
		}
	}	
	
	//@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex > 0) {
			return(NO_SUCH_PAGE);
		} else {
			// make pic
			BufferedImage fullSizeImage = new BufferedImage(
					getWidth(), 
					getHeight(), 
					BufferedImage.TYPE_INT_RGB);
			paint(fullSizeImage.getGraphics());
			
			// scale to fit
			double wRatio = getWidth()/pageFormat.getImageableWidth();
			double hRatio = getHeight()/pageFormat.getImageableHeight();
			int actualWidth;
			int actualHeight;
			if (wRatio > hRatio) {
				actualWidth = (int)(getWidth()/wRatio);
				actualHeight = (int)(getHeight()/wRatio);
			} else {
				actualWidth = (int)(getWidth()/hRatio);
				actualHeight = (int)(getHeight()/hRatio);
			}

			// print it
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g2.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(
					RenderingHints.KEY_FRACTIONALMETRICS, 
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2.drawImage(fullSizeImage, 
					(int)pageFormat.getImageableX(), 
					(int)pageFormat.getImageableY(), 
					actualWidth, 
					actualHeight, 
					null);
			fullSizeImage = null;
			return(PAGE_EXISTS);
		}
	}
}
