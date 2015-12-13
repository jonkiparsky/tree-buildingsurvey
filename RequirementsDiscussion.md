# Introduction #
These are requirements discussions between the CS682 Development Team and the Client (Professor White) for the Tree-Building Survey project.


---


Preliminary thoughts about scoring (from the code point of view - to be translated to/from the client's point of view when writing real requirements!)

It's easy to write graph algorithms that check/count:

  * whether the graph is connected (the number of components)

  * whether all the provided organisms have been used

  * whether provided organisms are all leaves

  * whether the graph is a tree/forest - i.e. whether there are any loops

  * whether pairs of provided organisms have been linked

  * whether internal nodes have indegree 1 (or 0 for a root) and outdegree 2 (or at most 2).

  * the distance (in graph steps) between any pair of nodes. (If the scoring rubric provides "correct" answers for some pairs then the scoring algorithm can report on how close to correct a tree is in each case, taking into account the fact that correct paths between provided organisms should go only through new nodes.)

Potential problem: these algorithms can fail to produce the right answer (from the client's point of view) if some of the empty/new nodes have been used just to provide text labels at various places on the tree picture. Perhaps any new nodes that aren't connected to anything should be assumed to be just labels, and ignored in the scoring (or counted as labels and reported as such). In a later release the code can provide for two semantically different kinds of objects, one for labels and for intermediate nodes.

Will the client have any scoring questions that depend on the way the nodes are positioned on the canvas?


---

_Question:_

Brian<br />
Seems to me that in "correct" solution each internal node has exactly<br />
two descendants - never three or more, and there's no need to bother<br />
with nodes with only one descendant (unless you have fossils in the tree).<br />
(These are interesting trees from a cs perspective too.)<br />
But you might want to consider a tree in which everything descended<br />
from one ur-organism as correct too, in a way.<br />
Ethan<br />

**Ethan Bolker 11:21, 30 September 2009 (UTC)**

_Answer:_

actually, it is fine to have as many descendants from an internal node as you like<br />
B<br />

**Brian White 15:32, 30 September 2009 (UTC)**

---


Question: In trying the code on various browsers (see issues) I saw no "submit" or "save" button under any of the browsers. Should there be?<br>
-jpk