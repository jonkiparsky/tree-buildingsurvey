//TBS Version 0.4
package tbs.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import tbs.TBSGraphics;
import tbs.TBSUtils;
import tbs.model.EmptyNode;
import tbs.model.ModelElement;
import tbs.model.Node;
import tbs.model.OrganismNode;
import tbs.model.TBSModel;
import tbs.model.*;
public class TBSView extends JComponent {

	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0xBB7D0BF0A83E3AF6L;
	
	// This connection follows the mouse
	private Point[] connInProgress = null;
	
	private TBSModel model;
	public TBSView(TBSModel m) {
        model = m;
	}
	
	public String promptUserForString(String message) {
		return (String) JOptionPane.showInputDialog(message);
	}
	
	public int promptUserForYesNoCancel(String message) {
		return JOptionPane.showConfirmDialog(null, message);
	}
	
	public void renderButtons(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 800, TBSGraphics.buttonsHeight);
		int leftX = 0;
		int upperY = TBSGraphics.buttonsHeight - TBSGraphics.buttonsYPadding;
		for(String s: TBSGraphics.buttons) {
			g2.setColor(Color.BLACK);
			TBSGraphics.drawCenteredString(g2, s, leftX, upperY, TBSGraphics.buttonsWidth, 0);
			g2.setColor(Color.BLUE);
			g2.drawRect(leftX, 0,TBSGraphics.buttonsWidth, TBSGraphics.buttonsHeight);
			leftX += TBSGraphics.buttonsWidth;
		}
	}

	public void renderModelElement(Graphics g, ModelElement me) {
		Graphics2D g2 = (Graphics2D) g;
		int stringWidth = 0;
		int imageWidth = 0;
		int imageStartX = 0;
		if(me instanceof OrganismNode) 
		{
			OrganismNode on = (OrganismNode) me;
			stringWidth = (int) TBSGraphics.getStringBounds(g2, on.getName()).getWidth();
			imageWidth = on.getImage().getWidth();
			// center image and text
			int imageXOffset = (TBSGraphics.organismNodeWidth - imageWidth - stringWidth) / 2;
			imageStartX = on.getLeftX() + imageXOffset;
			g2.setColor(TBSGraphics.organismBoxColor);
			g2.fillRect(on.getLeftX(), on.getUpperY(), TBSGraphics.organismNodeWidth, TBSGraphics.organismNodeHeight);
			g2.drawImage(on.getImage(), imageStartX, on.getUpperY(), null);
			int stringAreaLeftX = imageStartX + imageWidth + TBSGraphics.paddingWidth;
			int stringAreaWidth = stringWidth;
			int stringAreaUpperY = on.getUpperY();
			int stringAreaHeight = TBSGraphics.organismNodeHeight;			
			TBSGraphics.drawCenteredString(g2, on.getName(), stringAreaLeftX, stringAreaUpperY, stringAreaWidth, stringAreaHeight);
		}
		else if (me instanceof EmptyNode)
		{
			EmptyNode en = (EmptyNode) me;
			String name = en.getName();
			int leftX = en.getLeftX();
			int upperY = en.getUpperY();
			if(name == null) name = "";
			// make empty nodes light purple (like Prof. White's node.gif)
			g2.setColor(new Color(0.5f, 0.5f, 1.0f));
			g2.fillRect(en.getLeftX(), en.getUpperY(), en.getWidth(), en.getHeight());
			// make bold for greater visibility;
	  		Font f = new Font(TBSGraphics.fontName, TBSGraphics.fontStyle, TBSGraphics.fontSize);
	   		g2.setFont(f);
			if(name.length() > 0) {
				// zero length string gives an error
				Rectangle2D bounds = TBSGraphics.getStringBounds(g2, en.getName());
				int h = (int) bounds.getHeight();
				int w = (int) bounds.getWidth();
				int stringX = leftX + (en.getWidth() / 2) - (w / 2);
				int stringY = upperY - h;
				g2.drawString(name, stringX, stringY);
			}
		}
	}
	
	public void setConnInProgress(Point[] conn) {
		connInProgress = conn;
	}
	
	public void refreshGraphics() {
		repaint();	
	}
	
	public void renderConnections(Graphics2D g2) {
		for(ModelElement me: model.getElements()) {
			if(me instanceof Node) {
				Node fromNode = (Node) me;
				for(Connection c: fromNode.getConnections()) {
					Point[] conn = TBSUtils.computeConnectionBounds(c.getFromNode() , 
						c.getToNode());
					g2.setColor(Color.WHITE);
					g2.drawLine(conn[0].x, conn[0].y, conn[1].x, conn[1].y);
				}
			}
		}
		if(connInProgress != null) {
			g2.setColor(Color.WHITE);
			g2.drawLine(connInProgress[0].x, connInProgress[0].y, connInProgress[1].x, connInProgress[1].y);
			drawArrow(connInProgress, g2);
		}
		
	}
	
	public void drawArrow(Point[] conn, Graphics2D g2) {
		/*
		double dx = (conn[1].x - conn[0].x);
		double dy = (conn[1].y - conn[0].y);
		double slope = dy / dx;
		double perp = -1.0 * slope;
		double lineX = conn[1].x + slope * 5;
		double lineY = conn[1].y; // + perp * 5;;
		g2.setColor(Color.WHITE);
		g2.drawLine((int)lineX, (int) lineY, conn[1].x, conn[1].y);
		*/
	}

	// this is what the applet calls to refresh the screen
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.fillRect(0, 0, getWidth(), getHeight());
		refreshGraphics();
		Iterator<ModelElement> itr = model.getElements().iterator();
		while(itr.hasNext()) {
			renderModelElement(g, itr.next());
		}
		renderConnections(g2);
		renderButtons(g2);
		
	}
}
