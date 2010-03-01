//TBS Version 0.4
//TBSView: one logic for converting Model to a visual representation

package tbs.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Properties;

import javax.swing.JScrollBar;

import tbs.TBSGraphics;
import tbs.TBSUtils;
import tbs.graphanalysis.ConvexHull;
import tbs.model.AdminModel;
import tbs.model.Connection;
import tbs.model.EmptyNode;
import tbs.model.ModelElement;
import tbs.model.Node;
import tbs.model.OrganismNode;
import tbs.model.admin.Student;
import tbs.properties.PropertyLoader;

/**
 * TBSView contains the logic for rendering the information contained in
 * the data model.
 **/
public class AdminView extends TBSView {

	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0xBB7D0BF0A83E3AF6L; 

	private AdminModel model;
  
	private boolean hasStudentScroll = false;
	private JScrollBar studentBar;
	private int studentYOffset = 0;
	
	public AdminView(Graphics2D g2, AdminModel m) {
		super(true, m);
		model = m;
		int studentBarMax = TBSGraphics.studentNodeHeight * (model.getStudents().size()-1);
		studentBarMax += ((model.getStudents().size()-1) * TBSGraphics.ySpacing);
		if(studentBarMax > model.getApplet().getHeight()){
			studentBar = new JScrollBar(JScrollBar.VERTICAL, 0, model.getApplet().getHeight(), 0, studentBarMax);
			studentBar.setBlockIncrement(TBSGraphics.studentNodeHeight + TBSGraphics.ySpacing);
			add(studentBar, BorderLayout.WEST);
			hasStudentScroll = true;
		}else{
			studentBar = new JScrollBar();
		}
		positionButtons(g2);
	}

	public boolean hasStudentScroll() {
		return hasStudentScroll;
	}

	public JScrollBar getStudentBar() {
		return studentBar;
	}

	public int getStudentYOffset() {
		return studentYOffset;
	}

	// sets the start of viewable tree area
	public void setStudentYOffset(int yo) {
		studentYOffset = yo;
	}

	/**
	 * Displays the button bar.
	 */
	public void renderButtons(Graphics2D g2)
	{
		renderGroupSelection(g2);
		TBSButtonType buttonClicked = model.getController().getButtonClicked();
		if(buttonClicked == null || model.getPrompt() == null)
			buttonClicked = TBSButtonType.TREE;
		int characterWidth = TBSGraphics.maxStudentNameWidth + TBSGraphics.checkWidth + TBSGraphics.arrowWidth;
		int studentWidth = characterWidth + getVerticalBar().getWidth() + (hasStudentScroll ? studentBar.getWidth() : 0);
		
		TBSGraphics.questionButtonsStart = (model.getApplet().getWidth() - studentWidth)/2 + (studentWidth-getVerticalBar().getWidth())
			- ((TBSGraphics.buttonsWidth*getButtons().size())/2);
		Rectangle buttonRect = new Rectangle(TBSGraphics.questionButtonsStart,0,TBSGraphics.buttonsWidth, TBSGraphics.buttonsHeight);
		int upperY = TBSGraphics.buttonsHeight - TBSGraphics.padding.height;
		for(TBSButtonType b: getButtons()) {
			if(b.equals(buttonClicked))
				TBSGraphics.renderButtonBackground(g2, buttonRect, true);
			else
				TBSGraphics.renderButtonBackground(g2, buttonRect, false);
			g2.setColor(Color.gray);
			g2.draw(buttonRect);
			TBSGraphics.drawCenteredString(g2, b.toString(),
					buttonRect.x, upperY, buttonRect.width, 0);
			buttonRect.setLocation(buttonRect.x + TBSGraphics.buttonsWidth, buttonRect.y);
		}

		//Print Button
		buttonRect = new Rectangle(model.getApplet().getWidth()-(TBSGraphics.buttonsWidth/2 + getVerticalBar().getWidth()),
				0,TBSGraphics.buttonsWidth/2, TBSGraphics.buttonsHeight);
		TBSGraphics.renderButtonBackground(g2, buttonRect, false);
		g2.setColor(Color.gray);
		g2.draw(buttonRect);
		TBSGraphics.drawCenteredString(g2, "Print",
				buttonRect.x, upperY, buttonRect.width, 0);
		
		//Show All Tooltips Button
		buttonRect = new Rectangle(buttonRect.x - TBSGraphics.namesButtonWidth,
				0,TBSGraphics.namesButtonWidth, TBSGraphics.buttonsHeight);
		TBSGraphics.renderButtonBackground(g2, buttonRect, false);
		g2.setColor(Color.gray);
		g2.draw(buttonRect);
		TBSGraphics.drawCenteredString(g2, "Names" + (getDisplayAllTooltips() ? " \u2713" : ""),
				buttonRect.x, upperY, buttonRect.width, 0);
	}
	
	public void renderElements(Graphics2D g2) {
		/*
		 * Uncomment this line of code to start logging of 
		 * model integrity
		 * model.checkElementsIntegrity();
		 */
		int maxPosition = 0;
		for(ModelElement m : model.inTreeElements()){
			if(m instanceof Node){
				if(((Node) m).getRectangle().getMaxX() > maxPosition)
					maxPosition = (int) ((Node)m).getRectangle().getMaxX();
				if(m instanceof OrganismNode)
						renderOrganismNode(g2, (OrganismNode) m);
				else
						renderEmptyNode(g2, (EmptyNode) m);
			}else
				renderConnection(g2, TBSUtils.getConnectionBounds(((Connection) m).getFrom(), ((Connection) m).getTo()), TBSGraphics.connectionColor);
		}
		if(maxPosition > (model.getApplet().getWidth() - getVerticalBar().getWidth())){
			getHorizontalBar().setVisibleAmount(model.getApplet().getWidth() - getVerticalBar().getWidth());
			getHorizontalBar().setMaximum(maxPosition);
			getHorizontalBar().setVisible(true);
		}else
			getHorizontalBar().setVisible(false);
		renderTooltip(g2);
	}

	public void renderStudents(Graphics2D g2){
		String selectedStudentName = model.getStudent().getName();
		int x,y,width;
		int characterWidth = TBSGraphics.maxStudentNameWidth + TBSGraphics.checkWidth + TBSGraphics.arrowWidth;
		width = TBSGraphics.maxStudentNameWidth - TBSGraphics.padding.width;
		for(Student student : model.getStudents()){
			if(student.getName().equals(selectedStudentName))
				g2.setColor(TBSGraphics.selectedStudentColor);
			else
				g2.setColor(Color.WHITE);
			x = student.getAnchorPoint().x + (hasStudentScroll ? studentBar.getWidth() : 0);
			y = student.getAnchorPoint().y - studentYOffset;
			g2.fillRect(x, y,
					characterWidth, TBSGraphics.studentNodeHeight);
			String studentIndicators = "";
			int indicatorsWidth = TBSGraphics.arrowWidth + TBSGraphics.checkWidth;
			if(student.hasArrows())
				studentIndicators += " \u2192";
			String lastUpdate = student.getLastUpdate();
			if(!TBSUtils.isStringEmpty(lastUpdate))
				studentIndicators += " \u2713";
			if(studentIndicators.length() > 0)
				TBSGraphics.drawCenteredString(g2, studentIndicators,
						x + width, y, indicatorsWidth + TBSGraphics.padding.width,
						TBSGraphics.studentNodeHeight,
						Color.BLACK);
			y += TBSGraphics.padding.width;
			for(String nameString : student.getNodeName()){
				TBSGraphics.drawCenteredString(g2, nameString,
						x + TBSGraphics.padding.width, y,width, TBSGraphics.textHeight,
						Color.BLACK);
				y += TBSGraphics.textHeight;
			}
		}
	}
	
	public void renderGroupSelection(Graphics2D g2){
		if(model.getPrompt() == null || model.getPrompt().renderElements()){
			Dimension buttonDimensions = TBSGraphics.get2DStringBounds(g2,model.getHulls());
			TBSGraphics.hullButtonWidth = buttonDimensions.width + TBSGraphics.padding.width * 2;
			TBSGraphics.hullButtonHeight = buttonDimensions.height + TBSGraphics.padding.height * 2;
			Rectangle hullButton = new Rectangle(model.getApplet().getWidth()-(TBSGraphics.hullButtonWidth + getVerticalBar().getWidth()),
					model.getApplet().getHeight() - (getHorizontalBar().getHeight() + TBSGraphics.hullButtonHeight),
					TBSGraphics.hullButtonWidth, TBSGraphics.hullButtonHeight);
			ConvexHull ch;
			for(int i=(model.getHulls().size()-1);i>=0;i--){
				ch=model.getHulls().get(i);
				//Render Button
				g2.setColor(TBSGraphics.hullColors[i]);
				g2.fill(hullButton);
				TBSGraphics.drawCenteredString(g2,
						ch.getHullName() + (ch.getDisplayHull() ? " \u2713" : ""),
						hullButton.x, hullButton.y, hullButton.width, hullButton.height);
				if(ch.getDisplayHull()){
					//Render Hull
					g2.setStroke(new BasicStroke(3));
					g2.setColor(TBSGraphics.hullColors[i]);
					for(ConvexHull.Line l : ch.getHull()){
						g2.draw(new Line2D.Double(l.getPoint1().x - getXOffset(),
								l.getPoint1().y - getYOffset(),
								l.getPoint2().x - getXOffset(),
								l.getPoint2().y - getYOffset()));
					}
					g2.setStroke(new BasicStroke());
				}
				g2.setColor(Color.BLACK);
				g2.draw(hullButton);
				hullButton.setLocation(hullButton.x, hullButton.y - TBSGraphics.hullButtonHeight);
			}
			TBSGraphics.drawCenteredString(g2,
					"Area",
					hullButton.x, hullButton.y, hullButton.width, hullButton.height, TBSGraphics.emptyNodeColor);
			hullButton.setLocation(hullButton.x, hullButton.y - TBSGraphics.hullButtonHeight);
			TBSGraphics.drawCenteredString(g2,
					"View Group",
					hullButton.x, hullButton.y, hullButton.width, hullButton.height, TBSGraphics.emptyNodeColor);
			hullButton.setLocation(hullButton.x, hullButton.y - TBSGraphics.hullButtonHeight);
		}
	}

	/**
	 * Draw the statusString. 	
	 */
	public void renderScreenString(Graphics2D g2) {
		TBSButtonType buttonClicked = model.getController().getButtonClicked();
		int yStep = TBSGraphics.buttonsHeight;

		if(buttonClicked == null || model.getPrompt() == null)
			buttonClicked = TBSButtonType.TREE;

		Properties adminProps = PropertyLoader.getProperties("admin");
		StringBuffer screenString = new StringBuffer(String.format(adminProps.getProperty(buttonClicked.name()), model.getStudent().getName()));
		if(TBSButtonType.TREE.equals(buttonClicked)){
			String lastUpdate = model.getStudent().getLastUpdate();
			if(lastUpdate != null && lastUpdate.length() > 0)
				screenString.append("(Last Update: ").append(lastUpdate).append(")");
		}
		int studentWidth = TBSGraphics.maxStudentNameWidth + TBSGraphics.checkWidth + TBSGraphics.arrowWidth + 
			+ getVerticalBar().getWidth() + (hasStudentScroll ? studentBar.getWidth() : 0);
		int width = model.getApplet().getWidth() - studentWidth;
		int x = (model.getApplet().getWidth() - studentWidth)/2 + (studentWidth-getVerticalBar().getWidth());

		List<String> lines = TBSGraphics.breakStringByLineWidth(g2, screenString.toString(), width);
		int yVal = model.getApplet().getHeight() - (TBSGraphics.buttonsHeight * (lines.size()+1));
		for(String line : lines) {
			Dimension d = TBSGraphics.getStringBounds(g2, line);
			TBSGraphics.drawCenteredString(g2, line, x-(d.width/2), yVal, d.width, yStep, TBSGraphics.emptyNodeColor);
			yVal += yStep;
		}
	}

	private void positionButtons(Graphics2D g2)
	{
		Dimension buttonDimensions = TBSGraphics.get2DStringBounds(g2,TBSButtonType.getButtons(true));
		TBSGraphics.buttonsWidth = buttonDimensions.width + TBSGraphics.padding.width * 2;
		TBSGraphics.buttonsHeight = buttonDimensions.height + TBSGraphics.padding.height * 2;
		
		buttonDimensions = TBSGraphics.getStringBounds(g2,"Names");
		TBSGraphics.namesButtonWidth = buttonDimensions.width + TBSGraphics.checkWidth + TBSGraphics.padding.width * 2;
	}
}
