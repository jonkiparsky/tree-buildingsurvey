# Meeting 10 - 1/20/2010 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld, Brian White(Client)

### News: ###

The team has submitted a version of the product that has about 90% of the functionality needed to be delivered for start-of-the-semester deadline of February, 1st.  Testing has begun and discussion of ideas for spring semester enhancements are underway.  Regular classes will be beginning again on Tuesday, January 26th.

### Discussion: ###

**Current State of Things**
> The meeting began in Professor White's lab where the group gave Professor White a demo of the current product.  Initially, Professor White was a little bit skeptical because the last time he tried to use the product he was unable to save his work, but after a quick demonstartion of the working save functionality his skepticism changed to excitement.

> Professor White was very pleased with the lack of bugs in the current student version, but especially with the fact that a student could save his/her work.  The basic functionality that was required for February 1st's production date was student should be able to login, create a tree, answer some simple questions, and be reassured that their work has been saved to a database and that they have recieved their 15 points.  With the exception of authentication(password protection) all of these criteria had been met.

> Next up, we gave a Professor White a demosstration of the new and improved Administrator Version of the product.  Originally, for every student that had submitted a survey Professor White would have had to login with the admin password, observe that student's saved work, logout and then repeat those steps again.  Our new version of the admin product required no excessive logging in & logging out.  All of his students and their saved data is easily displayed(see image below) for quick analysis.  This new functionality was met with much excitement.

![http://upload.wikimedia.org/wikipedia/commons/7/72/New_Admin_Version.gif](http://upload.wikimedia.org/wikipedia/commons/7/72/New_Admin_Version.gif)

**What Happens Next?**
> Next up, we discussed what still needs to be completed for February 1st.  These things were as follows:
  1. Continue detailed testing and bug fixing
    * A bug that was found during the demo was with using the "Add" button and then trying to undo this "Add"
  1. Adding password protection to the product
    * Professor White would create a username for Andrew and assign him a simple password for testing this functionality
      * **A day later Professor White decided to give Andrew access to a database test area for password & username creation, which was very generous & integral in instituting this functionality**
  1. Reworking the javascript blocking a student from saving an incomplete survey so taht instead of blocking the student they would be given a Yes/No Confirmation that they would be saving an incomplete survey.
  1. Updating the styling/coloring on some of the website screens, namely the incorrect login screen & the completed survey screen
  1. Being able to have the administrator be able page through the students with submitted survey by use of the Up & Down keys on the keyboard.
  1. A final run-through of all of the inctructions/text in the product and sing-off from Professor White (Jon, being the technical writer, stepped up to the plate on this one)

> In the end, the group will be handing a copy of the perl script & the applet JAR file so that Professor White will be able to simply place them on his server with the respective database tables & be up and running for February 1st.

**Spring Semester & Conclusions**
> Very briefly, we discussed the scoring algorithm that we would like to institute for the analysis portion of the administrator version of the product.  This feature would be the main focus of our Spring Semester development.  Glenn, discussed a method making use of Dijkstra's algorithm.  Eventually the group would like to possibly add other ideas to the list for the spring semester one of which would be to make this product extendable so other professors/instructors could easily convert it for their needs.

> In conclusion, Professor White was very pleased with our progress on the product had no concerns that we would not be able to meet the production deadline of February 1st.