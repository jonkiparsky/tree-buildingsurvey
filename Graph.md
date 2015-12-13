# Introduction #
Graph represents the student's completed tree. It is basically a set of [vertices](Vertex.md) with methods for analyzing itself. Graph is the center of the Analysis classes.



## Construction ##
addVertex(): put vertex v in this graph
addEdge(): put Edge e n this graph

setGraphDirection: An attempt to determine whether student constructed a graph with consistent directionality (all connections go from root to leaves or vice versa). Not successful, should be fixed before being relied on.


## Analysis ##
## Branches ##
checkForBranches(): if there is a vertex with more than one connection, the tree has at least one branch


## Cycle Detection ##
containsCycle(): Marks all Vertices as unvisited, then visits() each in sequence
visit(): Recursive cycle check. If An adjacent node has been visited on this pass, we have discovered a cycle, return true. If not, visit() all adjacent [verticies](Vertex.md). If no cycle found, mark this vertex as complete.

## Constructing ancestor/descendant lists ##

initRelations(): one-stop shopping, runs relationship methods
buildDescendantList(Vertex v): recursively constructs a list of descendants for all vertices in the graph, starting with v
buildAncestorList(Vertex v): recursively constructs a list of ancestors for all vertices in the graph, starting with v
Each of these methods has a convenience caller that calls the method on the first Vertex in the list.

## Shortest Path calculations ##
Shortest path analysis uses a private class PathPair. This private class **needs further explication** See overview of analysis for the higher-level description of the use of shortest-path in tree analysis.

runFloydWarshall(): calculates shortest and average path lengths using the Floyd-Warshall algorithm. This algorithm is well-described in the literature(citations here)

calculateOrganismPathLengths()
doAnalysis()
getPathPairs():
testPair()

minOrgPathLength()
averageOrgPathLength()
maxOrgPathLength()
calcAverage()



## rendering ##
render(): set the upper left corner of the graph in a good spot, then call each component's render() method to display the graph.

## Getters ##
toString(): returns a list of vertices and connections, using their toString methods
getStudentName():

getVertexByID(): returns a Vertex, given its ID number
getInfo(): constructs a 3-line string for each Vertex, listing its "from" and "to" connections.

getUpperLeft(): returns the upper left point of the graph. This point is not necessarily occupied: if there is a node at (50, 100) and another at (100, 50), this method will return (50, 50)

get LowerRight(): returns the lower right corner of the graph. Same caveat as for UpperLeft.

getShortestPaths(): calls the runFloydWarshall() method and returns the result. **This looke like it returns a class-level variable, which would be odd, unless it's being called from outside the class.**


getPathIndexNames(): returns the pathIndexNames. If the FloydWarshall algorithm has not been run, run it.