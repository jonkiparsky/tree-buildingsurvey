# Introduction #
TBSUtils contains a collection of static utility methods used throughout the [TBSApplet](TBSApplet.md)


# Methods #
public static Line2D getConnectionBounds(Node n1, Node n2)
Returns a line connecting two node objects. The line is calculated from the Nodes' center points, but drawn from the edge of each Node, using getIntersectionPoint()


private static List

&lt;Line2D&gt;

 nodeSides(Node n)
Returns the list of lines marking a Node's sides

public static Point2D getIntersectionPoint(Line2D line1, Line2D line2)
Returns the intersection point of two lines

public static Line2D getArrowHead(Line2D conn, double angle)
Draws the arrowhead at the end of a connection

public static boolean isStringEmpty(String s)
Returns true if a String is null or ""

public static boolean collide(List

&lt;ConvexHull&gt;

 hulls)
Returns true if two convex hulls collide.

public statuc boolean smallCollision(List

&lt;ConvexHull&gt;

 hulls)

public static List

&lt;String&gt;

 collisonText(AdminModel model)

private static List

&lt;String&gt;

 collisionText(List

&lt;HullCollision&gt;

 collisions)

public static String commaSeparatedString(Collection<?> strings)

public static List

&lt;Point&gt;

 convertAreaToPoints(Area a)