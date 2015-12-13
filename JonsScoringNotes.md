**Some thoughts on scoring**

---


To begin with, as I've said before, scoring is very much the wrong word for what we want to do here. Individual performance is a secondary consideration at best, the point at interest is in fact the aggregate performance of the group. Rather, it is not the performance of the group that interests us, but the depth of understanding displayed by the group, and the quality of that understanding. This quality of understanding is displayed as much in the mistakes made as in the correct understanding displayed, and more subtly in the correlation of mistakes. A "scoring" algorithm suggests assignment of points to correct answers, and summing those points to get a "score". This is the correct
way to judge a single student, but perfectly obscures anything interesting we might learn in this experiment. This point being made, we move on to assembly and analysis of a graph from the data gathered in a TBS trial.

**Analysis of the graph**

---


We begin with analysis. knowing what data we will need will tell us how the graph should be built.

We are given some characteristics on which Professor White based his original "scoring" method. These are:

  * **Structural**
    1. Are all organisms terminal?
    1. Does the tree show a single common ancestor?
    1. Are groups labelled?
    1. Does the tree branch?
    1. Are all organisms connected?
    1. To what degree is the tree hierarchical?

  * **Placement/Organization**
    1. Is the tree divided at the top level into vertebrates and invertebrates?
    1. Does the tree divide secondarily into mammals and non-mammals on the vertebrate side and into arthropods and non-arthropods on the invertebrate side?

  * **Misconceptions**
    1. Does the student group marine organisms together? (whale, fish, shark, to which we might add horseshoe crab)
    1. Does the student group winged beasties together? (bird, butterfly, bat)

  * **We might further add other points of interest, mostly structural in nature, for example:**
    1. Is the tree acyclical?
    1. Does the tree coverge?
    1. Is the tree binary, or does it have multi-way branching? (by no means an exhaustive list)

Finally, it would be worth doing to search for correlations of different features or absence of those features, although this may have to wait for future iterations.


Of these, almost all are amenable to mechanical analysis. The question of labels may be beyond our scope, except in a rudimentary boolean check for the presence of text in empty nodes.

**Assembling the graph**

---

The graph consists of a list of nodes, each of which has some fields. Among these are four arrayLists of nodes, children, parents, ancestors and descendents. Children and parents are read directly from the data file. ancestors and descendents are constructed by iteratively adding the children of each child, stopping when we add the node itself or when there are no further children to add. If the former occurs, we have found a loop; the graph is circular at some point, otherwise we have an acyclical graph. Consulting these lists gives us the boolean functions isChildOf(), isParentOf(), isAncestorOf(), and isDescendentOf().

If we find a node which contains all of the nodes in the graph in its descendents list, then the graph is connected and we have found its root. If the graph is fully cyclical, meaning that there is a link from the "bottom" of the graph to the "top", then any point on this cycle may be treated as the "root". Note that there may be a non-cyclical branch in this case, consider the case where A connects to B and C, and C eventually descends to and ancestor of A, but B does not.

It is possible that the student may create a reversed graph, in which "ancestor" and "descendent" exchange logical places.

If we do not find a root node containing all of the nodes in the graph, then we must address the fact that we have a non-connected graph, possibly two or more graphs or a set of unconnected points or some combination; this is unlikely and it is not clear to me how critical it will be to deal with this in the February release.

It is conceivable that more than one node will fulfill the root condition.

If we find a singular node fulfilling the root condition, and the graph is not circular, then the tree satisfies structural condition #2: there is a single common ancestor.

It should be obvious that by this point we have tested condition S-5: we know whether all organisms are connected.

Condition S-4 can be tested simply as well. If all nodes but one have exactly one child, then the tree does not branch. However, it will be more interesting to find a way to describe, mathematically, the shape of the tree. A tree may be linear - a list - or it may generally diverge, or or may generally converge. A generally converging tree can be treated as the mirror case of a generally diverging tree, with its root in a bottom-level child. If a tree is generally diverging, we should identify any convergences (nodes with two or more parents); these will indicate a confusion on the student's part. The reverse holds for a converging tree. If a tree is entirely diverging - the most likely case, and the one I will focus on - then its shape might be indicated statistically, by three numbers: the number of levels from root to deepest child, the mean number of connections per node, and the standard deviation from that mean. Since I am not well-versed in statistics, I only offer this as a suggestion.

Condition S-6 can be answered by counting the longest path from root to a terminal node, not taking loops, if there are any.

Condition S-1 can be checked as the descendents lists are constructed: if all O-Nodes have empty descendent lists, then S-1 is true.

This leaves only the third of the structural conditions; this requires some further definition before it can be answered mechanically.
  * The placement conditions are trivial. Of the top node's children, if one contains all vertebrate nodes and only vertebrate nodes, one part of the condition is satisfied, and if one contains all of the inverterbrate nodes (and only invertebrate nodes) then the other part is satisfied. In either case, label the satisfying node "Vert\_Top" or "Invert\_Top" and perform a similar check for mammals and non-mammals or arthropods and non-arthropods to address the second condition.

A more interesting version of this would again use some statistics:
  * If one of the second-level nodes contains a preponderance of vertebrates, report that percentage, and so on for invertebrates, etc. This might get tricky. I nominate not me to do this one.
    * The easiest way to address the misconceptions section would be to provide a "siblingOf()" or a "cousinOf()" function, where a.siblingOf(b) would return the boolean value of b.childOf(a.parent()) - this breaks on multiple parent trees - and a.cousinOf(b) would return b.descendentOf(a.parent()). Which would be used would depend on the definition of "group together".
    * This addresses, roughly, the conditions mentioned in Professor White's desired conditions of "scoring". For the most part, the implementation of the math should be straightforward, though somewhat involved.
    * The implementation of the scoring routines as a whole - how the professor is to interact with the scoring functions - is another kettle of fish, but I hope that this helps to clarify the graph portion of the business.