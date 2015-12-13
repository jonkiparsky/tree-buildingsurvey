# Introduction #
Inherits from [ModelElement.java](ModelElement.md)
Connection represents the link between two nodes.

# Method Details #

getTo()
getFrom()
Getters returning the Node at either end of this connection.

hasNode(Node n)
returns true if n is either end of this connection.

contains(x, y)
returns true if point x,y is on the line of this connection.

collidesWith(ModelElement e)
returns false; collision is not meaningful for connections in current implementation

dump()
returns a string representing this Connection; this is called by ModelElement.exportTree(). String format for a Connection is "C:id:fromNode:toNode"

isInTree()
{return true;} Connections are always in the tree, if they exist. This might change, for example, if someone wanted to delete and store a connection somewhere.

setInTree( Boolean inTree )
Empty method to satisfy abstract class [Model Element](ModelElement.md).