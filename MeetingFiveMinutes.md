# Meeting 5 - 11/8/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld

Discussion:
> This meeting m arked the first time that our group had met outside of class.  We decided to go to Jon's place in Somerville. We began the meeting by discussing what features need to be working on the applet in time for thursday's venture capitalist demo:
  1. Buttons functioning
  1. Selectable connections
  1. Borders around node when selected
  1. Thickness of connections
  1. Styling empty nodes to look more like organism nodes
    * Move the label from on top to inside the node
> While we were going through the functions that needed to be completed for thursday we came across a bug:
  * When a user drags an organism off the screen and it readjusts to put it back on the screen and then does it again with a different organism in the same spot the collision control code does not function
> After we found this bug we came up with an interesting idea to save tree building space:
  * What if when the organism nodes were dragged into the tree the label would hide and act as a tooltip that would display when the user hover the mouse over it?
![http://upload.wikimedia.org/wikipedia/commons/6/6b/HiddenText.gif](http://upload.wikimedia.org/wikipedia/commons/6/6b/HiddenText.gif)
> We decided this is a good question to ask Professor White during our GUI discussion on tuesday.  Next we discussed how the duties of the presentation will be broken up:
  * Glenn: handle the demo of the current applet and explanation of implementation
  * Jon: ideas on extensions & cost
    * We calculated that each of us spend about 20-30 hours/week on this project, which is the equivalent of 1.5 programmers
  * Andrew: Summarize the [experiment .pdf file](TBSExperiment.md) that Professor White gave us afew days ago and how we wnated to turn this into an application
    * Also, be sure to mention the size of our group and project and how the project came about
> We concluded the meeting by going trhough each of the milestones discussed in the [last meeting](MeetingFourMinutes.md) and adding them to the [schedule](Schedule.md).  We also came up with few new ones that we thought were important.

> Those new milestones were: providing a "greeting page" for students to sign in (presumably with the instructions for the game), coding up the post-test (multiple-choice and free-entry answers, as in the original applet); and implementing "undo" - a mighty challenge, that.

**Future Meetings:**
  * Tuesday(11/10) w/ Professor White to discuss GUIs