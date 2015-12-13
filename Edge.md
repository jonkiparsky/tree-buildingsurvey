# Introduction #

Represents an edge of the student's tree, or graph.

# Methods #

render(): draw this edge
getConnectionBounds(): determine the endpoints of the line representing this edge. Similar to ().getConnectionBounds()

getArrowHead(): draw the arrowhead indicating direction of this edge. Similar to ().getArrowHead()

dx(), dy(): return the change in x or y of this line.

vertexSides(): calculate the sides of a Vertex (why is this not a method of Vertex?)

getIntersectionPoint(): calculate point of intersection of two lines, return null if parallel or identical.

toString: return descriptive string for this Edge.