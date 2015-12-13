## Resources ##

If there's anything you find useful in understanding this code, throw it up here. Remember, it's likely that at least one person on the team is less informed than you about anything we're dealing with. If you had to look it up, so will they.

# Subversion #
Subversion quick reference: http://www.cs.put.poznan.pl/csobaniec/Papers/svn-refcard.pdf

Subversion book: http://svnbook.red-bean.com/

More on subversion: To connect to the new branch as a separate project in Eclipse, this worked for me:

  1. Create a new workspace. This may be optional, but the only way I was able to make Eclipse create the new project as a separate project was to do it in a new workspace.
  1. Select "Import" under file menu
  1. Select SVN/Checkout Projects From SVN as import source
  1. Create a new repository location: 'https://tree-buildingsurvey.googlecode.com/svn/branches/rewrite'
  1. Select it for checkout. It'll offer to use a new project setup wizard, you can try that if you want - I just did a bare project create, it seemed to work.
  1. That should get you what's there, which is nothing at the moment, just some junk to test this all on. Let me know if there's any trouble.
  1. Be aware that this is probably not the best procedure under normal branching conditions, but this ain't them, so we'll do it this way for now.


# JDOM #
JDOM API: http://www.jdom.org/docs/apidocs/index.html


# [SVN Tortoise Client](http://tortoisesvn.net/downloads) #

# [Java 1.6](https://cds.sun.com/is-bin/INTERSHOP.enfinity/WFS/CDS-CDS_Developer-Site/en_US/-/USD/ViewProductDetail-Start?ProductRef=jre-6u16-oth-JPR@CDS-CDS_Developer) #
> [Installation Notes](http://java.sun.com/javase/6/webnotes/install/jre/install-windows.html)

# [Eclipse 3.4](http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/galileo/SR1/eclipse-jee-galileo-SR1-win32.zip) #
> [Installation Notes](http://wiki.eclipse.org/FAQ_Where_do_I_get_and_install_Eclipse%3F)

# [MySQL 5.1.39](http://dev.mysql.com/downloads/mysql/5.1.html) #

# [ActivePerl 5.10.1](http://www.activestate.com/activeperl/) #

# [Tomcat 6.0.20](http://tomcat.apache.org/download-60.cgi) #

# Model - View - Controller #

Professor White's code starts with the assumption that every object of biological interest _is_ a graphics object. That's a serious design error.

In this application there should be classes that represent the underlying biological _model_: perhaps just Species. Some of the instances are actual organisms - they are provided at startup. Others are the intermediate nodes the user creates. The biology models the user's ideas about evolution by allowing for links between species - maybe directed, maybe not. These objects know nothing about how they look on the screen.

The classes specifying the graphics are separate.

The connection can be managed using the Observer-Observable design pattern. The graphics objects are registered as Observers of the model objects. When the user makes a change to the model (by adding or modifying a link or a node) the model notifies the listeners (the Observers), who redraw the screen. (Changes in position don't change the model, but they do change the appearance.)

See http://www.cs.clemson.edu/~malloy/courses/patterns/observer.html,
http://java.sun.com/j2se/1.5.0/docs/api/java/util/Observable.html,
http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller.

Plan to do this refactoring - put it on the schedule. But don't start it until the existing code is bug free.

But do it then, _before_ starting to develop the alternative GUIs that the project calls for.