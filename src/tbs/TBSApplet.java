//TBSApplet: the runnable frame class of TBS

package tbs;



import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import tbs.controller.AdminController;
import tbs.controller.StudentController;
import tbs.model.AdminModel;
import tbs.model.OrganismNode;
import tbs.model.StudentModel;
import tbs.model.TBSModel;
import tbs.model.admin.Response;
import tbs.model.admin.Student;
import tbs.properties.PropertyLoader;
import tbs.view.AdminView;
import tbs.view.OpenQuestionButtonType;
import tbs.view.StudentView;
import tbs.view.prompt.Prompt;
import tbs.view.prompt.student.WelcomePrompt;
import tbs.view.prompt.student.WrittenQuestionPrompt;

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
	private boolean admin;
	private String browser;

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
				browser = getParameter("Browser");
				TBSGraphics.updateBrowserSpecs(browser);
				Graphics2D g2 = (Graphics2D) getGraphics();
				RenderingHints rh = new RenderingHints(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2.setRenderingHints(rh);
				g2.setFont(TBSGraphics.font);

				//Update some constants
				TBSGraphics.textHeight = TBSGraphics.getStringBounds(g2,"QOgj").height;
				TBSGraphics.checkWidth = TBSGraphics.getStringBounds(g2, " \u2713").width;
				TBSGraphics.arrowWidth = TBSGraphics.getStringBounds(g2, " \u2192").width;

				PropertyLoader.loaderLocation = this.getClass();
				List<OrganismNode> organisms = loadOrganisms(g2);
				String adminStr = getParameter("Admin");
				admin = Boolean.parseBoolean(adminStr);
				String studentDataString;
				if(!admin){
					studentDataString = getParameter("student");
					StudentModel studentModel = new StudentModel(app, organisms, studentDataString);
					StudentView view = new StudentView(g2, studentModel);
					StudentController controller = new StudentController(studentModel, view);
					studentModel.setView(view);
					studentModel.setController(controller);
					studentModel.setPrompt(new WelcomePrompt(studentModel));
					model = studentModel;
				}else{
					int studentCt = 0;//Default number of radio questions
					String numOfStudents = getParameter("StudentCount");
					try{
						studentCt = Integer.parseInt(numOfStudents);
					} catch(NumberFormatException e) {
						System.out.println(new StringBuffer("TBSApplet:Error parsing student count(value-").append(numOfStudents).append(")").toString());
					}
					List<Student> students = loadStudents(g2, studentCt);
					AdminModel adminModel = new AdminModel(app, organisms, students);
					AdminView view = new AdminView(g2, adminModel);
					AdminController controller = new AdminController(adminModel, view);
					adminModel.setView(view);
					adminModel.setController(controller);
					model = adminModel;
					
				}
				add(model.getView());
				model.getView().addMouseListener(model.getController());
				model.getView().addMouseMotionListener(model.getController());
				model.getView().addKeyListener(model.getController());
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
	public List<OrganismNode> loadOrganisms(Graphics2D g2) 
	{
		List<OrganismNode> organisms = new LinkedList<OrganismNode>();
		Properties props;
		String imageFilename = "";
		try 
		{
			props = PropertyLoader.loadPropertyFile("organisms");
			if(props.size() == 0)
				return new LinkedList<OrganismNode>();
			TBSGraphics.numOfOrganisms = props.size();
			BufferedImage img = null;
			String name, value;
			String[] splitValue;
			int i =0;
			Set<String> keys = new TreeSet<String>();
			for(Object o : props.keySet())
				keys.add(o.toString());
			for(String key : keys){
				name = key.replace("_", " ");
				Dimension d = TBSGraphics.getStringBounds(g2, name);
				if(d.width > TBSGraphics.maxOrganismStringWidth) 
					TBSGraphics.maxOrganismStringWidth = d.width;
				if(d.height > TBSGraphics.maxOrganismStringHeight) 
					TBSGraphics.maxOrganismStringHeight = d.height;
				value = props.getProperty(key).toString();
				splitValue = value.split(",");
				imageFilename = new StringBuffer("images/").append(splitValue[0]).toString();
				URL imageURL = this.getClass().getResource(imageFilename);
				URLConnection imageconn = (URLConnection) imageURL.openConnection(); 
				imageconn.setRequestProperty("REFERER", getDocumentBase().toString()); 
				InputStream imageis=imageconn.getInputStream();
				img = ImageIO.read(imageis);
				if(img.getWidth() > TBSGraphics.maxOrganismImageWidth) 
					TBSGraphics.maxOrganismImageWidth = img.getWidth();
				if(img.getHeight() > TBSGraphics.maxOrganismImageHeight) 
					TBSGraphics.maxOrganismImageHeight = img.getHeight();
				organisms.add(new OrganismNode( i, name, new Point(), img, d.width));
				imageis.close();
				i++;
			}
		} catch (Exception e){
			System.out.println(new StringBuffer("Error loading image ")
			.append(imageFilename).append(": ").append(e).toString());
			return organisms;
		} 
		return organisms;
	}

	public List<Student> loadStudents(Graphics2D g2, int studentParameterCount){
		TBSGraphics.studentNodeHeight = 0;
		List<Student> students = new LinkedList<Student>();
		int lines = 0;
		String studentDataString;
		for(int i=1; i<=studentParameterCount; i++){
			studentDataString = getParameter("student"+i);
			Student temp = new Student(studentDataString, i-1);
			temp.setNodeName(TBSGraphics.breakStringByLineWidth(g2, temp.getName(), TBSGraphics.maxStudentNameWidth));
			if(temp.getNodeName().size() > lines) 
				lines = temp.getNodeName().size();
			students.add(temp);
		}
		TBSGraphics.studentNodeHeight = lines * TBSGraphics.textHeight;
		TBSGraphics.studentNodeHeight += TBSGraphics.padding.width * 2;
		return students;
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
		} catch (Exception e){
			System.out.println("loadTreeFile: "+e);
			e.printStackTrace();
		}
		return lines;
	}

	public String getTree(){return model.exportTree();}

	public String getQ1(){
		Map<OpenQuestionButtonType, Response> responses = model.getStudent().getResponses();
		if(responses.containsKey(OpenQuestionButtonType.values()[0]))
			return responses.get(OpenQuestionButtonType.values()[0]).getText();
		else
			return "";
	}

	public String getQ2(){
		Map<OpenQuestionButtonType, Response> responses = model.getStudent().getResponses();
		if(responses.containsKey(OpenQuestionButtonType.values()[1]))
			return responses.get(OpenQuestionButtonType.values()[1]).getText();
		else
			return "";
	}	
	public String getQ3(){
		Map<OpenQuestionButtonType, Response> responses = model.getStudent().getResponses();
		if(responses.containsKey(OpenQuestionButtonType.values()[2]))
			return responses.get(OpenQuestionButtonType.values()[2]).getText();
		else
			return "";
	}

	public String getStatus(){
		if(admin)
			return "";
		else{
			StudentModel m = (StudentModel)model;
			StringBuffer status = new StringBuffer(m.surveyStatus());
			String unusedOrganisms = m.unusedOrganisms();
			if(status.length() > 0){
				status.append("\n\n");
				if(unusedOrganisms.length() > 0)
					status.append("Also, you have not used the following organisms:\n").append(m.unusedOrganisms());
			}else{
				if(unusedOrganisms.length() > 0)
					status.append("You have not used the following organisms:\n").append(m.unusedOrganisms());
			}
			return status.toString();
		}
	}

	public String questionInProgress(){
		Prompt p = model.getPrompt();
		if(p instanceof WrittenQuestionPrompt)
			return ((WrittenQuestionPrompt)p).getCurrentQuestion().getText();
		return "";
	}

	public void acceptQuestionInProgress(){
		WrittenQuestionPrompt p = (WrittenQuestionPrompt) model.getPrompt();
		p.forceAcceptChanges();
	}

	public String[][] getParameterInfo(){
		String[][] parameterInfo = null;
		if(admin){
			parameterInfo = new String[4][];
			parameterInfo[0] = new String[]{"Admin", "Boolean", "This tells the applet to run the admin version if true & the student version if false"};
			parameterInfo[1] = new String[]{"StudentCount", "Integer", "In admin mode this parameter tells the applet how many 'Student' parameters to get"};
			parameterInfo[2] = new String[]{"Student(#)", "String", "This contains the student data(name, last update date, tree, open-responses, section [used for has arrows or not]) for each student"};
			parameterInfo[3] = new String[]{"Browser", "String", "This contains information about the browser that has accessed this applet"};
		}else{
			parameterInfo = new String[3][];
			parameterInfo[0] = new String[]{"Admin", "Boolean", "This tells the applet to run the admin version if true & the student version if false"};
			parameterInfo[1] = new String[]{"Student", "String", "This contains the student data(name, last update date, tree, open-responses, section [used for has arrows or not]) for this student"};
			parameterInfo[2] = new String[]{"Browser", "String", "This contains information about the browser that has accessed this applet"};
		}
		return parameterInfo;
	}

	public String getAppletInfo(){
		return "Diversity of Life Survey Applet, Version 1.3\n"
		+ "Copyright Tree Building Survey Group,2010";
	}
}

