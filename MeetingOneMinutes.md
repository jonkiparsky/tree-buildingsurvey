# Meeting 1 - 10/27/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld

News:
> Andrew restored Professor White's server earlier today after crashing it back on Friday, October 23, 2009.
Discussion:
> The main topic of discussion was the Version 0.2 deliverable for Friday, October 30, 2009.  We began this discussion by going over the current code architecture that was in place.  There is four main components to the code:
  * Applet - Sets up applet object
  * View - Controls the rendering of the images in the applet
  * Model - Houses the information needed to construct the components used by all the other clases (`ModelElements`)
    * `Node` - This object will eventually house the nodes created by the user
    * `OrganismNode` - This is an extension of the Node object but with images & labels
  * Controller - This class handles the actions (button clicks) and mouse controls

> The main feature of the version would be that the organisms would be dragable(The user can drag and drop).
> Before we could start discussing the dragability of the organisms we needed to organize how these organisms would be represented in the Model.  Here are some of the changes that will need to take place:
  * There will be a `TreeMap` containing `ModelElements`(`Nodes`, `OrganismNodes`) that will be the main storage of the information being displayed
  * `OrganismNodes` will have a boolean property describing whether it is _used_ or _unused_
  * `OrganismNodes` will store it orignal positioning in the unused list and used position after the user has dragged it somewhere
  * Default organism positions will be created during loading of images from list.txt(possibly database table later on)
  * We will leave the unused list in order of how the images are pulled (usually alphabetical) and would revisit coding a control to make sure the images are alphabetically ordered

> Next, changes to the controller code to accomodate dragging were discussed:
  * `mouseClicked()`, `mousedDragged()`, `mouseReleased()` methods will need to be updated to record the x,y coordinates of the mouse and apply them to the correct organism node in the Model's `TreeMap` object.
  * updating the `collidesWith(Node n)` method in the Model class to actually calculate whether the position the user has dragged an organism is already occupied by another organism
  * when a user does drag an organism to a position already occupied the organism will default back to its last position (this will eventually be replaced with an algorithm to determine the closest usable position to where the user wants the organism to go)

> The last functionality that was discussed was the button panel contained at the top of the applet which houses the actions (link, unlink, label, delete, split, print, & undo).  We decided that it would be a good idea to create the rendering of these buttons, but leaving their actions for a future versioning.

> The code was assigned as follows:
  * Model - Glenn
  * Controller - Andrew
  * Button Panel - Jon

> Here is a visual representation of how some of the features were assigned for Friday's versioning of the applet:
![http://upload.wikimedia.org/wikipedia/commons/e/e1/Meeting1.gif](http://upload.wikimedia.org/wikipedia/commons/e/e1/Meeting1.gif)

> We ended the meeting by agreeing that for future meetings we would like to possibly have the coding assignments completed two versions in the future as to eliminate the need for emergency meetings.
**Future Meetings:** Thursday(10/29) (Class) & possibly Saturday(10/31) or Sunday(11/1) afternoon.


Well done, Andrew, I think you've caught it quite well.
Some further thoughts - l'esprit d'escalier - that struck me after leaving:

- The eventual means of loading the organisms will depend on how complicated the set of test organisms will be. If client wants an extended set of organisms and the option to define different sets for different purposes (ie, do different samples of organisms affect performance) or wants to store complex data about the organisms, then db might be appropriate. If they remain as a constanst set of 20, then a flat file will do nicely.
(Come to think about it, even if the set increases, a flat file and a set of load lists would do the job nicely, in true unix style, and would in fact be the easiest interface, much easier than some maintenance panel)

- Andrew, you might give any thought to polling the treemap and asking the model elements whether they've been clicked in? Easy enough to put an "isWithin()" method in the model, then you could just run down the list until you get a true or run out of list. Just a thought that whacked me about the head, as they will. It would save the bother of keeping up a lookup table, and as we know lazy is often a good thing.
Well enough, off to work.