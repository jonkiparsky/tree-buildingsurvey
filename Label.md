to do: finish methods

Extends: [Command](Command.md)

A Label command sets the text string within a branch point ([EmptyNode](EmptyNode.md)) to a new value. The Label Command object stores the previous value, in case the student wants to restore it.


# Methods #
Execution of a Label command involves simply setting the [EmptyNode](EmptyNode.md)'s Name field to the new value, and adjusting its width accordingly. (Possible refactor: setting name should set width)
Undoing a Label command is the same process, except that the Name field is set to the former value, rather than the new one.

getLabelAfter
setLabelAfter