Extends: [Command](Command.md)

# Introduction #
Add is the [Command](Command.md) object associated with adding a branch point ([EmptyNode](EmptyNode.md)) to the model. It is triggered either by clicking within the model space while the applet is in "Add" state, or by dragging the "Immortal Empty Node" or an [organism](OrganismNode.md) into the model space.
Add's execute method simply calls the model.addNode method to register the presence of the node in the model. To undo, Add calls [ModelUtils](ModelUtils.md).removeElement()