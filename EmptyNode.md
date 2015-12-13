# Introduction #
Inherits from [Node.java](Node.md) [ModelElement.java](ModelElement.md)

Empty Nodes (labelled "Branch Point" when running the applet) are user-created junction points used in creating the tree structure. The correct understanding of these objects is probably the most important factor in a student's success in building a tree.

Empty Nodes differ from [Organism Nodes](OrganismNode.md) in that they can be created and  labelled by the student. They also have a generic appearance, rather than a distinct image file.

# Method Details #

getHeight()
getWidth()
Get the dimensions of this EmptyNode, for rendering. Height does not change, but width is changed as text is entered.
setAlteredWidth() is called to inform the Empty Node of its new horizontal dimension when it has been labelled.

isBeingLabeled()
setBeingLabeled(boolean beingLabeled)
Used by the [Controller](StudentController.md) to manage the act of labelling this node.
(is that right? Controller?)

toString()
Returns a string for this node, either "Branch Node" or name string + "Node"

convertToVertex()
Turn this node into a Vertex. (must track where this is used)