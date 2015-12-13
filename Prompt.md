# Introduction #
Prompt is the overall class of objects used for communication with the student.
Prompt stands in for a number of Swing objects (JLabel, JOptionPane, and so forth). Swing components are avoided throughout the TBSApplet due to observed incompatibilies with Java configurations on certain operating systems, most notably OS X. Prompt provides a way to display and elicit information using lower-level (and therefore more broadly compatible) methods. Being custom-built, they interact nicely with the rest of TBS and suit the look and feel of the applet.

# Extending classes #

StudentModel Prompts:
HelpPrompt, RadioQuestionPrompt, ResizeWarning, TextEntryBox, WelcomePrompt, WrittenQuestionPrompt, YesNoPrompt
AdminModelPrompts:
AnalysisPrompt, ColorEditorPrompt, RadioQuestionReviewPrompt, WrittenQuestionReviewPrompt

# Details #
Prompt is an abstract class underlying all "prompt" objects in the Applet. The source code is very informative, what follows is essentially the source with annotations. See specific Prompt objects for more detailed commentary on particular implementations.


# Fields #
> private TBSModel model;
> private Graphics2D g2;
References to the current model and graphics context.

> private Point anchorPoint;

Defines the prompt's location in the window.

> private Dimension promptSize;
Defines the Prompt's space in the window

> private Rectangle closeButton;
> private Rectangle minimizeButton;
> private Rectangle bottomButtons;

Used to represent the locations of the various buttons used by different prompts. (note the inherent limitation of this set of objects to our particular needs: to put buttons in other locations, for example, would require modifying the abstract class. This is not a general Swing-killer)

> private int buttonHeight = TBSGraphics.textHeight + TBSGraphics.padding.height;

Sets button height to the height of one row of text plus padding. See [TBSGraphics](TBSGraphics.md) for these constants.

> private int stringY = 0;



> private boolean renderMinimize;
> private boolean renderClose;

FILL IN


> private boolean finished;
Used for closing this prompt.



> private boolean minimizationInProgress = false;
> private boolean maximizationInProgress = false;

For tracking minimization and maximization state. See () for implementation.

> private int resizeIndex;
> private int xIncr, yIncr, wIncr, hIncr;


> private boolean minimizedState = false;
Prompts are not minimized when created.

> private String minimizedTitle;

FILL IN

> private boolean renderButtonsAndString;
> private boolean renderElements;

FILL IN





# Methods #

> public Prompt(boolean renderButtonsAndString, boolean renderElements, Dimension promptSize, TBSModel model)

Default constructor for Prompt objects.

Prompt.java defines accessors for the above fields. Use of "get" prefix on getters is inconsistent, but logical. See Prompt.java for method names.



setFinished(boolean finished)
Use this setter to close a Prompt. The Prompt will close itself down.

void paintComponent(Graphics2D g2)

Display instructions go here.

isOverButton(MouseEvent e)

returns true if the reported MouseEvent (a click) occurs over a button

getCursor( MouseEvent e )
returns the correct Cursor for the current state
FILL IN here


> mousePressed(MouseEvent e)
> keyPressed(KeyEvent e)
> keyTyped(KeyEvent e)

Prompts capture these Events, handling instructions go here.

calculateValues(int lineCount, int extraHeight, boolean hasBottomButtons)
Calculates values for some constants used in rendering and handling prompts, including:
promptSize, centerX, centerY, anchorPoint, stringY, and locations for bottomButtons and closeButton (if either are rendered in this Prompt).

drawBox()
Draws the outline of the Prompt

drawString(String s, int x, int y)
convenience method for drawing an unselected String to the Prompt. Calls next method with "false" as final parameter.

drawString (String s, int x, int y, Boolean isSelected)
Convenience method for drawing a string, with option of selected or not. This in turn calls the "drawCenteredString" method from [TBSGraphics](TBSGraphics.md), qv.

drawText(List

&lt;String&gt;

 lines)
Convenience method for putting multiple lines of text into a Prompt, unselected. Calls next method, with isSelected = false.

drawText(List

&lt;String&gt;

 lines, boolean isSelected)
Convenience method for putting multiple lines of text into a Prompt, selected or unselected. This method relies on drawString(), above.

drawHeader(String s)
Draws a header.

drawButtons(Object[.md](.md) buttons)
drawButtons(Object[.md](.md) buttons, String selected)
drawButtons(Rectangle buttonArea, Object[.md](.md) buttons, String selected)
drawUtilityButton(Rectangle utilityButton, int utilityType)

Used for drawing various types of buttons.


drawCloseButtonGraphic(Rectangle rect)
draws the "X" on the close button

drawMinimizeButtonGraphic(Rectangle rect)
fills in the graphic on a Minimize button

startMinimization()
startMaximization()
drawResizedBox()
drawMinimized()
Names are pretty clear: they do what they say they do.



getSelectedButtonIndex(int mouseX, int buttonCount)
Returns the index of the selected button (bottom row).

getSelectedButtonIndex(int mouseX, int buttonCount, Rectangle buttonArea)
Returns the index of the selected button (elsewhere).