Extends: [Command](Command.md)


Unlink is the Command object corresponding to the wholesale deletion of the conections that a selected node is participating in. This command stores a LinkedList of connecions that are deleted, and uses this list to restore all connections when Undo is called.