to do: detail of undoing

Extends: Command

Delete is the Command  object associated with removing a branch point ([EmptyNode](EmptyNode.md)) or [organism](OrganismNode.md) from the model. It is triggered either by the Delete button or by dragging a node out of the model space. Delete's execute method is quite simple, but its Undo is rather more complex than Add's.

To execute a Delete command, it is only necessary to call [ModelUtils](ModelUtils.md).removeElement() on the node in question.
To undo, however, we must be very careful to restore the node completely, to its correct position and with the correct connections.