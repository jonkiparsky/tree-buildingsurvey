> Two documents were provided by Professor White as guides to analyzing the trees.

A summary of the experiment prepared by Aimee Young for her Master's Thesis committee (http://tree-buildingsurvey.googlecode.com/files/aimee%20to%20committee%20oct%202008.pdf 2 page PDF], all text)

A somewhat different summary, with illustrations:
[Page 1](http://upload.wikimedia.org/wikipedia/commons/9/97/TBSExp-page1.gif)
[Page 2](http://upload.wikimedia.org/wikipedia/commons/d/d5/TBSExp-page2.gif)
[Page 3](http://upload.wikimedia.org/wikipedia/commons/8/8a/TBSExp-page3.gif)
[Page 4](http://upload.wikimedia.org/wikipedia/commons/1/1f/TBSExp-page4.gif)
[Page 5](http://upload.wikimedia.org/wikipedia/commons/0/04/TBSExp-page5.gif)


Converting the parameters in these documents into graph-theoretical terms, we will begin by using these definitions. **These are preliminary definitions and are expected to change**.


Structural:
1) Are all organisms terminal?
Boolean: do all organisms in the tree have 0 outgoing connections?
Boolean, non-directional: do all organisms have exactly 1 connection?
  * **Note** this fails in the case of 10 linked pairs of organisms!
scalar, directional: divide number of terminal Organisms by the number of organisms in the tree.
Scalar, non-directional: divide number of organisms with exactly one connection by number of organisms in the tree

2) Does the tree show a single common ancestor?
Boolean: is there a single Node which includes all elements in the tree in its descendants?

3) Are groups labelled?
Boolean: is there at least one label with text in it? (this will become more nuanced if the suggestions in [issue #80](https://code.google.com/p/tree-buildingsurvey/issues/detail?id=#80) are taken up)

4) Does the tree branch?
This can be solved by arithmetic: divide (number of connected nodes -1) by depth of tree. If you get 1, the tree doesn't branch. If you get >1, the tree branches. This isn't a very interesting number - it smears the data - but it does answer the question in a rough way.
We should implement this, then do something more graphy.

5) Are all organisms connected?
There are two questions here. Are all the organisms in play? Are all the organisms in play connected in one graph?
Both are trivial.


6) To what degree is the tree hierarchical?
This is related to #4. The statement in the scoring rubrics is not exactly a matter of raw depth-of-tree, but of semantic grouping.
One way to interpret this would be naively checking depth of the tree. This is probably a good first-pass interpretation. It will be wrong, but informative.


Placement/Organization
1) Is the tree divided into vertebrates and
invertebrates?

Find the lowest node which contains more than 50% of the vertebrates in the tree, and likewise the one containing more than 50% of the invertebrates. Report percentage of verts and inverts in each.


2) Does the tree divide secondarily into mammals and non-mammals on the
vertebrate side and into arthropods and non-arthropods on the
invertebrate side?

This requires new information - currently the applet only knows about vertebrates and invertebrates. Given that information, the analogous solution to above should apply.

Misconceptions
1) Does the student group marine organisms together? (whale, fish,
shark, to which we might add horseshoe crab)
2) Does the student group winged beasties together? (bird, butterfly,
bat)

These will require new information; applet does not know about wings or swimming. Given that information, define "together" as "siblings" for the first pass, then refine.



Again, all of these are chosen for simplicity, not for correctness. The places where they fail will show us how to improve them. Do not think of this as a final proposal, but as a way to get the scoring in motion.