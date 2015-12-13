## Introduction ##
The schedule for the spring semester will be couched in terms of deliverables per week. Responsibility for deliverables will be taken at team meetings.

In order to ensure orderly development, code submissions should be in fulfillment of schedule items or in response to items on the issues list. Experimental ideas should be developed in personal branches.


The schedule for the fall semester has been moved to [here](FallSchedule.md).

## Third Sprint ##
  * _April 1-???_: Development of Neural Network learning (Glenn)
  * _April 1-10_: First draft of deployable Applet (Jon/Andrew)
    * Includes reading and writing from file
    * _April 5th_: Version released on test server that does not correctly write to file due to permissions(Andrew)
  * April 13: Meeting with Professor White to iron out final scoring specs
  * April 13: Finish Dev Guide and javadoc for Model section (Jon)
  * _April 15-30_: Finish scoring algorithm and migrate changes to Standalone App & whatever possible to Admin version of Applet
  * April 20: Finish Dev Guide and javadoc for View (Jon)
  * _April 11-20_: Second draft of deployable Applet (Jon)
    * Includes creation of new classes, students, etc.
  * _April 1-20_: Researching Servlet-to-Applet technology for technology presentation (Andrew)
  * _April 20_: Technology Presentation
  * April 27 : Finish Dev guide and javadoc for Controller (Jon)
  * _May 1_: **Code Freeze**
  * May 4: Finalize Dev Guide and javadoc (tie up loose ends) (Jon)
  * _May 11_: Deliver final versions of Applet & Standalone Scoring application to professor White
  * _May 1-20_: Poster creation

## Second Sprint features backlog ##

Team Switching:
  * Create [build instructions](BuildInstructions.md) for builders(Andrew)
  * Build C3PO(Glenn)
  * Build VisitME(Jon)

Relatively Minor Features:

  * Minimize/restore prompts
  * Bring `AdminApp` and `AdminApplet` into sync as much as possible
  * Display student responses in Admin Application
  * Display responses in printout
  * Respond to Prof. White's review of current scoring mechanism (response pending, this may have to wait for third round)
  * Finish common-ancestor scoring (Jon)
  * Selectable columns in data readout

Major Overhaul:
  * Machine learning/neural network scoring (pending Prof. White's approval)
  * [Deployable Applet](Deployable.md)






Previously scheduled, not delivered:
(may reschedule, or not, depending on demand)

  * Glenn: Collect statistics on multiple graphs
  * Glenn: Student trees searchable by graph features
  * Andrew: Study feasability of logging. Deliverable: written report, aye or nay and why
    * [Logging Feasibility Report](AppletLogging.md)
  * Andrew: If feasible, logging. Otherwise, a new deliverable here.
  * Jon: Refactor, unit tests
    * These were derailed by Andrew's work on refactoring. Might return to unit tests, at the moment documentation seems more critical.


## Delivered Items from first sprint ##
  * Glenn: Implement White's tests
  * Glenn: Load tree into a graph. Deliverable: load a tree and traverse it.
  * Andrew: Refactor Model. Deliverable: Simplicity ([RefactoringAnalysis Refactoring
  * Jon:  Unit tests for White's scoring items (sort of)
  * Jon: Implement various of Prof. White's tests.
  * Andrew: Refactor Prompts
  * Andrew: Refactor View
Jon: Work on testing methods, documentation