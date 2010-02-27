//TBS Version 0.4
//TBSGraphics: Constants and low-level methods for graphics handling
package tbs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import tbs.model.ModelElement;

/**
 * This class encapsulates the constants used in graphics handling for
 * the TBS applet, and contains some grunt-work methods for setting up
 * objects in the Node hierarchy.
 * This class has only static methods and perhaps should be declared
 * static. In any case, there is no constructor as it is not intended to
 * be instantiated.
 */

public class TBSGraphics {

	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0xE6D7FA0516CC8DB2L;

	/**
	 * Applet width: Set by TBSApplet to size of browser window.
	 */	
	public static int appletWidth;

	/**
	 * Applet height: set by TBSApplet to size of browser window.
	 */
	public static int appletHeight;

	/** 
	 * The LINE_OF_DEATH is the vertical line separating the active
	 * elements of the model from the inactive; when a @ModelElement is
	 * moved across it, it is either placed in the tree or removed,
	 * depending on whether it lands in the active or the inactive
	 * portion. 
	 */
	public static int LINE_OF_DEATH = 180;
  
  /**
   * Universal x & y padding dimensions for applet, used for:
   *  -Minimum number of pixels around the right and left of an organism's name
   *  -Vertical spacing of buttons
   *  -Horizontal spacing of buttons
   *  -Prompt padding
   */
  public static Dimension padding = new Dimension(10,5);

	/**
	 * Total number of Organisms contained in the applet, this will
	 * be used in the future to more efficiently search the list of
	 * ModelElement objects in the Model.
	 */
	public static int numOfOrganisms;

	/**
	 * The fixed width of all OrganismNodes. Value is calculated in
	 * StudentView
	 */ 
	public static int organismNodeWidth = 0;

	/**
	 * The fixed height of all OrganismNodes. Value is calculated in
	 * StudentView
	 */ 
	public static int organismNodeHeight = 0;

	/**
	 * The fixed width of all OrganismNode label strings. Value is calculated in
	 * StudentView
	 */ 
	public static int maxOrganismStringWidth = 0;

	/**
	 * The fixed height of all OrganismNode label strings. Value is calculated in
	 * TBSApplet.loadOrganisms()
	 */ 
	public static int maxOrganismStringHeight = 0;

	/**
	 * The fixed width of all OrganismNode images. Value is calculated in
	 * TBSApplet.loadOrganisms()
	 */ 
	public static int maxOrganismImageWidth = 0;

	/**
	 * The fixed height of all OrganismNode images. Value is calculated in
	 * TBSApplet.loadOrganisms()
	 */ 
	public static int maxOrganismImageHeight = 0;

	/**
	 * The fixed width of all OrganismNodes. Value is calculated in
	 * TBSApplet.loadOrganisms()
	 */ 
	public static int maxStudentNameWidth = 150;
	public static int checkWidth = 0;
	public static int arrowWidth = 0;


	/**
	 * The fixed height of all studentNodes. Value is calculated in
	 * TBSModel.createModelElements (CHECK THIS)
	 */ 
	public static int studentNodeHeight = 0;

	/**
	 * The fixed width of all student:Node label strings. Value is calculated in
	 * TBSModel.createModelElements (CHECK THIS)
	 */ 
	public static int maxStudentStringWidth = 0;

	/**
	 * The fixed height of all OrganismNode label strings. Value is calculated in
	 * TBSModel.createModelElements (CHECK THIS)
	 */ 
	public static int maxStudentStringHeight = 0;

	/**
	 * Label for inTree empty nodes
	 */
	public static String emptyNodeLabel = "#%d";

	public static int maxNameLength = 30;
	
	/*
	 * Regex patten checking for non-alphanumeric characters being 
	 * entered when labeling an empty node
	 */
	public static Pattern emptyNodePattern = Pattern.compile("[0-9a-zA-Z' ']");
	public static Pattern writtenResponseIllegalCharacters = Pattern.compile("[+=\"]");

	public static int maxLinesOfWrittenText = 8;

	/**
	 * Label for immortal empty node
	 */
	public static String immortalNodeLabel = "Branch Point";


	/**
	 * Width of immortal empty node label
	 */
	public static int immortalNodeLabelWidth = 0;

	/**
	 * The fixed width of all EmptyNodes. 
	 */ 
	public static int emptyNodeWidth = 20;

	/**
	 * The fixed height of all EmptyNodes. 
	 */ 
	public static int emptyNodeHeight = 20;


	/**
	 * The initial x-coordinate of all EmptyNodes. Value is calculated in
	 * TBSModel.createModelElements (CHECK THIS)
	 */ 
	public static int emptyNodeLeftX;

	/**
	 * The initial y-coordinate of all EmptyNodes. Value is calculated in
	 * TBSModel.createModelElements (CHECK THIS)
	 */ 
	public static int emptyNodeUpperY;
	public static int emptyNodeYLabelOffset = 5;
	public static int emptyNodePadding = 4;

	/**
	 * The initial length of all arrowheads.
	 */ 
	public static double arrowLength = 0.1;

	/**
	 * Space between bottom and top of images [in the left-hand column] 
	 */
	public static int ySpacing = 1;

	// Font Properties
	public static Font font = null;
	public static Font tooltipFont = new Font("default", Font.PLAIN, 12);

	/**
	 * Color of text strings labeling OrganismNodes. Currently set to
	 * black.
	 */
	public static Color organismStringColor = Color.BLACK;

	/**
	 * Background color of organismNodes. Currently set to white.
	 */
	public static Color organismBoxColor = Color.WHITE;
	public static Color connectionColor = new Color(0.5f, 1.0f, 0.5f);
	public static Color emptyNodeColor = new Color(0.5f, 0.5f, 1.0f);
	public static Color selectedPromptTextColor = emptyNodeColor;

	/**
	 * Styling of selected elements
	 */
	public static Color connectionSelectedColor = Color.GREEN;
	public static Color selectedNodeBorderColor = Color.GREEN;

	/**
	 * Height of buttons. Set in  [TBSModel.???]
	 */
	public static int buttonsHeight = 0;

	/**
	 * Width of hull buttons. Set in [TBSModel.???]
	 */ 
	public static int hullButtonWidth = 0;
	
	/**
	 * Height of hull buttons. Set in  [TBSModel.???]
	 */
	public static int hullButtonHeight = 0;

	/**
	 * Width of buttons. Set in [TBSModel.???]
	 */ 
	public static int buttonsWidth = 0;

	/**
	 * Button Colors
	 */
	public static Color buttonNotSelected = new Color(0.45f, 0.55f, 0.65f);
	public static Color buttonSelected = new Color(0.2f, 0.8f, 0.2f);
	public static Color buttonEnd = new Color(1.0f, 1.0f, 1.0f);


	/**
	 * Space between buttons and question buttons.
	 */ 
	public static int spaceBeforeQuestionButtons = 60;

	/**
	 * Width of question buttons. Set in [TBSModel.???]
	 */ 
	public static int questionButtonsWidth = 0;
	
	/**
	 * Width of question buttons. Set in [TBSModel.???]
	 */ 
	public static int namesButtonWidth = 0;

	/**
	 * Starting x-coordinate of question buttons. Set in [TBSModel.???]
	 */ 
	public static int questionButtonsStart = 0;

	public static Stroke closeButtonStroke = null;

	public static int textHeight = 0;

	public static Comparator<ModelElement> elementIdComparator = new Comparator<ModelElement>() {
		public int compare( ModelElement o1, ModelElement o2 ) {
			return o1.getId().compareTo(o2.getId());
		}
	};

	/**
	 * Returns the @Rectangle2D surrounding a piece of text
	 */
	public static Dimension getStringBounds(Graphics2D g2, String s) {
		if(TBSUtils.isStringEmpty(s))
			return new Dimension();
		TextLayout layout = new TextLayout(s, g2.getFont(), g2.getFontRenderContext());
		Rectangle2D bounds = layout.getBounds();
		return new Dimension((int) bounds.getWidth(), (int) bounds.getHeight());
	}

	public static Dimension get2DStringBounds(Graphics2D g2, Collection<?> strings) 
	{
		Point max = new Point(0,0);
		for(Object s: strings) 
		{
			Dimension bounds = getStringBounds(g2, s.toString());
			if(bounds.width > max.x) 
				max.x = (int) bounds.getWidth();
			if(bounds.height > max.y) 
				max.y = (int) bounds.getHeight();
		}
		return new Dimension(max.x, max.y);
	}

	public static void drawCenteredString(Graphics2D g2, String s, 
			int leftX, int upperY, int width, int height) { 
		drawCenteredString(g2, s, leftX, upperY, width, height, Color.black);
	}


	/**
	 * Paints a string centered in the rectangle defined.
	 */	
	public static void drawCenteredString(Graphics2D g2, String s, 
			int leftX, int upperY, int width, int height, Color c){
		drawCenteredString(g2, s, leftX, upperY, width, height, c, font);
	}

	public static void drawCenteredString(Graphics2D g2, String s, 
			int leftX, int upperY, int width, int height, Color c, Font f) 
	{
		if(TBSUtils.isStringEmpty(s))
			return;
		g2.setColor(c);
		TextLayout layout = new TextLayout(s, g2.getFont(), g2.getFontRenderContext());
		Rectangle2D bounds = layout.getBounds();
		int stringHeight = (int) bounds.getHeight();
		int stringWidth = (int) bounds.getWidth();
		float x,y;
		if(width == 0)
			x = leftX;
		else
			x = leftX + (width - stringWidth) / 2;
		if(height == 0)
			y = upperY;
		else
			y = upperY + height - (height - stringHeight) / 2;
		// if width or height is 0, do not center along that axis
		layout.draw(g2, x, y);
	}

	public static void renderButtonBackground(Graphics2D g2, Rectangle button, boolean selected) {
		Color start = selected ? TBSGraphics.buttonSelected : TBSGraphics.buttonNotSelected;
		Color end = TBSGraphics.buttonEnd;

		float redDiff = end.getRed() - start.getRed();
		float greenDiff = end.getGreen() - start.getGreen();
		float blueDiff = end.getBlue() - start.getBlue();
		for(int y = button.y; y <= button.y + button.height / 3; y++) {
			float fy = (float) (y - button.y);
			float fh = (float) button.height / 3;
			float fdiff = 0.6f + 0.4f * fy / fh;
			float red = start.getRed() + redDiff * fdiff;
			float green = start.getGreen() + greenDiff * fdiff;
			float blue = start.getBlue() + blueDiff * fdiff;
			red /= 255.0f;
			green /= 255.0f;
			blue /= 255.0f;
			g2.setColor(new Color(red, green, blue));
			g2.drawLine(button.x, y , button.x + button.width, y);
		}
		for(int y = button.y + button.height / 3; y < button.y + button.height; y++) {
			float fy = (float) y - (button.height / 3) - button.y;
			float fh = (float) 2.0f * (button.height / 3);
			float fdiff = fy / fh;
			float red = end.getRed() - redDiff * fdiff;
			float green = end.getGreen() - greenDiff * fdiff;
			float blue = end.getBlue() - blueDiff * fdiff;
			red /= 255.0f;
			green /= 255.0f;
			blue /= 255.0f;
			g2.setColor(new Color(red, green, blue));
			g2.drawLine(button.x, y , button.x + button.width, y);
		}
	}

	public static List<String> breakStringByLineWidth(Graphics2D g2, String s, int width){
		String currentLine = "";
		List<String> widthBrokenString = new LinkedList<String>();
		if(TBSUtils.isStringEmpty(s)){
			widthBrokenString.add("");
			return widthBrokenString;
		}
		for(String token : s.split(" ")){
			if(TBSGraphics.getStringBounds(g2, currentLine + token).width > width){
				widthBrokenString.add(currentLine);
				currentLine = token + " ";
			}else{
				currentLine += token + " ";
			}
		}
		if(currentLine.length() > 0)
			widthBrokenString.add(currentLine);
		return widthBrokenString;
	}

	public static void updateBrowserSpecs(String browser){
		Font tempFont = new Font("default", Font.BOLD, 16);
		Stroke stroke = new BasicStroke(3);
		if(browser != null && browser.length() > 0){
			if((browser.toLowerCase()).contains("mac")){
				tempFont = new Font("default", Font.PLAIN, 16);
				stroke = new BasicStroke();
			}
		}
		font = tempFont;
		closeButtonStroke = stroke;
	}
}
