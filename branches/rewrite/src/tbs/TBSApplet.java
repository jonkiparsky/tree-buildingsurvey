package tbs;
//TBS Version 0.3



import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import tbs.controller.TBSController;
import tbs.model.TBSModel;
import tbs.view.TBSView;

public class TBSApplet extends JApplet {
	
	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0x03046F6687102247L;
	
	private TBSModel model;
	private TBSView view;
	private TBSController controller;

	public void init() {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	model = new TBSModel(getGraphics(), loadOrganismsFromDirectory("images"));
                add(model.getView());
                model.getView().addMouseListener(model.getController());
                model.getView().addMouseMotionListener(model.getController());
                TBSGraphics.appletWidth = getWidth();
                TBSGraphics.appletHeight = getHeight();
            }
        });
    }
    
   	public TreeMap<String, BufferedImage> loadOrganismsFromDirectory(String directoryName) {
		TreeMap<String, BufferedImage> organismNameToImage = new TreeMap<String, BufferedImage>();
		try {
			// read names of organisms and image file names from list.txt in "/images"
			//URL fileURL=new URL(getCodeBase(),"images/list.txt"); 
			URL fileURL = this.getClass().getResource("images/list.txt");
			URLConnection conn=(URLConnection) fileURL.openConnection();    
			conn.setRequestProperty("REFERER",getDocumentBase().toString());    
			InputStream is=conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			String[] parseLine = null;
			String organismName = null;
			String organismImageFilename = null;
			BufferedImage img = null;
            while ((line = reader.readLine()) != null) {
	            // load image from files, and map organism name to image
                parseLine = line.split(",");
                organismName = parseLine[0];
                organismImageFilename = parseLine[1];
                if(organismImageFilename != null){
                	//URL imageURL=new URL(getCodeBase(), "images/" + organismImageFilename);
                	URL imageURL = this.getClass().getResource("images/" + organismImageFilename);
                	URLConnection imageconn=(URLConnection) imageURL.openConnection();    
                	imageconn.setRequestProperty("REFERER",getDocumentBase().toString());    
                	InputStream imageis=imageconn.getInputStream();
                	img = ImageIO.read(imageis);
                	organismNameToImage.put(organismName, img);
                	imageis.close();
                }
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return organismNameToImage;
	}
    
}

