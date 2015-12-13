Extends: [Command](Command.md)

Drag is the Command object corresponding to a move of a node. It is triggered when the user drags an object within the applet window (but not into the left-hand column, where the object will be deleted). Adding an object by dragging it into the applet is not considered a Drag command, it is stored as an [Add](Add.md).

Executing and undoing the Drag command are both accomplished by setting the node's anchorPoint to the correct value.