
package tbs.view.prompt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import tbs.TBSGraphics;
import tbs.model.TBSModel;
import tbs.view.OpenQuestionButtonType;
import tbs.view.prompt.buttons.OpenQuestionPromptButtonType;

public class OpenQuestionPrompt extends Prompt{
	
	//Information to be used by all prompt types
	TBSModel model;
	Graphics2D g2 = null;
	Properties questionProps;
	OpenQuestionButtonType currentQuestion;
	String question = null;
	String userInput = null;
	
	//Prompt sizing information
	List<String> lineBrokenQuestion;
	List<OpenQuestionPromptButtonType> buttons;
	int numLines = 8; // number of lines of text input
	Dimension padding = new Dimension(10,5);
	Dimension promptSize = new Dimension();
	Point anchorPoint = null;
	int questionStringY;
	Rectangle buttonsArea;
	int buttonHeight;
	int textHeight;
	
	//Question 3(Radio) Properties
	List<String> radioAnswers;
	int currentRadioQuestion;
	Rectangle radioQuestionSelection;
	
	// if buttons != null, value of button pressed is returned
	// if buttons == null, text input is assumed
	public OpenQuestionPrompt(TBSModel model, OpenQuestionButtonType currentQuestion) {
		super();
		this.model = model;
		this.currentQuestion = currentQuestion;
		questionProps = model.getQuestionProperties();
		this.question = questionProps.getProperty(currentQuestion.getQuestionKey());
		buttons = OpenQuestionPromptButtonType.getButtons(currentQuestion.isRadio());
		userInput = model.getQuestion(currentQuestion);
		if(currentQuestion.isRadio()){
			radioAnswers = new LinkedList<String>();
			currentRadioQuestion = 1;
			if(userInput == null || userInput == "")
				userInput = "0,0,0,0,0,0,0,0,0,0,0,0,0";
			for(String answer : userInput.split(","))
					radioAnswers.add(answer);
			int numRadios = Integer.parseInt(questionProps.getProperty("questionThree.numQuestions"));
			if(radioAnswers.size() < numRadios){
				for(int i=radioAnswers.size();i<=numRadios;i++)
					radioAnswers.add("0");
			}
		}
	}
	
	public String getUserInput() {
		if(currentQuestion.isRadio()){
			String radioAnswersToString = "";
			for(String answer : radioAnswers)
				radioAnswersToString += answer+",";
			return radioAnswersToString.substring(0, radioAnswersToString.length()-1);
		}
		return userInput;
	}
	
	public void mousePressed(MouseEvent e){
		calculateValues();
        if(buttonsArea.contains(e.getPoint())){
        	int index = (int) ((e.getX() - buttonsArea.getX()) * buttons.size()) / promptSize.width;
        	String buttonValue = buttons.get(index).getValue();
    		if("0".equals(buttonValue))
        		setFinished(true);
    		else{
    			if(currentQuestion.isRadio()){
    				radioAnswers.set(currentRadioQuestion-1, buttonValue);
    				if(currentRadioQuestion == radioAnswers.size())
    					buttons = OpenQuestionPromptButtonType.getButtons(false);
    				else
    					currentRadioQuestion++;
    			}
    		}
        }else{
        	if(currentQuestion.isRadio()){
        		if(radioQuestionSelection.contains(e.getPoint())){
        			if(currentRadioQuestion == radioAnswers.size())
    					buttons = OpenQuestionPromptButtonType.getButtons(true);
        			currentRadioQuestion = ((int) ((e.getY() - radioQuestionSelection.getY()) * radioAnswers.size()) / radioQuestionSelection.height) + 1;
        		}
        	}
        }
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DELETE) {
			if(userInput.length() > 0)
				userInput = userInput.substring(0 , userInput.length() - 1);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			//String[] lines = userInput.split("\n");
			//if(lines.length < numLines - 1) userInput += "\n";
		}
	}
	
	public void keyTyped(KeyEvent e) {
		int lineWidth = 0;
		char c = e.getKeyChar();
		String[] lines = userInput.split("\n");
		String currentLine = lines[lines.length - 1];
		if(currentLine.length() > 0) {
			lineWidth = TBSGraphics.getStringBounds(g2,currentLine).width;
		} else {
			lineWidth = 0;
		}
		if(lineWidth > (promptSize.width - padding.width * 2)) {
			// automatically insert new line for long lines if enough room
			if(lines.length < numLines - 1) {
				if(c != '\b') userInput += "\n";
			} else {
				// out of space
				return;
			}
		}
		if(c == '\b'){
			if(userInput.length() > 0)
				userInput = userInput.substring(0 , userInput.length() - 1);
		} else
			userInput += c;
		// check if max number of lines exceeded
		lines = userInput.split("\n");
		if(lines.length >= numLines) 
			userInput = userInput.substring(0 , userInput.length() - 1);
	}
	
	public void paintComponent(Graphics2D g2) {
		this.g2 = g2;
		textHeight = TBSGraphics.getStringBounds(g2,"QOgj").height;
		promptSize.setSize(750 + padding.width * 2, 0);
		lineBrokenQuestion = new LinkedList<String>();
		int questionLength = 0;
		if(currentQuestion.isRadio()){
			lineBrokenQuestion.addAll(TBSGraphics.breakStringByLineWidth(g2,
					question, promptSize.width - padding.width * 2));
			questionLength = lineBrokenQuestion.size();
			constructRadioQuestions();
		}else
			lineBrokenQuestion.addAll(TBSGraphics.breakStringByLineWidth(g2,
					question, promptSize.width - padding.width * 2));
		calculateValues();
		drawBox();
		drawText(lineBrokenQuestion, questionLength);
		questionStringY += ((textHeight+4) * lineBrokenQuestion.size());
		if(!currentQuestion.isRadio() && userInput != null)
			drawText(Arrays.asList(userInput.split("\n")));
		drawButtons();
	}
	
	public void calculateValues() {
		int lineCount = 2 + lineBrokenQuestion.size();
		if(!currentQuestion.isRadio())
			lineCount += numLines;
		promptSize.setSize(promptSize.width, (textHeight * lineCount) + (padding.height * (lineCount + 1)));
		int centerX = model.getApplet().getWidth() / 2;
		int centerY = model.getApplet().getHeight() / 2;
		anchorPoint = new Point(centerX - (promptSize.width / 2), centerY - (promptSize.height / 2));
		questionStringY = anchorPoint.y;
		buttonHeight = textHeight + padding.height;
		buttonsArea = new Rectangle(anchorPoint.x, anchorPoint.y + (promptSize.height - buttonHeight),
				promptSize.width, buttonHeight);
	}
	
	public void drawBox() {
		Rectangle box = new Rectangle(anchorPoint.x-2, anchorPoint.y-2, promptSize.width+4, promptSize.height+4);
		g2.setColor(Color.lightGray);
		g2.fill(box);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Rectangle2D.Double(box.x-1.5, box.y-1.5, box.width+3, box.getHeight()+3));
		g2.setStroke(new BasicStroke());
	}
	public void drawText(List<String> lines){ drawText(lines, 1); }
	
	public void drawText(List<String> lines, int startAnswers) {
		int startY = questionStringY;
		int startX = anchorPoint.x + padding.width;
		for(int i=0;i<lines.size();i++){
			if(currentQuestion.isRadio() && (currentRadioQuestion+startAnswers)==(i+1) && buttons.size() > 1)
				drawString(lines.get(i), startX, startY, true);
			else				
				drawString(lines.get(i), startX, startY);
			startY += textHeight + 4;
			if(currentQuestion.isRadio() && (i+1) == startAnswers)
				startX += TBSGraphics.questionButtonsWidth;
		}
		if(currentQuestion.isRadio()){
			startY = questionStringY + ((textHeight + 4) * startAnswers);
			startX = anchorPoint.x + promptSize.width;
			radioQuestionSelection = new Rectangle(anchorPoint.x + padding.width, startY,
					TBSGraphics.questionButtonsWidth, radioAnswers.size() * (textHeight + 4));
			for(int i=0;i<radioAnswers.size();i++){
				String answerText = OpenQuestionPromptButtonType.getRadioText(radioAnswers.get(i));
				int answerWidth = TBSGraphics.getStringBounds(g2,answerText).width + 4;
				if(currentRadioQuestion==(i+1) && buttons.size() > 1)
					drawString(answerText, startX-answerWidth, startY, true);
				else
					drawString(answerText, startX-answerWidth, startY);
				startY += textHeight + 4;
			}
		}
	}
	
	public void drawString(String s, int x, int y){
		drawString(s, x, y, false);
	}
	
	public void drawString(String s, int x, int y, boolean isSelected) {
		if(s != null && s.length() > 0)
			TBSGraphics.drawCenteredString(g2, s, x, y, 0, textHeight + 4, isSelected ? TBSGraphics.emptyNodeColor : Color.BLACK);
	}
	
	public void drawButtons()
	{
		Rectangle buttonRect = new Rectangle(buttonsArea.x, buttonsArea.y,
				buttonsArea.width/buttons.size(), buttonsArea.height);
		for(OpenQuestionPromptButtonType button: buttons) {
			TBSGraphics.renderButtonBackground(g2, buttonRect, false);
			g2.setColor(Color.gray);
			g2.draw(buttonRect);
			TBSGraphics.drawCenteredString(g2, button.toString(), buttonRect.x,
					buttonRect.y + (buttonRect.height - 2), buttonRect.width, 0);
			buttonRect.setLocation(buttonRect.x + buttonRect.width, buttonRect.y);
		}
		if(currentQuestion.isRadio()){
			buttonRect = new Rectangle(radioQuestionSelection.x, radioQuestionSelection.y,
					radioQuestionSelection.width, radioQuestionSelection.height/radioAnswers.size());
			for(int i=1;i<=radioAnswers.size();i++){
				TBSGraphics.renderButtonBackground(g2, buttonRect, false);
				g2.setColor(Color.gray);
				g2.draw(buttonRect);
				TBSGraphics.drawCenteredString(g2, "" + i, buttonRect.x,
						buttonRect.y + (buttonRect.height - 2), buttonRect.width, 0);
				buttonRect.setLocation(buttonRect.x, buttonRect.y + (textHeight + 4));
			}
		}
	}
	
	public OpenQuestionButtonType getCurrentQuestion() {
		return currentQuestion;
	}
	
	public void constructRadioQuestions(){
		for(int i=1;i<=radioAnswers.size(); i++)
			lineBrokenQuestion.add(" " + questionProps.getProperty("questionThree"+i));
	}
	
	public boolean isOverButton(MouseEvent e){
		if(buttonsArea.contains(e.getPoint()))
			return true;
		if(currentQuestion.isRadio())
			return radioQuestionSelection.contains(e.getPoint());
		return false;
	}
	
}