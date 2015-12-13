# Introduction #

How to make the TBS Applet usable by other professors at other institutions


# Why deployable? #

The TBS Applet, in its current state ([r900](https://code.google.com/p/tree-buildingsurvey/source/detail?r=900)), is an effective device for eliciting student intuitions about phylogenetic relations among a collection of organisms. However, since it depends on a hard-wired connection to a local database, it is quite non-portable, since it can only be run for students who are in Professor White's database on UMB's computers.
This arrangement could be duplicated on other computer systems, but when "configure the database" is a step in installation, it is safe to say that "ease of deployment" has gone off the list of features.

In order to gather a larger data sample, as well as a broader distribution of student respondents, it would be useful to modify the applet's data-storage methods to allow for testing at arbitrary locations.


# The Interactions #

The students' interaction with the applet will be very much like the current applet. The only real difference will be that they will have to select their class from a drop-down menu. (Classes will probably be identified by name of professor of name of institution; we anticipate relatively low participation on the order of 10-20 classes per year) Having done this, they will be able to select their own names, and enter an automatically-generated password, and then complete the survey as in the current version.

From the participating professors' point of view, running the applet will also be fairly simple. The applet will be hosted on Prof. White's servers at UMass, so there will be no installation. A professor who wants to inflict the survey on his class will have to convey a class list to Prof. White; it will be Prof. White's responsibility to run a setup script at the UMass end.

# The Changes #

The changes to the current applet will be minor. No changes will be made to the java itself. The changes will be to the surrounding perl scripts.

  * A new script will be added to set up each class. This will create a directory, associate it with an identifier for the class, and populate it with empty data files for each student. This information will be made available to the TBSApplet.pl script.

  * The TBSApplet script will look for student data files in directory files (based on student selections) rather than reading from a database.

  * That's about it.