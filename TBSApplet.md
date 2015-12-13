# Introduction #

TBSApplet.java is the entry point for the Java code. It is accessed through the perl-generated [frame page](TBSFrame.md), which uses Javascript to load the applet and pass in user data.

See Javadoc documentation for further information.

# Details #
The TBSApplet class implements only the init() method of the standard Applet class methods. init() uses parameters from the frame page and from [TBSGraphics](TBSGraphics.md) to determine how the applet will run.
Information passed in from the user and environment ([TBSFrame](TBSFrame.md)) includes:
  * size of window
  * current browser
  * whether user is logging in as student or admin
  * current student's tree or all student trees, depending on whether user is student or admin

Information loaded from [TBSGraphics](TBSGraphics.md) includes
  * text handling data
  * etc


Init sets up the appropriate [TBSModel](TBSModel.md) for the user, either [StudentModel](StudentModel.md) or [AdminModel](AdminModel.md). It also sets up a Graphics context for all drawing that will take place, and sets up an appropriate [View](TBSView.md) and [Controller](TBSController.md).


# Other methods in TBSApplet.java #

loadOrganisms()

Creates and returns a list of OrganismNodes from the properties file [Organisms.Properties organisms.properties], associating each with its image file from the images directory.

loadStudents()
Creates and returns a list of students, reading them from the "student" parameters passed to the applet by the [frame page](TBSFrame.md). Called by the admin version of the applet.

loadTreeFile()
Loads a tree file.

getTree()
Returns the text represenation of the tree stored in the current StudentModel.


getQ1()
Returns student response to Question 1.

getQ2()

Returns student response to Question 2.

getQ3()
Returns student response to Question 3 (Question 3, a set of multiple-choice questions, is not currently asked by the applet. Method retained in case this decision is reversed.

getStatus()
Returns a string reporting the student's current status in completing the survey. Gets a status string from the model, and appends a list of unused organisms.

questionInProgress()
Returns the student's current answer to an open-ended question.

acceptQuestionInProgress()
Forces save of current answer if student quits applet while answering a question.

getParameterInfo()


getAppletInfo()
Returns an outdated string with incorrect copyright information. MUSTFIX