# Introduction #

In order to address issues, we need to know who's working on them; this policy is meant to allow that. This document has been circulated, and no changes have been suggested. It's now team policy: follow it or suggest a change.

# Details #
  * All bugs should be posted as issues when they are found, with any details that can be determined.
    * Even if you intend to fix them immediately, put them on the board as you discover or create them. Tie them to revision number and give a date when observed. (revision number should be visible but unobtrusive in the applet or the frame page - this will be posted in the issues list as an enhancement) This will have two beneficial effects. The first is to let the team know what you're working on, so they can help or stay out of your way as appropriate. The second is to let the team and future developers understand why the code is the way it is. The third (no one expects the Spanish Inquisition!) is to allow us to give you proper applause for your work.

  * If you want to work on a bug, take responsibility for it in the comments under that bug and change status to Started.
    * Take charge of one bug at a time, unless you think several bugs form a complex that are fixable on one commit. Even if you think it's a quick fix, a one-liner, tag it before you start. Again, this is to allow proper coordination of work.


  * One person at a time on a bug.
    * There's no sense in wasting time having two people working on one problem at once. If you have suggestions, post a comment in the issues field.
    * Sometimes you'll see an easy fix, and someone else taking some time working on the problem. There are several ways that could happen. One is that your fix is right, but the other person doesn't see it. Letting them work it out is a good exercise for them, and makes them more useful to you in the future. Another is that your fix isn't quite right, and they've got a better one. Letting them work it out makes the code better.

  * Cross-reference to the commit.
    * A commit that kills a bug should cite that bug by issue number, and give a brief explanation of what was wrong, and what the fix did. A comment in the bug should cite the revision number. This allows us - or Prof. White, or his next team - to retrace our work, and keep from making the same mistakes again. It's not always obvious why one programmer wrote something the way they did; a good English-language summary can save everyone else some time. It can also reveal flaws in your logic, either to others or to you.

  * Review bug fixes.
    * If you see a bug posted as "fixed", take a minute to look it over and make sure you understand it. This keeps you current on the code and helps find faulty fixes.

  * The issues list should be part of our regular coding routine. If there are bugs, fix them. If there aren't bugs, find some.



An alternative approach to assigning bug responsibility: the team trades off weeks on bug patrol, rotating each week. The guy on bug detail is off the sprint schedule, and is only allowed to commit bug fixes. The other two should do their best to stump him. Some minor penalty is assigned for leaving bugs on the board, like buying the refreshments for the next meeting.

I don't know how well this would work, but it sounds like it might be fun to try.