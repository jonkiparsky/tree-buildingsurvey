# Introduction #
Represents a vertex in the analysis phase.



# Details #
# Methods #

Constructor: The constructor for Vertex is trivial, assigning the required parameters to the appropriate fields and declaring the ArrayLists required for tracking relationships.


## Parent/Child Relationships ##
Immediate, directed relationships
addFrom(), addTo(): add a node to the vertex's "From" or "To" connections list
getFrom, getTo(): return the "From" or "To" connections list
getToVertices(), getFromVertices(): duplicates above. **Why**?

## Ancestor/Descendant Relationships ##
Tracking directed relationships in the graph. Distinct from parent/child in that parent/child is immediate, these include any Vertex above or below this one. Limited application, so far.

ancestorOf(): returns true if this is an anscestor of that
descendantOf(): returns true if this is a descendant of that
addAncestors(): adds the list to the ancestors list
addDescendants(): adds the list to the descendants list
getAncestors(), getDescnedants(): return the list of ancestors/descendants

getAdjVertices(): return all vertices directly connected to this
direction(): part of the attempt to determine graph's "direction" - returns positive value if it is connected From, negative if it is connected To

## Cycle Detection ##
Cycle detection is done by a colored depth-first search. All nodes are initially marked white. When a node is encountered, it is marked grey, and when its descendants are completely visited, it is marked black. If we come across a grey node, then we know there is a cycle.

enum Mark: Mark has values WHITE, GREY, BLACK. These indicate respectively that the vertex is unvisited, that it has being examined for cycles, or that it is clear of cycles. See [Graph](Graph.md).cycles

setMark(): set the mark for this vertex
getMark(): return the mark
isTerminal(): return true if this node is terminal

## Error Handling ##
If this vertex has an error, it is highlighted in red. These methods handle that functionality.

setError() : sets error (a boolean flag)
hasError() : returns the value of error


## Rendering ##
render(): selects either renderVertex() or renderVertexWithImage(), depending on whether the Vertex's getImage returns an image or a null.
renderVertex(): draws a simple box suitable to an imageless node. Called by render()
renderVertexWithImage(): draws a simple box with an image in it. called by render()
renderError() : Draw a red box around the vertex if error is set. Called by render()


### Rendering Utility Methods ###

> getUpperLeft() - returns upper left point
> getLowerRight() - returns lower right point by calling getVertexBounds and calculating
> getVertexBounds() - the getVertexBounds() method is overloaded. **at this writing, I believe that the no-parameter call is used within Vertex, and the parameter-rich version is called from other classes, but I don't know if that's correct.**


## Housekeeping ##
toString() String consists of name, whether there is an image, location coordinates.

Following getters and setters are standard, nothing remarkable to note.

  * etName()
  * asName()
  * etType()
  * etInfo()
  * etIndex()
  * etIndex()
  * etChildren()
  * etParents()