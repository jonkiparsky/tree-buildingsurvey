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

import tbs.TBSGraphics;
import tbs.model.StudentModel;
import tbs.model.admin.Student;
import tbs.view.prompt.Prompt;
import tbs.view.prompt.student.WrittenQuestionPrompt;

/**
 * StudentView represents the model for a test subject, displaying controls for building and
 * manipulating a tree.
 **/
public class StudentView extends TBSView {

	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0xBB7D0BF0A83E3AF6L; 

	private StudentModel model;

	// This connection follows the mouse
	private Line2D connInProgress;
	private String screenString;
	
	public StudentView(Graphics2D g2, StudentModel m) {
		super(false, m);
		model = m;
		connInProgress = null;
		screenString = null; 
		positionButtons(g2);
		positionModelElements(g2);

	}

	/**
	 * Displays the button bar.
	 */
	public void renderButtons(Graphics2D g2)
	{
		TBSButtonType buttonClicked = model.getController().getButtonClicked();
		if(buttonClicked == null)
			buttonClicked = TBSButtonType.SELECT;
		Rectangle buttonRect = new Rectangle(0,0,TBSGraphics.buttonsWidth, TBSGraphics.buttonsHeight);
		int upperY = TBSGraphics.buttonsHeight - TBSGraphics.padding.height;
		for(TBSButtonType b: getButtons()) {
			TBSGraphics.renderButtonBackground(g2, buttonRect, b.equals(buttonClicked));
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
		Student student = model.getStudent();
		String buttonString;
		TBSGraphics.renderButtonBackground(g2, buttonRect, (prompt != null) && prompt instanceof WrittenQuestionPrompt);
		g2.setColor(Color.gray);
		g2.draw(buttonRect);
		buttonString = "Questions";
		if(student.getResponse(OpenQuestionButtonType.ONE).isCompleted() && 
				student.getResponse(OpenQuestionButtonType.TWO).isCompleted())
			buttonString += " \u2713";
		TBSGraphics.drawCenteredString(g2, buttonString,
				buttonRect.x, upperY, buttonRect.width, 0);
		buttonRect.setLocation(buttonRect.x + TBSGraphics.questionButtonsWidth, buttonRect.y);

	}
	
	public void renderElements(Graphics2D g2) {
		renderUnselectedModelElements(g2);
		//Immortal Branch Node
		int stringAreaLeftX = TBSGraphics.emptyNodeLeftX + TBSGraphics.emptyNodeWidth + TBSGraphics.padding.width;
		TBSGraphics.drawCenteredString(g2, TBSGraphics.immortalNodeLabel,
				stringAreaLeftX, TBSGraphics.emptyNodeUpperY,
				TBSGraphics.immortalNodeLabelWidth, TBSGraphics.emptyNodeHeight,
				TBSGraphics.emptyNodeColor);
		g2.fill(new Rectangle(TBSGraphics.emptyNodeLeftX, TBSGraphics.emptyNodeUpperY, TBSGraphics.emptyNodeWidth, TBSGraphics.emptyNodeHeight));
		renderSelectedModelElements(g2);
		if(connInProgress != null)
			renderConnection(g2, connInProgress, TBSGraphics.connectionColor);
		renderTooltip(g2);
		if(model.getStudentControllerTest() != null)
			model.getStudentControllerTest().renderVirtualCursor(g2);
	}

	public void renderStudents(Graphics2D g2) {}

	/**
	 * Establish this connection as the one to update and set. 	
	 */
	public void setConnInProgress(Line2D conn) {
		connInProgress = conn;
	}

	/**
	 * Set status string.
	 */
	public void setScreenString(String s) {
		screenString = s;
	}
	
	public String getScreenString() {
		return screenString;
	}

	/**
	 * Draw the statusString. 	
	 */
	public void renderScreenString(Graphics2D g2) {
		if(screenString != null && screenString.length() > 0) {
			int xVal = TBSGraphics.LINE_OF_DEATH + 20;
			int yVal = TBSGraphics.buttonsHeight;
      int width = model.getApplet().getWidth() - (xVal + TBSGraphics.buttonsWidth);
			List<String> lines = TBSGraphics.breakStringByLineWidth(g2, screenString, width);
			for(String line : lines) {
				TBSGraphics.drawCenteredString(g2, line, xVal, yVal, 0, TBSGraphics.buttonsHeight, Color.CYAN);
				yVal += TBSGraphics.buttonsHeight;
			}
		}
	}

	private void positionModelElements(Graphics2D g2) {
		TBSGraphics.organismNodeWidth = TBSGraphics.maxOrganismStringWidth + TBSGraphics.maxOrganismImageWidth + TBSGraphics.padding.height * 2;
		if(TBSGraphics.maxOrganismStringHeight  > TBSGraphics.maxOrganismImageHeight)
			TBSGraphics.organismNodeHeight = TBSGraphics.maxOrganismStringHeight;
		else
			TBSGraphics.organismNodeHeight = TBSGraphics.maxOrganismImageHeight;
		
		//create left-side empty node
		TBSGraphics.immortalNodeLabelWidth = (int) TBSGraphics.getStringBounds(g2, TBSGraphics.immortalNodeLabel).getWidth();
		TBSGraphics.emptyNodeLeftX = (TBSGraphics.organismNodeWidth - (TBSGraphics.emptyNodeWidth + TBSGraphics.immortalNodeLabelWidth)) / 2;
		int emptyY = (TBSGraphics.buttonsHeight + 10) + (TBSGraphics.numOfOrganisms * (TBSGraphics.organismNodeHeight + TBSGraphics.ySpacing));
		TBSGraphics.emptyNodeUpperY = emptyY + ((TBSGraphics.organismNodeHeight - TBSGraphics.emptyNodeHeight)/2);
	}

	private void positionButtons(Graphics2D g2)
	{
		Dimension buttonDimensions = TBSGraphics.get2DStringBounds(g2,TBSButtonType.getButtons(false));
		TBSGraphics.buttonsWidth = buttonDimensions.width + TBSGraphics.padding.width* 2;
		TBSGraphics.buttonsHeight = buttonDimensions.height + TBSGraphics.padding.height * 2;

		buttonDimensions = TBSGraphics.getStringBounds(g2,"Questions");
		TBSGraphics.questionButtonsWidth = buttonDimensions.width + TBSGraphics.checkWidth + TBSGraphics.padding.width * 2;
	}
}
