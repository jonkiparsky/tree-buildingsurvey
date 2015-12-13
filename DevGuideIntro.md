[Table Of Contents/Index](DevGuideTOC.md)

# Introduction #

The TBS (tree-based survey) applet was developed for Professor Brian White of UMass Boston by Andrew Schonfeld, Glenn Thelen, and Jon Kiparsky as a project for Ethan Bolker's Software Engineering course. The concept was developed by White as a tool to investigate student preconceptions of evolutionary relations. The participating student is asked to show the relationships among twenty organisms

# About the Code #

The TBS Applet comprises a large and highly interactive codebase. The overall architecture is based on a Model/View/Controller structure, dividng the program logic into three areas. The Model is responsible for defining the objects involved and the ways in which their state may be changed, the View contains logic defining the ways in which Model objects are rendered, and the Controller provides the interface by which the user manipulates the objects. Among other things, this allows the applet to be broadly divided between the Student and the Admin privilege sets, each with separate privilege sets and totally distinct goals, but using the same underlying code.
If you are approaching this code for the first time, you will find that it is helpful to explore the applet a few times, both in Student and Admin modes, in order to have referents for the various objects.


# About this Guide #

While there is javadoc documentation for much (if not all) of the applet's code, javadoc is of limited use to the maintainer/developer, as it only documents public classes and fields. This is proper, since javadoc is meant to provide documentation for a user of an API, while you are presumably going to be maintaining and developing (or building off of) the private-access methods as well as the public.
This guide is meant to supplement the javadoc with a more narrative treatment of the classes. The goal is to document intention over implemtation, on the assumption that if you know what the class is meant to do, you can probably work out how it does it. We will also try to highlight potential pitfalls, but in code this complicated, there are many complicated interactions. Make small steps and check your work.
The structure of this guide mirrors that of the code: there is a page for each file in the source, in addition to some overview pages. The pages are linked to those documenting dependent and parent classes, so it should be possible to trace the entire code tree via hyperlinks from the entry point, [TBSApplet](TBSApplet.md). There is also a [table of contents](DevGuideTOC.md) for direct access to particular pages.


# Unit Tests #
There are no unit tests in this package. Sorry. If you're thinking of making major changes to this project, you'd probably do well to start by bolting on a set of tests. That way, you'll know when your changes are causing unintended effects, and as a side benefit, you'll know the code pretty well before you start working on it.


# Trouble spots #
This code has been tested out in one run of about 200 students, and some destructive testing by volunteer testers. There are probably still some bugs left to find.
Some places where you might find them:
  * Anything to do with the undo feature. The mechanism for storing actions and reversing them seems to be somewhat brittle. Some bugs have been found and fixed; expect that there are others.
  * Restoring saved trees. The most common intermittent bug has been in storing state of a saved tree. The mechanism for saving trees to disk is completely separate from that for undo (one exports a list of values to a flat file, the other maintains a stack of instructions for undoing each command in sequence) so it is probably coincidence that these two areas are the top places for remaining bugs.





# Model/View/Controller #

This synopsis of the MVC pattern is taken from the [Sun website](http://java.sun.com/blueprints/patterns/MVC.html):
> Several problems can arise when applications contain a mixture of data access code,  business logic code, and presentation code. Such applications are difficult to maintain, because interdependencies between all of the components cause strong ripple effects whenever a change is made anywhere. High coupling makes classes difficult or impossible to reuse because they depend on so many other classes. Adding new data views often requires reimplementing or cutting and pasting business logic code, which then requires maintenance in multiple places. Data access code suffers from the same problem, being cut and pasted among business logic methods.

> The Model-View-Controller design pattern solves these problems by decoupling data access, business logic, and data presentation and user interaction.