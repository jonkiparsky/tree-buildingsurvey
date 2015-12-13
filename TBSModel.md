# Introduction #
TBSModel is the superclass for the two Model objects representing the data under inspection. The two Models - [StudentModel](StudentModel.md) and [AdminModel](AdminModel.md) - have somewhat different ways of handling data due to the differing needs of the two classes of users.

## Methods ##

findIndexByID()
Returns the index number for the [ModelElement](ModelElement.md) with this ID number.


getElementBySN()
Returns the [ModelElement](ModelElement.md) with the given serial number. This method relies on the fact that objects are added in serial# order, and remain sorted, although they may be deleted. If this assumption ceases to be true, this method will fail.

hasConnections()
returns true if there are any [Connection](Connection.md)s in the tree. Used to determine whether "unlink" buttons should be active.

hasEmptyNodes()
returns true if the current tree has any [empty nodes](EmptyNode.md) in it. Used to determine whether the Label button should be active.

inTreeElements
Returns the list of active [elements](ModelElement.md) Note that this includes all elements in the Model, not only those connected to the tree.

outOfTreeElements()
Return the list of [elements](ModelElement.md) not in the tree, that is, the elements still in the left-hand column. This list will include the "immortal empty node", or at least it should, MUST CHECK THIS

### housekeeping ###
public boolean isElementsInTree()
(sic) Returns true if there are any [ModelElements](ModelElements.md) in the tree.

setElementsInTree(boolean elementsInTree)
Register [element](ModelElement.md) as "active" (true) or "inactive" (false). [EmptyNode](EmptyNode.md)s should never be inactive - if they exist, they are active, except for the "ImmortalEmptyNode".

isEmptyNodesInTree()
Returns true if the tree contains any [EmptyNode](EmptyNode.md)s. Called to see if "Label" button should be active.

setEmptyNodesInTree(boolean emptyNodesInTree)

isConnectionsInTree()
Returns true if there are any [Connections](Connections.md) in the tree. Checked to determine whether "Unlink" should be active.
setConnectionsInTree(boolean connectionsInTree)


### load ###

loadTree()  - Takes a String (provided by the perl page which starts the applet) and parses it to construct the student's saved tree. Data comes in as :-delimited String, so parsing is straightforward. ModelElements are handled by the three methods loadOrganismNode, loadEmptyNode, and loadConnection.

loadOrganismNode() - Receives a substring from loadTree, parses it as an [OrganismNode](OrganismNode.md)

loadEmptyNode() - Receives a substring from loadTree, parses it as an [EmptyNode](EmptyNode.md)

loadConnection() - Receives a substring from loadTree, parses it as a [Connection](Connection.md)

printConnections()
Prints out a list of all connections for each element in the model. Not currently used, but retained for testing purposes.

exportTree()
Creates and returns a String representing the tree, by iterating the elements of the model and calling the [dump()](ModelElement.md) method of each. The list of elements is obtained by calling inTreeElements in this class.

checkElementIntegrity()
Reviews the list of elements and returns a warning if any ID number is duplicated or if any element is connected to an element which doesn't exist.
Useful for testing, but sucks cycles. Don't call in production!