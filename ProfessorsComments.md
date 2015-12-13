# Comments and questions from the professor #

### May 19 ###

Congratulations on following good SE practice to build a good product.

### March 11 ###

Just checking in to say that I like the results and the process from the first sprint and look forward to the second.

### February 16 ###

I see frequent updates to svn (and some to wiki). I applaud your commitment to using the svn commit comment field.

### February 4 ###

Good start on first sprint. (For your team it's actually the second sprint.)

I looked briefly at class StudentControllerTest.java. Interesting. Running it should be part of your build. I'd like to see what test output looks like.

### January 24 ###

The good news is that bug fixing continues. The bad news is that I see no schedule for the deliverable to Professor White. When does he need it by? When is your code freeze?

What is the priority ordering for fixing bugs? Who is assigned to which ones?

### January 3 ###

I see nothing on the schedule between now and January 30, which is when you say you will have a "Functioning program with full feature set". What is that feature set? Does Professor White know what he will have then, and that he will be able to use it with his students? Is that the right date for him (since the semester will already have begun by then.)

I note from the update history on google code that only Andrew has done any work since the 15th of December. Was that part of the plan? Is there a plan? The latest version on the test applets page is [R533](https://code.google.com/p/tree-buildingsurvey/source/detail?r=533), December 15, so there is no way for me - or anyone else - to test what Andrew has produced since then. There are no new bugs entered either.

You will have noted the grade I submitted for the fall semester. Now I expect you to earn it.

### December 14 ###

Still nothing on your schedule about the requirements specification document (due tomorrow - I know you've done it but I should see it scheduled!) or about steps toward the significant production release you need for Professor White at the end of January.

### December 8 ###

Your schedule contains just three items for December and January: 12/01, 12/31 and 1/30.
I guess you met the 12/01 deliverable, since there is something called "Version 1" posted. But there is nothing on the wiki that tells me what functionality it is supposed to have, so you could have posted just about anything.

In any case what's there is clearly not finished. There are still bugs - probably more than have been entered as Issues since I don't believe you've tested thoroughly. The instructions on the page don't match the behavior of the program. If that's because the instructions are for Prof. White's old version, they must be edited to be correct before you can declare yourselves done.

This should have been called Release 0.7, or at most Release 1.0alpha.

When do you propose to have a real production release, thoroughly tested by you, by Professor White and by people in the hallway (see Joel's test)?

What are your plans for now until the end of January? Please write them down in enough detail so that they can really be checked for completion.

### December 2 ###

The last meeting seems to have been very productive. Please update the schedule to reflect the conclusions you reached there.

You might want to move the database connection discussion and resolution to separate page where a curious future developer cam find out why you did it as you did.

One step build page still needs instructions for the Biology professor (in Idaho) who just wants to deploy the applet - s/he shouldn't have to compile it first. S/he's never heard of ant except in biological contexts. (His/her job will be much harder when the applet must connect to a database. Maybe there should be a version that does not require that. Ask Professor White.)

### November 18 ###

Good start on the one step build. What's there now are developer instructions.

You also need instructions for a biology professor who wants to install TBS using the jar file you've compiled. She'll need a compiled jar file with classes (not source) and instructions. (Eventually those instructions will need to explain database connections too - perhaps you should explicitly offer a standalone version. It would still be useful as long as print was supported.)


---


### November 18 ###

The good news is that you have open issues. The bad news is that there seem to be no closed issues. One of Joel's recommendations is that you fix bugs as you find them. Please do that.

The downloads page seems to have a zip of the source files (at least that's what the name suggests). It should provide a complete release: the compiled jar, a sample html page to invoke the applet, a readme file and the source code. And that file should be prepared by a one step build - see my comment below from more than a week ago.

### November 10 ###

Version 0.4 is marked as done at
http://code.google.com/p/tree-buildingsurvey/wiki/Versioning
but the link to it is broken. (I do see 0.4 from the link on the home page.)

You need a **one step build** that checks the code (and the containing web page?) out of svn, compiles the jar file and deploys it to the server and updates the links on the wiki. That build process should also set or check the version number so that it's consistent wherever it appears (as an svn label, in the code, on the web page, on the wiki).

If you can't automate all those steps the least you can do is provide a human readable script that steps through the process, and then make sure you follow your own script! That way you can never "forget to update ...".

### November 9 ###

Link on google code home page to Test Current Tree-Building Applet! takes me to an ancient version, not the current version. Either keep this link up to date or delete it - probably safest to delete. Replace it with a link to the versions page.

The text on the html page hosting what claims to be version 0.3 says it's version 0.1.
The sooner you make "About" work from the applet menu bar the easier it will be to keep the version number current and visible.

I'd hoped to see the results of a recent meeting with Prof White.


---

Right version, I forgot to update that part of the html. It was the same link that every other version 0.3 applet link took you to.
Now it takes you to 0.4, which says 0.4 on it.

Meeting minutes will be posted shortly after the meeting, which is still scheduled for 3/10.


---


### October 27 ###

I like the documentation on the page hosting applet version 0.0. Version 0.1 doesn't have any when I visit it from the applet wiki page, but does when I visit it from the wiki table of contents. You should establish a consistent methodology (with a template) for releasing versions. Hosting seems to be from Andrew's home page at umb, which is OK as long as all the versions end up there. Or you can use the UMB cs account at fatcow.

The applet versioning page is a good start. Features need to be expanded, of course. In particular, I think that the choice of user interfaces and when connection to the database should happen sooner.

I can see the code in svn (good!) but I can't see how to recreate some particular version.
That may be because I don't know how to look.



---

I've posted the number of the final revision under 0.01 near the link to the applet, and placed the source files (tarred) under downloads. By version two commit, I hope to have figured out how to mark this better within svn, whether that be by branch or subtler means. -jpk

---



Schedule needs to show when you hope/plan to meet with Prof White to determine what the new GUIs will look like.

---

Amen with a side of hallelujah to that. -jk

---


---


### October 22 ###

The Applets page is better now. It still needs work. The link there to version 0.1 is to a zip file. There should also be a link to a site where the applet is served, so I can test it without downloading and installing it.

The comments there about what is broken should be entered as bugs on the Issues page. If you don't want to do that you should fix the problems before you check in the code and post the version for testing.

The schedule still needs work - as it does for the other teams too. In class today I will suggest a new model for schedule maintenance.


---


### October 21 ###

I'm glad to see test applets loaded. I seem to find several, in several places. They need to be better documented. I have some suggestions. First, put all the links to applets on just one page. Second, post them in reverse chronological order (newest first). Finally, make sure that each applet is self documenting, so that when someone is looking at s/he  can know the version number and the date (that can be a simple text field in the applet, or the title of the containing web page). Then when a bug is reported the report can (and should!) say which version the bug was detected in. That will save time when versions are superseded since some old bugs can be marked as moot when they fixed themselves.


---


### October 20 ###

The schedule shows the build system as complete. But I can't find out on this wiki how to get the latest version of the code from svn and build it and test it. (I do see the instructions for the technology stack, but not how those pieces go together.)

You're misusing the downloads page. It should offer the latest version(s) of the working application, for people to download and install - code, documentation, installation instructions. The things there now are part of your internal development processes and belong elsewhere on the wiki.

I have some quite detailed schedule specifications I'll suggest this afternoon in class.



---

### October 13 ###

Items on the schedule should be listed in chronological order - it's hard to read the current version.

There's very little on the schedule beyond the items for a few days ago. Refactoring architecture is due in two days. Will it be done?

The vision statement is marked as done (pending client approval) but I don't think it's done at all!


---


### October 10 ###

This might be useful, at least for a first test. No good for exercising Java, though.

http://browsershots.org


---


### October 8 ###

Your (draft) vision statement is all about you and not at all about what you're trying to build. Moreover, it's all completely generic boilerplate. Sounds like Dilbert.

Your vision statement should describe your vision for the deliverable. That's the important part. After you've done that, you can add a sentence about what good engineers you intend to be.


---


### October 7 ###

Thanks for the schedule page. I suggest another column for "Done" rather than overwriting the due date when the item is done. Keeping the original due date helps when you look back to see if you were meeting the schedule you set - particularly if you put the actual completion date in the "Done" column.


---


### October 6 ###

The wiki needs a schedule page, so that all the things you said at your first meeting you would do soon have dates attached.

Reply from team:

Ask, and it shall be given unto you. [Schedule page](Schedule.md)