package tbs;
//TBS Version 0.3

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

/**
* This class encapsulates the constants used in graphics handling for
* the TBS applet, and contains some grunt-work methods for setting up
* objects in the Node hierarchy.
*/

public class TBSGraphics {
	
	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0xE6D7FA0516CC8DB2L;
	
	public static int appletWidth;
	public static int appletHeight;
	
	//boundary between active and inactive elements.
	//Name can be changed. 
	public static int LINE_OF_DEATH = 120;
	
	// Contains the length and width of all organism nodes
	public static int organismNodeWidth = 0;
	public static int organismNodeHeight = 0;
	public static int maxOrganismStringWidth = 0;
	public static int maxOrganismStringHeight = 0;
	public static int maxOrganismImageWidth = 0;
	public static int maxOrganismImageHeight = 0;
	

	public static int emptyNodeWidth = 20;
	public static int emptyNodeHeight = 20;
	public static int emptyNodeLeftX;
	public static int emptyNodeUpperY;
	public static int emptyNodeYLabelOffset = 5;
	
	// minimum number of pixels around the right and left of an organism's name
	public static int paddingWidth = 5;
	
	// Space between bottom and top of images
	public static int ySpacing = 1;
	
	// Font Properties
	public static String fontName = "default"; // Use default font
	public static int fontStyle = Font.BOLD;
	public static int fontSize = 16;
	
	public static Color organismStringColor = Color.BLACK;
	public static Color organismBoxColor = Color.WHITE;

	public static int buttonsYPadding = 5;
	public static int buttonsXPadding = 10;
	public static int buttonsHeight = 0;
	public static int buttonsWidth = 0;
	public static ArrayList<String> buttons;
	
	public static Font getFont(Graphics2D g2) {
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		Font f = new Font(fontName, fontStyle, fontSize);
		g2.setFont(f);
		return f;
	}
	
	public static Rectangle2D getStringBounds(Graphics2D g2, String name) {
		Font f = TBSGraphics.getFont(g2);
   		FontRenderContext frc = g2.getFontRenderContext();
   		TextLayout layout = new TextLayout(name, f, frc);
   		return layout.getBounds();
	}
		
	public static Point getMaxBounds(ArrayList<Point> points) {
		Point max = new Point(0,0);
		for(Point p: points) {
			int width = (int) p.x;
			int height = (int) p.y;
			if(width > max.x) max.x = width;
			if(height > max.y) max.y = height;
		}
		return max;	
	}
	
	public static Point get2DStringBounds(Graphics2D g2, 
			Collection<String> strings) 
	{
		ArrayList<Point> points = new ArrayList<Point>();
		for(String s: strings) 
		{
			Rectangle2D bounds = getStringBounds(g2, s);
			points.add(new Point((int) bounds.getWidth(), 
				(int) bounds.getHeight()));
		}
		return getMaxBounds(points);
	}
	
	public static Point get2DImageBounds(Graphics2D g2, 
			Collection<BufferedImage> images) 
	{
		ArrayList<Point> points = new ArrayList<Point>();
		for(BufferedImage i: images) 
		{
			points.add(new Point(i.getWidth(), i.getHeight()));
		}
		return getMaxBounds(points);
	}
	
	public static Point get2DBounds(Point p0, Point p1) 
	{
		if(p1.x > p0.x) p0.x = p1.x;
		if(p1.y > p0.y) p0.y = p1.y;
		return p0;
	}
	
	public static void drawCenteredString(Graphics2D g2, String s, 
				int leftX, int upperY, int width, int height) 
	{
		// RenderingHints tell
		g2.setColor(Color.black);
   		Font f = TBSGraphics.getFont(g2);
   		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout(s, f, frc);
		Rectangle2D bounds = layout.getBounds();
		int stringHeight = (int) bounds.getHeight();
		int stringWidth = (int) bounds.getWidth();
		float y = upperY + height - (height - stringHeight) / 2;
   		float x = leftX + (width - stringWidth) / 2;
   		// if width or height is 0, do not center along that axis
   		if(width == 0) x = leftX;
   		if(height == 0) y = upperY;
   		layout.draw(g2, x, y);
	}

	
	
}
