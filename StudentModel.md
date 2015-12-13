# Introduction #
The [TBSModel](TBSModel.md) class for trees under construction by a student taking the TBS survey.

The Model is responsible for creating and maintaining the state of the tree and its components, and for exporting a string representation of the tree when the student is finished constructing it.

StudentModel begins by constructing the initial state, whether this is for a new tree or for continuing work on a tree already begun. A string "tree" is passed as an initial parameter; this string is either empty or it contains the previous state of the tree. If the previous state exists, StudentModel uses TBSModel.loadTree() to determine the positions of already-placed [ModelElements](ModelElement.md) and their [Connections](Connection.md). If this string is empty, a default initial configuration is used. Available actions are set based on the presence or absence of [Nodes](Node.md) in the tree; the reflects the availability of actions by setting [buttons](TBSButtonType.md) to either active or inactive state. (See TBSButtonType and related classes for details.)

As part of maintenance of the tree, the StudentModel creates and maintains a history of actions taken by the student since session startup; all actions taken in a given session can be reversed with the Undo button. The history is maintained as a stack of Command objects. A Command, when placed on the stack, contains the instructions for undoing that command.
**note:** Undo has been a source of odd bugs, which are typically difficult to replicate and thus to diagnose. This is an area which could probably use a careful overhaul.


# Method Details #
Constructor:


History/Undo
getHistory()
setHistory(Stack`<Command>` history)
addActionToHistory(Command c)
> "History" is the stack of Command objects representing the actions taken by the student since the start of the current session. This is the central object manipulated by the Undo command. See Command and related classes for details.
RemoveActionFromHistory()
> > pops the most recent action from the stack. Histotry is a pure stack, there are no "cheater" methods for accessing items below the top. This is critical for maintaining state.


Prompts/user help and interaction
["Prompts"](Prompt.md) are objects used for displaying messages and getting input from the user. These are a home-brewed analog to the collection of J classes offered by the Swing package. They are used primarily due to odd failures of the Swing classses on various platforms, primarily MacOS. In addition, they are better suited to the TBS purposes and contribute nicely to the look and feel of the applet.

viewPrompt(OpenQuestionButtonType currentQuestion)

> (details here)
viewPrompt(TBSButtonType button)
> (details here)
helpUser()
> (set up help prompt - details [here](HelpPrompt.md))



buttons
The TBS button classes are again custom-built for this application. Rather than instantiating a number of independent objects, the button bar is a region of the screen with buttons painted on it. Rather than making use of the listener classes, simple cartesian geometry locates mouse clicks and passes the information to the correct methods.
See TBSButtonType and related classes for further information.

getButtonStates()
> Returns the collection of button states.
isButtonActive(TBSButtonType button)
> returns state of an individual button.

Housekeeping

icompletedItems()
unusedOrganisms()
surveyStatus()
> These methods are used in closing the tree. They would be useful in setting a "completion" requirement on students. Current implementation merely warns students that questions are unanswered or organisms unused; changing this to deny assignment credit if tree is not completed would be trivial.



StudentControllerTest
getStudentControllerTest()
setStudentControllerTest()
> These relate to the auto-testing mechanism partly implemented to exercise the GUI. This was an interesting venture which has not been developed sufficiently to return usable results.