package tbs.view;

public enum TBSButtonType {

	SELECT("Select"),
	ADD("Add"),
	DELETE("Delete"),
	CONNECT("Link"),
	DISCONNECT("Unlink"),
	LABEL("Label"),
	PRINT("Print"), 
	UNDO("Undo"), 
	SAVE("Save");
	
	private String text;
	
	private TBSButtonType(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	@Override
	public String toString(){
		return text;
	}
}
