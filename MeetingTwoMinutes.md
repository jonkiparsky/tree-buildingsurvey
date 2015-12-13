# Meting 2 - 10/29/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld, Professor Bolker

News:
> Version 0.2 of applet, including the dragability functionality, was completed last night.
Discussion:
> We began our meeting by discussing the current progress of our group with Professor Bolker. Here is list of some of the things we discussed:
  * Bug: Version 0.2 applet is currently not working on macs.
  * Bug: .jar packaging is not correct
  * Division Of Labor:
    * Professor Bolker believed that our current group dynamic was that Glenn was writing code, Jon was managing the direction of the group and Andrew was handling the administrative duties.  But we convinced him that those assumptions were correct, but that everyone has been writing code.
  * SVN issues:
    * Code being written in parallel and creating conflicting check-ins
    * Glenn was having some trouble understanding some of the nuances of SVN and accidentally deleted some work that Jon had committed
  * How to handle node collisions after a user drag and drop?
  * Discussed the possibility of when a user selects an organism that has connections and drags it back over to the unused organisms section it will delete those connections from the display
  * **We decided that we should meet with Professor White to discuss UI options Thursday, November 5th** (Jon would handle coordination of scheduling the meeting with Professor White)
  * Professor Bolker wanted to get a rough idea of when we could get multiple UI's(applets) to be displayed on one webpage
    * Unfortunately we did not have a definitive timeframe for when this would be completed
  * Making basic functions of the applet perform in the smae ways as Microsoft Word or some other commonly used software
    * EX: editing text of node should be done by double-clicking it and typing text
  * **Hallway Usability Testing**, Professor Bolker was hoping that once we had an applet with a sufficient amount of working functionality, we could maybe shop it around to friends and colleagues for testing purposes
  * Professor Bolker suggessted adding basic menu functions (Save, Delete, etc.) as soon as possible so it can be easily reused in future versions of the applet
  * Jon inquired about possiblity have a class dedicated to the discussion of licensing, what it is and how to choose the right one?

After we finished our discussion with Professor Bolker we had a long talk about how SVN check-ins should be performed.  We came to the decision that deletion of any .java files is a NO-NO and also that checking-in build folders is extraneous and just creates confusing updates when checking the team synchronization in Eclipse.

We then went through all of the check-ins that had happened on SVN over the past night.  We analyzed Jon's code that had been checked in and accidentally deleted by Glenn.  It was agreed upon that some of Jon's code was very useful and Glenn volunteered to go through and add these facets of Jon's code back into the project.

Next we talked about how and where we should house our applets in a common location for viewing on the web(hopefully eliminating the possibility of differences in what is displayed by different links on the google code site).  Andrew volunteered to try and set something up for this.  Andrew also went on to propose the possibility of using full URL's for image location.  This would eliminate the problems of locating the folder holding the images in comparision to where the .jar file is located.  Andrew will look into this over the weekend as packaging of .java files since if this update URLs works then packaging should be possible.

We ended the meeting by discussing some of our ideas on new functions to be added(we don't know whether these are possible or not yet) to the applet:
  * Node/Organism Collision Handling
![http://upload.wikimedia.org/wikipedia/commons/2/25/NodeCollision.gif](http://upload.wikimedia.org/wikipedia/commons/2/25/NodeCollision.gif)
  * Functionality of connections and region selection
![http://upload.wikimedia.org/wikipedia/commons/b/b9/ConnectionFunctionality.gif](http://upload.wikimedia.org/wikipedia/commons/b/b9/ConnectionFunctionality.gif)

Future Meetings:
  * Tuesday (11/3/2009) during class
  * Thursday (11/5/2009) with Professor White for discussion of UI's