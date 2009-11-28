package tbs.model.history;

import tbs.model.Connection;
import tbs.model.Node;
import tbs.model.TBSModel;

public class Link extends Command{

	private Connection connection;
	
	public Link(Connection connection){
		this.connection = connection;
	}
	
	@Override
	public void execute(TBSModel model) {
		
	}

	@Override
	public void undo(TBSModel model) {
		System.out.println("Undoing link command.");
		int index = model.findIndexByElement(connection);
		if(index >= 0){
			model.removeElement(index);
			index = model.findIndexByElement(connection.getFrom());
			((Node) model.getElement(index)).removeConnectionTo(connection.getTo());
			index = model.findIndexByElement(connection.getTo());
			((Node) model.getElement(index)).removeConnectionFrom(connection.getFrom());
		}
	}

}
