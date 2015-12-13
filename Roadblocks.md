# Introduction #

This will act as somewhat of a blog detailing some of the "bumps in the road" or challenges that have been experienced by TBS development group.

# Roadblocks #

November 1, 2009 - Relative URL's for location of images
> During meeting #2(10/29) Andrew had volunteered to research the problem of location of image files being dependent of where the .jar file was placed.  He had come up with the suggestion of just using the images directory located on Professor White's server by using relative URLs.  Although, Jon had warned him that there maybe security settings used by applets that make this impossible to work, Andrew proceeded with caution.  After a few hours of struggling with the exact problem that Jon had warned him about he decided to go another route.  After reviewing Professor White's original applet code he saw that he was making use of resources, `this.getClass().getResource("images/list.txt");`.  Once Andrew saw this code he had a eureka moment and decided to move the images directory into the src directory and have the images retrieved in the code by making use of the `getResources()` method listed earlier.  Yet another example of how old code is by no means unusable.

October 27-29, 2009 - SVN check-ins
> In anticipation of the deliverable for the new applet versioning being due the next night, everyone in the TBS development group was checking in new code.  Unfortunately, in the process Glenn had accidentally deleted source files and then committed new ones.  During the time that it took from when Glenn had deleted and checked in new source code there were check-ins from Jon and when Glenn deleted and checked in new code it removed Jon's changes.  After discussing some of the nuances of SVN during the team meeting the next night, we came to an agreement that files should never get deleted.  It was not a fatal problem, because Glenn was able to go through the check-ins that Jon had made and revert some of the changes he made back into the current code.

October 23-27, 2009 - More Extraneous Development and Server Crash
> After Andrew was able to get a deployment running on Professor White's test server he decided that it may be a good idea to start looking at ways to update the server's software in order to make deployments using more up-to-date technology.  What should have been understood from the start was that this type of development was way out of the scope of the project.  Unfortunately, by the time Andrew finally understood this why something like this was considered to be outside the scope of the project he had already managed to crash Professor White's server.  Although an extremely embarassing and stressful experience, Andrew was able to learn a very valuable lesson in what NOT to do when developing a piece of software.  Andrew is currently in the process of trying to right his wrong and helping Professor White restore his server.

October 17, 2009 - Verifying Deployment on Professor White's Server
> Professor White was nice enough to lend us a test server (originally just housing his own code) for us to deploy new versions of our code on.  This seemed like a simple task, but the server was running a much different version of apache software.  Andrew took the task of deploying the current updated code to this server and, after much conferencing with Professor White, was able to get a copy of the group's updated code up and running.  This was verified when a previous bug spotted with Professor White's original code was no longer occuring on the code Andrew deployed.

October 9-13, 2009 - Understanding orignal code & local deployment
> With perl being a main staple of Professor White's code, the challenge of creating a locally deployable version of his code was an intense one.  After much research on the web, installation of perl software and perl eclipse plug-ins, proper configuration of tomcat web.xml & context.xml files a locally viewable version of Professor White's current code was made.  The obstacle of understanding and implementing the perl code was difficult but it was a good learning experience.  Even if we decide to go in a different direction for the display that hosts the applet.

October 13, 2009 - Vision Statement
> Original understanding of vision statement formulation was a little bit off. But after discussion of team members and collaboration with Professor White a [vision statement](VisionStatement.md) was made.

October 6, 2009 - Extraneous Database Development
> After analyzing the original code written by Professor White, Andrew began trying to locally replicate the database structures utilized in the code.  This was something that should have saved until the latter parts of the development process.