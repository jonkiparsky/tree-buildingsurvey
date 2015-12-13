Extends [SubDropDown](SubDropDown.md)

# Introduction #

Simple getters:
getHullName()
getHull()
getParent()
getLevel()
getChildCollisions()
getCentroid()


toString(): what is getDisplay()?
equals(): Compare on identity. If this fails, compare by name

render():


GrahamScan():
The Graham scan is the standard method for finding the convex hull of a set of points. It works by finding a point guaranteed to be on the hull, and sorting the other points in the set by their polar angle relative to this "pivot point". The algorithm then walks counter-clockwise around the set of points drawing lines from one to the next as long as it is making right turns. If it makes a right turn from a point, it backtracks and eliminates that point from the hull.  A discussion of the algorithm including an applet demonstrating its functioning can be found at [Rahid bin Muhammad's Computational Geometry pages](http://www.personal.kent.edu/~rmuhamma/Compgeometry/MyCG/ConvexHull/GrahamScan/grahamScan.htm).

formsLeftTurn(Point a, Point c, Point c): returns true if the angle abc forms a left turn or a straight line when moving in a counter-clockwise direction. If the angle forms a right turn, b is not a part of the convex hull.