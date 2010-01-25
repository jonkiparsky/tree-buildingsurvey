package tbs.view;

import java.util.LinkedList;
import java.util.List;

public enum OpenQuestionButtonType {

	ONE("Question 1", "questionOne", false, -1),
	TWO("Question 2", "questionTwo", false, -1);

	/*
	 * We are eliminating the radio question, until Professor White
	 * says otherwise. Here is the code to add it:
	 * THREE("Question 3", "questionThree",true, 13);
	 * To add any more just write the same thing with new information.
	 */
	
	private String adminText;
	
	private String questionKey;
	
	private boolean radio;
	
	private int radioQuestionCount;
	
	private OpenQuestionButtonType(String adminText,
			String questionKey, boolean radio, int radioQuestionCount){
		this.adminText = adminText;
		this.questionKey = questionKey;
		this.radio = radio;
		this.radioQuestionCount = radioQuestionCount;
	}
	
	public String getAdminText(){
		return adminText;	
	}
	
	public String getQuestionKey(){
		return questionKey;	
	}
	
	public boolean isRadio(){
		return radio;
	}
	
	public int getRadioQuestionCount(){
		return radioQuestionCount;
	}
	
	public String toString(){
		return adminText;
	}
	
	public static List<OpenQuestionButtonType> getRadioButtons(){
		List<OpenQuestionButtonType> buttons = new LinkedList<OpenQuestionButtonType>();
		for(OpenQuestionButtonType button : values()){
			if(button.radio)
				buttons.add(button);
		}
		return buttons;
	}
	
	public static List<OpenQuestionButtonType> getWrittenButtons(){
		List<OpenQuestionButtonType> buttons = new LinkedList<OpenQuestionButtonType>();
		for(OpenQuestionButtonType button : values()){
			if(!button.radio)
				buttons.add(button);
		}
		return buttons;
	}
}
