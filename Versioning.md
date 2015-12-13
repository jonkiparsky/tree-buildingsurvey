# Applet Versions (proposed due date) #

Dates in **bold** are firm, and scheduled, with features locked and tasks assigned. Plain text dates and features are proposed, but have not been specified suffiently to be firm. Features may float.

  * **0.1**: Display Organism Images and Text  (**Done**)
    * Read flat file and .GIF images from server side
    * No Model and Controller implementation
  * **0.2**: Draggable Organisms (**Done**)
    * See [meeting minutes](MeetingOneMinutes.md) for detail of model-view-controller division of labor. This version allows the users to stack organisms on top of each other and drag multiple organisms at once.  The alphabetically later organism will be rendered on top.  We plan to add collision detection by Version 1.0.
  * **0.3** 11/01 (**Done**) (see it [here](http://www.cs.umb.edu/~kiparsky/TBS03/Applet03.html))
    * Distinguish between available organisms (on the left) and organisms nodes (on the right)
    * add button panel: there is a button panel, although the buttons are not yet functional. See notes to [r307](https://code.google.com/p/tree-buildingsurvey/source/detail?r=307) for more on the button panel status. Input is requested on how to proceed.
    * Empty nodes can be created
      * They are not pretty, we are aware of that. This is functionality, not pretty.
      * Two methods of node creation have been implemented: the user can drag an empty node from the "bottomless stack" on the left side of the screen, or she may double-click. A conflict between the two methods has been reconciled. There is also a space in the button panel for "label" which is B. White's term for "create an empty node". How many creation methods do we want to use?
    * Nodes can be deleted: This also is implemented in two distinct ways. Currently, double-click will ask the user if they want to delete; also, simply dragging a node off-screen will delete it.
    * Empty nodes can be labeled: Double-click node creation offers the user the chance to label the node.
    * In addition, we hope that MacOS X compatibility problems have been resolved, at least for the moment.
    * If we may toot our collective horn, this release puts us fairly well ahead of schedule: we are 3 minor versions and 1.5 weeks ahead of where we thought we'd be a week ago.
    * This however requires that we re-group and determine the feature path from here before any further development: no new code until after meeting on Tuesday. Following release dates are now obsolete; look for new information Tuesday after class.
  * **0.4**: Create & Delete Connections (**DONE, 11/09**)(see it [here](http://www.cs.umb.edu/~kiparsky/TBS041/Applet04.html))
    * Connections can be created. Deleting a node deletes connections to/from node

  * **0.5**: Connections selectable, can be deleted. Some buttons functional: link, unlink, remove, rename. (**11/12**)
  * **0.6**: Fix mouse semantics of connection. Must determine what they are before fixing! Still room for other issues in this release.(11/15?)
  * **1.0**: Menu allows users to select options for UI display and mouse behavior. Trees can be saved to database and loaded from database. Responsibility areas: Multi-GUI - Glenn / Save structure - Jon / Database connection - Andrew (12/01)
  * **1.1**: Menu allows users to select options mouse behavior. Trees can be saved to database and loaded from database. Open-response questions have been moved from html display to the applet.(12/14)
  * **1.2**: Contains all of the functionality from previous versions. Also, tooltips to in-tree organism nodes, non-alphanumeric character checks in node labeling, information display when user clicks a button, graying-out and mouse cursor updates when user hovers over active & inactive buttons.(1/3)
  * **1.3**: Contains all of the functionality from previous versions. Also:(1/12)
    * First version of the Admin applet/website has been released.
    * Redesign of the website
    * Yes/No, Instroduction, & Help prompts
    * Print function
    * Cursor functionality for "Label" action

  * 1.x**: "Scoring" (graph analysis) enabled,  pop-up help (tooltip and help window), undo, front screen ("Hello, please select your name from this list"), back screen (multiple-choice and free-entry tests). (1/31/10)**These will be divided into smaller iterations by 12/01). 1/31 is release date for the version to be tested on the spring biology class


Some features for forthcoming iterations. No particular order. These to follow the now long-delayed 0.5

  * solidify UI. Explicit, written expectations of mouse and button behavior. Susceptible to change, but changes to behavior must follow changes to design, not vice versa. This may or may not include determining the behavior in [issue 16](https://code.google.com/p/tree-buildingsurvey/issues/detail?id=16).
  * status line (I see it's already in the code, we might as well schedule it for deployment, so it's not so obvious that we're hacking away at this at random without a schedule or a plan)
  * minimize oNodes to icon only
  * rework emptyNode labels
  * [issue 21](https://code.google.com/p/tree-buildingsurvey/issues/detail?id=21)
  * [issue 19](https://code.google.com/p/tree-buildingsurvey/issues/detail?id=19)