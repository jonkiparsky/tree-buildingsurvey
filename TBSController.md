# Introduction #
The Controller handles user actions and communicates them to the Model. The abstract class TBSController.java primarily serves as a tie-in point for either the StudentController or the AdminController.


# Method Details #

Concrete methods:

elementMouseIsHoveringOver() is deceptively named: it returns the Node, not the ModelElement
which contains the point (x,y) (not necessarily the current mouse location)

indexMouseIsOver() is slightly less deceptive: it returns the index, in the current model, of the node containing point (x,y) (which again is not necessarily the current mouse location)

elementMouseIsOver() returns, as promised, the ModelElement at the current mouse location.


getButtonClicked() / setButtonClicked() - buttonClicked tracks the identity of the most recently selected button.

Abstract methods

handleMouseButtonPressed() / handleMousePressed() - definition of these methods depends on whether the user is running as a student or as an administrator. See StudentController and AdminController.