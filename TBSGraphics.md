# Introduction #

INCOMPLETE

TBSGraphics contains constants and static methods used for graphics throughout the [TBS Applet](TBSApplet.md). Static constants are either hard-coded or set according to environment. When constants are set elsewhere in the code, this page indicates where this occurs as well as the rationale for the constant.

Constants:
  * Applet height & width are set according to height and width parameters passed by frame page.
  * The "line of death" is the vertical line separating the inactive organisms from those in the tree. The constant LINE\_OF\_DEATH is the X coordinate of this vertical line. It is hard-coded to 180 pixels, corresponding to the width of the Horseshoe Crab node plus its label.
  * padding is a Dimension representing the standard padding, in pixels, separating objects. Currently hard-coded to 10 pixels horizontally, and 5 vertically.
  * numOfOrganisms is the number of organisms loaded into the applet by TBSApplet.loadOrganisms(). It is declared here and set when organisms are loaded.
  * organismNodeHeight and organismNodeWidth are the width of all labelled organism nodes (ie, organism nodes "at rest" in the left-hand column). These values are calculated and set by StudentView.
  * maxOrganismStringHeight and maxOrganismStringWidth are values calculated by TBSApplet.loadOrganisms().
  * maxOrganismImageHeight and maxOrganismImageWidth are values calculated by TBSApplet.loadOrganisms().
  * maxStudentNameWidth is a hard-coded constant, currently set to 150 pixels. (is there a figure in characters at any point in the code?)
  * emptyNodeLabel: the default label for EmptyNodes ("Branch Points") when they are placed in the tree.
  * emptyNodePattern & writtenResponseIllegalCharacters are used to ensure that only legal characters are accepted from the student.
  * maxLinesOfWrittenText is an arbitrary cap on allowable text entry in prompt objects.
  * emptyNodeLeftX/UpperY: EmptyNodes ("Branch Points") begin in the "inactive column"; their location is calculated based on the location of the last Organism Node. Changes in dimensions of Organism Nodes will automatically adjust starting location of Empty Node.
  * emptyNodeHeight & emptyNodeWidth: EmptyNodes ("Branch Points") are rendered as 20X20 squares when placed in the tree, until text is entered.
  * emptyNodeLabelOffset and Padding are used in rendering EmptyNodes correctly.
  * arrowLength is the initial length of arrowheads (before dragging)
  * ySpacing is the constant padding between Nodes in the left-hand column.
  * buttonsHeight & buttonsWidth
  * questionButtonsStart:
  * closeButtonStroke:
  * textHeight:

  * Color values are chosen arbitrarily, and are kept in TBSGraphics for convenience and ease of modification.
  * Font properties are defined here for consistency throughout the applet.


Static methods:
  * Comparator 

&lt;ModelElement&gt;

 : Model elements are sorted by ID number.
  * get2DStringBounds() : Returns a Dimension for the rectangle occupied by a string.
  * drawCenteredString() : calculates the correct placement for a centered string in a given frame and draws it.
  * renderButtonBackground() : Renders button background
  * breakStringByLineWidth() : Calculates line breaks, returns a list of Strings of correct length for a given line length.
  * updateBroswerSpecs() : Checks for Macintosh browsers and adjusts font accordingly.

# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages