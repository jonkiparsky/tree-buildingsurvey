# Meeting 8 - 12/10/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld

News:
  * The Business Requirements Document is due in next tuesday (12/15).  Currently, there is a version detailing the use-cases involved in the current version of the applet in Jon's possesion.
  * There is now a version of the applet that has a database connection for saving a tree and answers to open response questions on Professor White's server, [Current TBS Site](http://cluster.bio.whe.umb.edu/cgi-bin/Test/TBSTestSurvey.pl)

Discussion:

**Eliminate Web Form Scrolling**
> This meeting began by discussing the recent developments Andrew made to the website where saving is possible.  Our end goal was to move the open-response questions from the HTML form to the Applet.  The reasoning behind this move is that when a user scrolls down to enter answers to the open-response questions it tends mess with the rendering of the applet.  We don't have a solution for this other than moving the questions to the applet as well as the instructions on how to use the applet so that the only things inlcuded in the HTML form are information on who is logged in, the applet, & the button to "submit" the tree.  If these things are accomplished there will not be enough information on the form to allow for scrolling.
> One worry about moving the open-response questions to the applet would be verifying that the user has answered them before clicking the "submit" button.  Andrew, managed to come up with a solution to this problem using javascript.  Here it is:
  1. Placing getters within the Applet class that retrieve the user's responses to the open-response questions so they can be called upon from javascript
  1. When the user clicks the "submit" button call a javascript function
  1. The javascript function calls the getters in the Applet class and verifies each of the user's responses
  1. If the responses pass the verification then form is submitted to the finished survey screen, otherwise an alert message describing which questions still need answering is displayed on the screen
  1. This behavior will continue to happen until the user finshes all the questions

> _The only catch to this behavior, which was the same in Professor White's orignal version, is that when the user exits the form without clicking the "submit" button nothing will be saved to the database._

**Future Features**
> The next discussion was on what features we would possibly like to incorporate into the project before the Late January/Early February deliverable.  Here was the list that we came up with:
  1. Creating Splash Instructions to be displayed in the Applet by use of a "Help" button
  1. Placing open-response questions within the Applet
  1. Consulting Professor White when creating Applet instructions
  1. Status message display upon certain tree-creation-actions
  1. Adding label to empty node in reservoir
  1. Adding a minimum width to organism nodes once they are dragged into the tree
    * Human node in particular has a small image so it is hard to click on once the text is hidden
  1. Adding tooltips to organism nodes dragged into tree for name display
  1. Labeling of empty nodes
    * Cursor usage when labeling empty nodes to mimic behavior of text input fields
    * Restriction of Alpha-numeric characters
  1. Gray-out buttons that cannot be used in current state of tree
    * _Unlink cannot be used when there are no connections present_
  1. Tree scoring based Professor White's original scoring rubric

> We next discussed some things that will need Professor White's collaboration.  These will may not be instuted in time for our deliverable:
  1. Which buttons will be considered modes(ex: link button stay selected until user clicks empty space)
  1. Accurate name for empty node (suggestions: editable, free, generic, anonymous, labelable, blank)
    * _One suggestion for this question is that whichever name we decide upon it should be related to the name of the button for labeling.  So, "labelable" would go with what we are using now "label", but if we used "editable" the button would be changed to "edit"_
    * We would also like to add when user hovers over organism node in "label" mode it will change the mouse cursor to an 'X' or a red circle with a line through it.


> We ended off the meeting with Jon volunteering to add an introduction/narrative on our project as well as information on these future features to the current version of the requirements document.  We also promised to submit our ranking of how hard the design of these features so that Jon could incorporate this information into the requirements document as well.
> Andrew volunteered to add the open-response questions to the applet using the new code that Glenn had added for displaying a question in the applet.

**Future Meetings:**
  * TBA
