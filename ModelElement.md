# Summary #

Superclass of [Nodes](Node.md)([organisms](OrganismNode.md) and [empty nodes](EmptyNode.md)) and [connections](Connection.md). ModelElement comprises all of the objects which may participate in a student-constructed tree.

collidesWith(ModelElement e) must return true if this element overlaps with e.

contains(Point p) must return true if p lies within this element's boundaries.

dump() must return a string representing the vital attributes of a particular ModelElement. Implementation varies, see particular classes, but generally is of the form 'TYPE:ID#:NAME:X:Y:IN\_TREE:To\_Connections:From\_Connections', where IN\_TREE is a boolean indicating whether this element is "active" and "To\_Connections" and "From\_Connections" are lists of nodes which this node connects to and which connect to this node. Connections are a special case, and have exactly one of each sort of connection and no location. "IN\_TREE" is only relevant for Organism Nodes, and may be factored out of this string, as we no longer dump inactive nodes. This factoring would break backward compatibility of saved trees.


Equals(Object o) reliably returns the correct results.

See instantiating classes for particular details.