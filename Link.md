Extends: [Command](Command.md)

Link is the [Command](Command.md) object corresponding to the Link button, which makes a [Connection](Connection.md) between two [Node](Node.md)s.

The execute method is empty (done elsewhere - track this)

Since connections in the TBS Applet are stored as bi-directional (each connection is a "to connection" in the origin node and a "from connection" in the target node) Link's undo method must delete both ends of the connection.