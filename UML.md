# Introduction #

This is a place to discuss the use of UML in the project


---

## Initial Version of GUI Code - UML Diagram ##

I have uploaded a pdf file containing the UML class diagram of the GUI code as supplied by Professor White

I used the Java To UML editor plugin for Eclipse, a UML editor (Topcased), and PrimoPDF

The filename is:

"UML Class Diagram Of Initial GUI Code (as supplied by Professor White).pdf"


---


## How to Generate a UML File From a Java Project in Eclipse ##

  1. Download **Eclipse Modeling Tools (includes incubating components) (371 MB)**  from http://www.eclipse.org/downloads/ (scroll down a little). _Note: This is a full version of Eclipse that includes modeling tools_
  1. Extract the files into a directory of your choosing. _Note: Since this is a full version of Eclipse you can extract the files into a different directory than that of your current Eclipse install_
  1. Place a copy of "org.topcased.java.reverse\_0.2.0.jar" into your "eclipse/plugins/" directory (where "eclipse" is the directory that you extracted Eclipse with modeling tools into). _Note: There is no installation, just place the .jar file in the "plugins" directory as indicated_
  1. Start Eclipse
  1. Go to File > New > Project and select the "Java Project From Existing Ant Buildfile" wizard
  1. Click "Browse" and select "Phylogeny Survey/build.xml" (from wherever you put Prof. White's code)
  1. In the package view in the left window: right click "Phylogeny Survey", and select "UML From Java" from the menu (towards the bottom)
  1. Click the "Finish" button in the window that pops up (the default name is "model.uml")

There should now be a file "model.uml" in the directory "workspace/Phylogeny Survey/" (where "workspace" is wherever your eclipse workspace directory is).

You should now be able to open "model.uml" for viewing in a UML editor