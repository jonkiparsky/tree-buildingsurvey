package tbs.model.admin;

import tbs.view.prompt.buttons.OpenQuestionPromptButtonType;

public class WrittenResponse extends Response{

	private String text;
	
	public WrittenResponse(String input){
		initWritten(input);
	}
	
	public String getText() {
		return text;
	}
	
	public void updateText(String input){
		text = input;
		if(input != null && input != "")
			setCompleted(true);
	}
	
	private void initWritten(String input){
		if(input == null || input == "")
			text = "";
		else{
			text = input.trim();
			setCompleted(true);
		}
	}

	public void updateText(int index, OpenQuestionPromptButtonType answer) {}

}
