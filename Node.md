# Introduction #

Node is the superclass for objects which serve as connecting points in a TBS tree; it is instantiated by [Organism Nodes](OrganismNode.md) and [Empty Nodes](EmptyNode.md), also called "branch points" in the applet.

The methods in Node.java are primarily utility code, and mostly self-explanatory. These notes will focus more on  the purpose than on the means by which these methods do their work; as such, many methods will be omitted. See javadoc [YET](NOT.md) for more complete information.

isInTree() returns a boolean if the Node in question is "active"; an Organism Node is "active" when it has been dragged into the tree area. Empty Nodes, except for the "Immortal Empty Node" are active if (and only if) they exist. The same is true of Connections. When an Organism Node is inactive, it should refuse connections and cannot be selected (see [controller](TBSController.md) for more on this). Inactive nodes will not be included in the output of [TBSModel.exportTree()](TBSModel.md).

The "beingDragged()" methods are used by TBSController and StudentController to keep track of which node is being moved at a given moment.

dump() creates the substring representing this Node in the tree; this is called by [TBSModel.exportTree()](TBSModel.md).

collidesWith(ModelElement m) - this does not address collisions with Connections. If intersections with collisions are to be handled, this will have to be addressed.

contains() - self-explanatory.

---

The connection-handling methods are a little bit brittle, and possibly a little bit broken. Handle with care, but be aware the they probably need to be fixed.