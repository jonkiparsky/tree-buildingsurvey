# Meeting 7 - 12/1/2009 #

Attendance: Jon Kiparsky, Glenn Thelen, Andrew Schonfeld

News:
> The Business Requirements Document is due in two weeks (12/14).
Discussion:
> This is the first "offical" meeting that has occured for about 3 weeks, due to thanksgiving & work schedules. Even though meetings have not been as frequent there has still been many code changes submitted.
> We began the meeting discussing the current progress of the project.  After some deliberation it was agreed that we would not meet our orignal deliverable of December 1st for database connections & multiple views.  Now this should not be misinterpreted as "falling behind".  We understood that the technology is already in place, we have just been exploring the feasibility of other options in the meantime.

**Database Connections**
> Based off of what Professor White currently had in place using a Perl script and the endless weeks of struggling that Andrew has experienced trying to hack applet security for database connections, we believe this is the only way to make a database talk to an applet.
  1. Start with a perl script that renders HTML as well as Javascript.
  1. When the script is first called upon from a browser it will render a login screen complete with a dropdown of usernames (retrieved from a perl DB query running in the background)
  1. once the user enters their password and clicks "Login" the perl script goes to work again and validates the login information
  1. once validation is complete the perl script tells the browser either to stay on the same screen(invalid login) or to call another perl DB for this user's saved Tree
  1. if the user is valid, the perl script renders a new HTML page that has a javascript function that is called onLoad() of the page that calls a method within the jarfile specified within an `<Applet>` tag with the saved Tree retrieved from the DB
  1. this method within the jarfile parses the Tree retrieved from the DB and turns that into a Model that is rendered in the applet, otherwise the applet starts with a new Tree
  1. once the user has finished their Tree they will click the  "Save" button on the website (not the applet) which will fire another javascript method that will set a variable to the string representation of the user's current Tree by calling a getter method within the `<Applet>` tag jarfile
  1. lastly the perl script calls the DB with an update query containing the user's updated Tree

> Our original goals for this project were to keep everything within the applet code (including database calls), but unfortunately due to the java's applet security this is impossible.  The only other suggestion would be to ask Professor White to upgrade the software on his server so that servlet programming would be possible, but we agreed that we are already too far along and too close to our late January deliverable that this is not possible.
> We ended our database discussion be deciding that Andrew would try one more solution which, if proved impossible, would be abandoned for Professor White's original code with many some newer, flashier HTML & Javascript added.
  * The possible solution would be to retrieve the output from calling a local perl script in a java URL object or by using java's Runtime object's exec() method.
    * _That night Andrew spent 4 hours working through these possible solutions and even a few others and once again came to the conclusion that applet permissions are far too advanced to be hacked_

**Multiple Views**
  * Professor White had actually informed Jon that he would actually prefer only 2 views (arrows & no arrows) rather than the 4 we had previously discussed.  We don't know if he had suggested this to make our job easier or not, but either way we agreed that only doing these two views would be fine.  The route that we discussed taking for this functionality would be to simply have two buttons at the top of the applet call a boolean toggle that would turn arrows on and off. (One caveat to this idea is that we would have to update the delete functionality for when a user selects a connection for deletion, this would now delete both connections in a two connection)

**Business Requirements Document**
> To end our meeting Jon agreed that he was the most apt to do some work on our Business Requirements Document.  Andrew had already begun work upon a BRD based off a similar one used at his current job and added that to subversion.  He also forwarded Jon a snippet of that BRD from his work so that he could possibly continue ours in the same format.

**Future Meetings:**
  * Thursday(12/3) - most likely not feasible since Professor Bolker hasn't had a chance to lecture in a few classes.
