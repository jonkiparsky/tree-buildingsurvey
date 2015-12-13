# Introduction #

Inherits from: [Node.java](Node.md) [ModelElement.java](ModelElement.md)

Organism Nodes represent the named organisms provided at statup to the student (Bat, Bird, Leech, etc)
These are not a fixed set in principle; the set is defined by the contents of the file "[organisms.properties](organismsProps.md)". In order to alter the set of organisms in the initial configuration, alter that file and add any needed image files to the images directory.
At scoring time, organisms have certain inherent properties (contained the properties file) - they are verebrates or not, mammalian or not, etc. These properties are ignored during tree building; the Model is unaware of them.
The main differences between Organism Nodes and Empty Nodes are that Organism Nodes are conserved during any run of the applet (they can neither be created nor destroyed) and organism nodes are immutable (their identities are fixed, while Empty Nodes can be labelled as the student wishes). Organism Nodes, in addition, have images associated with them, while the Empty Nodes do not.

# Method Details #

getHeight() - returns the constant height of all Organism Nodes, contained in TBSGraphics.

getWidth() - returns the width of the **image file** associated with this organism - Organism Nodes are represented as images in the tree, not as image+string.

getDefaultPoint() - returns the initial starting point of this Organism; when the organism is deleted, it returns to this point.

reset() - deletes this organism, cancelling any connections it is involved in and returning it to its default point.

getImageStartX()
getStringAreaLeftX()