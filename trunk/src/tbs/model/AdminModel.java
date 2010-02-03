//TBS version 0.4
//Model: creates and maintains the logical structure underlying TBS

package tbs.model;

import java.util.LinkedList;
import java.util.List;

import tbs.TBSApplet;
import tbs.TBSGraphics;
import tbs.TBSUtils;
import tbs.model.admin.Student;
import tbs.view.TextEntryBox;
import tbs.view.prompt.admin.AnalysisPrompt;
import tbs.view.prompt.admin.RadioQuestionReviewPrompt;
import tbs.view.prompt.admin.WrittenQuestionReviewPrompt;

public class AdminModel extends TBSModel
{
	private WrittenQuestionReviewPrompt writtenQuestionReviewPrompt;
	private RadioQuestionReviewPrompt radioQuestionReviewPrompt;
	private AnalysisPrompt analysisPrompt;
	private List<Student> students;

	public AdminModel(TBSApplet applet,	List<OrganismNode> organisms, List<Student> students) {
		super(applet, organisms);
		this.students = students;
		Student student = this.students.get(0);
		setStudent(student);
		String tree = student.getTree();
		if(!TBSUtils.isStringEmpty(tree))
			loadTree(tree);
		writtenQuestionReviewPrompt = new WrittenQuestionReviewPrompt(this);
		/*
		 * Until Professor White says otherwise we will be eliminating the radio
		 * portion of the open-response
		 * radioQuestionReviewPrompt = new RadioQuestionReviewPrompt(this);
		 */
	}

	public void changeSavedTree(int studentIndex){
		/*
		 * Make sure your don't re-calculate the selected student's
		 * information
		 */
		System.out.println("Selected Index:" + studentIndex);
		if(studentIndex != students.indexOf(getStudent())){
			Student student = students.get(studentIndex);
			setStudent(student);
			String tree = student.getTree();
			resetModel();
			if(!TBSUtils.isStringEmpty(tree))
				loadTree(tree);
			writtenQuestionReviewPrompt = null;
			analysisPrompt = null;
		}
	}

	public void removeFromTree(ModelElement m){
		if(m == null)
			return;
		if(m instanceof Node){
			Node n = (Node) m;
			unlink(n);
			if(n instanceof OrganismNode)
				((OrganismNode) n).reset();
			else
				removeElement(m);
		}
		else
			removeConnection((Connection) m);
	}

	/**
	 * Unlink had to live in Model when connections were
	 * one-way. Now, this simply calls the Node-based two-way unlink.
	 */
	public void unlink(Node n)
	{
		List<Connection> connections = getConnectionsByNode(n);
		for(Connection c : connections)
			removeConnection(c);
	}

	public List<Student> getStudents(){
		return students;
	}

	public void questionReview() {
		if(writtenQuestionReviewPrompt == null)
			writtenQuestionReviewPrompt = new WrittenQuestionReviewPrompt(this);
		else
			writtenQuestionReviewPrompt.setFinished(false);
		setPrompt(writtenQuestionReviewPrompt);
		getView().refreshGraphics();
	}

	public void analyze(){
		if(analysisPrompt == null)
			analysisPrompt = new AnalysisPrompt(this);
		else
			analysisPrompt.setFinished(false);
		setPrompt(analysisPrompt);
		getView().refreshGraphics();
	}
}