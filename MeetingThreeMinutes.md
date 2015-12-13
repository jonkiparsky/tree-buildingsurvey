# Meeting 3 - 11/3/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld, Professor Bolker

News:
> Version 0.3 of applet, including the first version of the button panel, was completed yesterday.  The first version of collision control for node dragging has been implemented but not yet deployed.  A finalized build script incorporated source packaging and image inclusion has been completed and implemented.
Discussion:
> The meeting was originally supposed to begin in Professor White's office in Wheatley, but unfortunately there was a confusion on the date of the meeting.  The meeting was meant for an important requirements discussion.  We will regroup with Professor White to check if the meeting time is actually tomorrow(11/4) or try to reschedule for thursday(11/5).
> So we began our meeting in class by discussing the current progress of our group with Professor Bolker. Here is list of some of the things we discussed:
  * Connections
    * Deletion of a connection should be done by selecting a connection by clicking on it and either clicking the "Delete/Remove" button or hitting the Delete button on the keyboard
    * Connections should have the ability to go through nodes with the display showing the connection on top of the node
    * Creation of a connection should be done in 3 steps(the first 2 are interchangable):
      1. Select node
      1. Click "Link" button(a line with an arrow at the end of it will display beginning at the selected node and ending at the current position of the mouse)
      1. Select another node(the line coming from the mouse will become stationary ending at the node recently selected)
![http://upload.wikimedia.org/wikipedia/commons/4/46/ConnectionCreation.gif](http://upload.wikimedia.org/wikipedia/commons/4/46/ConnectionCreation.gif)
  * Editing text of nodes should be done by double-clicking a node and typing text(possibly within a text box)
> ![http://upload.wikimedia.org/wikipedia/commons/e/e2/EditingNodeText.gif](http://upload.wikimedia.org/wikipedia/commons/e/e2/EditingNodeText.gif)
  * Format of meeting with Professor White(Client)
    * The group should take control of the meeting by describing what we are building.  While describing our product Professor White can step in at anytime and inform us of incorrect specifications.
    * The main question that needs to be answered in these early meeting is, "What requirements must be completed in time for the Spring semester?"
    * The question that can be saved for later meetings is, "What requirements need to be met for end of the spring semester?"
    * Be sure to ask the question, "Should new nodes, be called 'nodes'?" This is a tough question to answer because the new name could be too leading.
> After the conclusion of our discussion of the meeting with Professor Bolker we began to discuss what needs to be completed before our next meeting(either tomorrow with Professor White or during class thursday).  This time the group decided to focus more on "housekeeping" aspects of the code rather than new development.  Here was the division of tasks:
  * Jon
    * Revert double-click deletion of nodes
    * Adding Javadoc statements to code
  * Glenn
    * Creating a config class to store all of the hard-coded final properties used in the code
    * Refactoring overly complex classes/methods
  * Andrew
    * Adding javadoc creation to build script
    * Updating collision detection code

> We ended our meeting with a discussion of one of the features that Jon had managed to code up for the latest version of the applet, the button panel.  Jon had left and open-ended design decision of two possible options for representing the button panel:
  1. Making the button panel simply behave like nodes, but instead of being dragable make them activate specific actions
  1. Using stored swing components
> We decided the going the first route, not only because it was the one currently implemented, was the more simplistic and efficient approach towards coding the button panel.

Future Meetings:
  * Wednesday (11/4/2009) with Professor White for discussion of UI's (Andrew may not be present due to work constraints)
  * Thursday (11/5/2009) during class