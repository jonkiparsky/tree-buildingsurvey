//TBSApplet: the runnable frame class of TBS

package tbs;



import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import tbs.model.TBSModel;
import tbs.view.TBSQuestionButtonType;

/**
* TBSApplet is the frame in which the Tree-Building System runs. Its
* function is to initialize the major classes of the applet and to load
* in the initial data.
*/
public class TBSApplet extends JApplet {
	
	/**
	 * 8-byte serialization class ID generated by
	 * https://www.fourmilab.ch/hotbits/secure_generate.html
	 */
	private static final long serialVersionUID = 0x03046F6687102247L;
	
	private TBSModel model;
	private TBSApplet app = this;

	/**
	* INIT instantiates TBSGraphics and TBSModel, and calls the
	* loadOrganismsFromDirectory method in order to populate the initial
	* set of OrganismNodes.
	*/
	public void init() {

 	EventQueue.invokeLater(new Runnable() {
 		public void run() {
 			TBSGraphics.appletWidth = getWidth();
 			TBSGraphics.appletHeight = getHeight();
 			String savedTree = getParameter("SavedTree");
 			String q1 = getParameter("quest1");
 			String q2 = getParameter("quest2");
 			String q3 = getParameter("quest3");
 			model = new TBSModel(app, savedTree.trim(), getGraphics(), loadOrganismsFromDirectory());
 			add(model.getView());
 			model.getView().addMouseListener(model.getController());
 			model.getView().addMouseMotionListener(model.getController());
 			model.getView().addKeyListener(model.getController());
 			loadQuestions();
 			model.setQuestion(q1.trim(), TBSQuestionButtonType.ONE);
 			model.setQuestion(q2.trim(), TBSQuestionButtonType.TWO);
 			model.setQuestion(q3.trim(), TBSQuestionButtonType.THREE);
 		}});
 	}
 

	/**
	* Initialize ModelElements with names and images of Organisms for
	* this task.
	* The initial data for this program is a flat comma-delimited text file 
	* containing each organism's name, name of the image file containing
	* its icon, and the class to which it is assigned for scoring
	* purposes. LoadImageOrganisms reads and parses this text file to
	* populate the ModelElements list with the organisms the student is
	* to use in building his or her tree.
	*/
 	public TreeMap<String, BufferedImage> loadOrganismsFromDirectory() 
	{
		TreeMap<String, BufferedImage> organismNameToImage = 
			new TreeMap<String, BufferedImage>();
		try 
		{
	// read names of organisms and image file names from list.txt in "/images"
			//URL fileURL=new URL(getCodeBase(),"images/list.txt"); 
			URL fileURL = this.getClass().getResource("images/list.txt");
			URLConnection conn=(URLConnection) fileURL.openConnection(); 
			conn.setRequestProperty("REFERER",getDocumentBase().toString()); 
			InputStream is=conn.getInputStream();
			BufferedReader reader = 
				new BufferedReader(new InputStreamReader(is));
			String line = null;
			String[] parseLine = null;
			String organismName = null;
			String organismImageFilename = null;
			BufferedImage img = null;
 			while ((line = reader.readLine()) != null) 
			{
 	//load image from files, and map organism name to image
 				parseLine = line.split(",");
 				organismName = parseLine[0];
 				organismImageFilename = parseLine[1];
 				if(organismImageFilename != null)
				{
	//URL imageURL=new URL(getCodeBase(), "images/" + organismImageFilename);
					URL imageURL = this.getClass().getResource("images/" + 
						organismImageFilename);
 					URLConnection imageconn=
						(URLConnection) imageURL.openConnection(); 
 					imageconn.setRequestProperty("REFERER",
					getDocumentBase().toString()); 
 					InputStream imageis=imageconn.getInputStream();
 					img = ImageIO.read(imageis);
 					organismNameToImage.put(organismName, img);
 					imageis.close();
 				}
 			}
 			is.close();
 		} catch (Exception e) 
		{
 			e.printStackTrace();
 		} 
 		TBSGraphics.numOfOrganisms = organismNameToImage.size();
 		return organismNameToImage;
	}


	/**
	* Read a file and return a list of Strings, one String per Node.
	*/
	public List<String> loadTreeFile(String fileName)
	{
		URL fileURL = null;	
		URLConnection conn = null;
		InputStream is= null;
		BufferedReader reader = null;
 		ArrayList<String> lines = new ArrayList<String>();
		String line = "";
	try{
		fileURL=new URL(getCodeBase(), fileName);
		System.out.println("Got a file");
		 conn = (URLConnection) fileURL.openConnection();
		conn.setRequestProperty("REFERER", getDocumentBase().toString());
		 is = conn.getInputStream();
		reader= new BufferedReader(new
			InputStreamReader(is));
		line = null;
		while ((line = reader.readLine()) != null)
		{
		 	lines.add(line);
		}
	is.close();	
	}//end try block
	

	catch (Exception e)
	{
		System.out.println("loadTreeFile: "+e);
		e.printStackTrace();

	}
	return lines;
	}//end loadTreeFile
	
	public String getTree(){ return model.exportTree(); }
	
	public String getQ1(){return model.getQuestion(TBSQuestionButtonType.ONE);}
	
	public String getQ2(){return model.getQuestion(TBSQuestionButtonType.TWO);}
	
	public String getQ3(){
		return model.getQuestion(TBSQuestionButtonType.THREE);
	}
	
	public void loadQuestions(){
		Properties props = new Properties();
		try{
			props.load(this.getClass().getResource("questions.properties").openStream());
			model.setQuestionProperties(props);
		}catch(Exception e){
			System.out.println("Unable to load question properties: " + e);
		}
	}
}

