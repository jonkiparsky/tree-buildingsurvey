# Meeting 4 - 11/5/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld, Professor Bolker

News:
> Code has been cleaned up a little bit with the addition of Glenn's TBSGraphics class maintaining the bulk of the static values used in the applet.  There is currently one week before the venture capitalist presentations (11/12).  The UI meeting with Professor White has been postponed until next tuesday (11/10).
Discussion:
> We decided to break this meeting up into two parts:
  1. Venture Capitalist Presentation prep.
  1. New Code Enhancements
> We began with the code enhancements and who should do which work. One of the goals of our presentation would be a working version of our applet with a good amount of the functionality working.  One of the main pieces of functionality that we wouyld like to have working would eb the connections (directed and undirected).  Here was the way the work was broken up for this function:
  * Jon: back-end data structure that stores the information about which nodes each node are connected to, as well as selectability of nodes
  * Andrew: metod that takes in two nodes as input(source and destination nodes) and returns two points which would make up the starting and ending bounds of the connection line
  * Glenn: rendering of the connections
> It was at this point in time that we felt that we had enough to go on coding-wise until the next meeting so we shifted the subject over to the presentation.  Jon, started the discussion by proposing that our presetation should have the following parts(in this order):
  * Original project idea(Professor White's code)
  * Our current implementation and what we plan to add to it
  * Extensions of our project for future use
    * Financial ramifications of our work
> Professor Bolker came over to join us right after we had agreed to Jon's proposal. Some of Professor Bolker's keypoints for our presentation were:
  * Make it a point to mention the size ofour group compared to the others
  * Our project has a deliverable st for early ebruary/late january
  * Information on the financial secs of our project are nice, but not required
  * Be respectful about the job that Professor White had done on the old code
  * 3 main questions to answer:
    1. What project we're doing
    1. Why its worth doing
    1. Why we are able to do it
> After he mentioned these points we went off on a tangent discussing anything from scheduling to the business requirements document.  We explained to the Professor Bolker that it is tough for us to have a concrete schedule of code development because we usually go off and code up what we discuss each meeting, but we don't what we are going to discuss until the meeting so its tought to predict.  Professor Bolker came up with the solution of just displaying major milestones and adding smaller developments as they are achieved.  Some of the major milestones are:
  * Multiple GUI's
  * Links (Which we were going to finish this weekend)
  * Database connectivity
  * Structure to use when saving information on user's tree
  * Scoring algorithm
> Once Professor Bolker listed off these major milestones we started to discuss how we would go about making some of these. Here was our idea for selection of multiple GUIs:
![http://upload.wikimedia.org/wikipedia/commons/4/48/GUIVersionSelection.gif](http://upload.wikimedia.org/wikipedia/commons/4/48/GUIVersionSelection.gif)
> We then started to discuss the structure that would store information on a user's tree.  Jon had suggested using a semicolon delimited text file.  At this point we decided that we had gone a little off topic and thought it would be best if scheduled a meeting for this weekend for work on the presentation.
**Future Meetings:**
  * Sunday(11/8) Somerville(Jon's apartment) 3pm
  * Tuesday(11/10) during class