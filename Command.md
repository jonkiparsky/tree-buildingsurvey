[Table of Contents](DevGuideTOC.md)


# Introduction #

The TBS Applet maintains a record of the actions taken by a student in the course of a session. This record is saved in the form of a Stack of Command objects in the [StudentModel](StudentModel.md). Command objects, objects implementing the Command interface, represent actions which change the Model's state. Each Command object must implement an "undo" method and store any data needed to restore the model to the preceding state. Commands can only be undone in the reverse of the sequence in which they were applied, hence the Stack is the appropriate structure for storing this history. The TBSApplet does not inplement a "redo" feature; once a command is undone, no record is kept of it.

This command history allows the student to undo actions back to the start of the session. Note that history is not saved between sessions, this has two consequences. The first is that a student cannot undo actions taken in a previous session; if the student leaves the applet and returns to continue building their model, they can only undo actions taken since the most recent startup of the applet. The other consequence is that the professor cannot review the sequence of actions taken by the student in building the tree.
Saving the sequence of actions taken across sessions would be possible with some work. This would, at a guess, increase the disk usage of each student by a factor of not more than ten.

**History/undo bugs may still be present, and are very difficult to trace and repair. This area would be a very good place to focus attention if improvements to the applet are contemplated. Unit testing would be useful here.**

# Details #

The abstract class Command requires that its extending classes contain instructions for executing and undoing a command, and that they override the toString method with a reasonable label for the command.

Extending classes - commands in the TBS Applet - are:
[Add](Add.md), [Delete](Delete.md), [Drag](Drag.md), [Label](Label.md), [Link](Link.md), and [Unlink](Unlink.md)

Notice that a command need not be triggered by a button press ([Drag](Drag.md)) and that not all button actions are Commands ([Undo](Undo.md), for example, cannot be undone)