# Introduction #
StudentController defines the methods for handling actions when running the TBS Applet as a student user. These methods are concerned with constructing a phylogenetic tree. StudentController inherits from TBSController, and communicates with StudentModel.


# Method Details #
keyPressed() handles keyPressed events. When a Prompt is open, keypressed passes all keystrokes to it. (in this case, an Enter does trigger some further action from the controller to restore the proper cursor and set the correct button state) In all other states, keystrokes are limited to DELETE and ESCAPE (which delete the selected element and break out of ADD state, respectively)

keyTyped() should probably be aliased to keyTyped. I need to find out why this is the way it is.