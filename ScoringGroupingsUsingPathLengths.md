# Scoring Groupings Based on Ratios of Average Shortest Path Lengths #

**Step 1**: Given N organisms, assign a unique index from the range `[0 to N - 1]` to each organism.<br>  Given M user-created nodes (i.e. empty nodes), assign a unique index from <code>[N to N + M – 1]</code> to each user created node.<br>
<br>
The total number of nodes is T = N + M.<br>
<br>
<b>Step 2</b>: Create a 2D matrix <code>path[][]</code> of dimension T by T and initialize all data to <i>UNCONNECTED</i>, which can be finite or infinite.  In our implementation <i>UNCONNECTED</i> = 9999. For finite values, this value should be less than half the maximum value that can be stored in its data type.  This is to avoid overflow. It should also be much larger than any reasonable path length.<br>
<br>
<b>Step 3</b>: Set each <code>path[n][n]</code> = 0 <i>(this is because the shortest path from a node to itself is 0)</i>

<b>Step 4</b>: For all nodes with index n1 with an adjacent node with index n2: set <code>path[n1][n2]</code> = 1<br>
<br>
<b>Step 5</b>: Run the following algorithm (Floyd-Warshall):<br>
<br>
<pre><code>for(int k = 0; k &lt; T; k++) {<br>
   for(int i = 0; i &lt; T; i++) {<br>
      for(int j = 0; j &lt; T; j++) {<br>
	path[i][j] = Math.min(path[i][j], path[i][k] + path[k][j];<br>
      }<br>
   }<br>
}<br>
</code></pre>

Note that running time is N cubed.  Since no student graph has more than 40 nodes (about 64000 loops) this is not prohibitive. Even with 10000 student trees, the number of loops required is less than one billion, and there are only slightly over two operations per loop (on average). This should be no more than a few seconds work for a modern CPU.<br>
<br>
Since this algorithm was designed to score grouping independent of graph structure, directionality is ignored, so <code>path[i][j] = path[j][i]</code>

After running the Floyd-Warshall algorithm <code>path[][]</code> will contain the shortest path between each combination of pairs of organisms<br>
<br>
At this point, any rows and columns with index >= number of organisms (N) can be discarded.<br>
<br>
<b>Step 6</b>: Find the <i>maximum shortest path length</i> between two connected organisms. (In the event that no organisms are connected, the maximum shortest path is 0, the distance between a node and itself)<br>
<br>
<pre><code>int max = 0;<br>
for(int i = 0; i &lt; N; i++) {<br>
   for(int j = 0; j &lt; N; j++) {<br>
        if (path[i][j] == UNCONNECTED) continue;<br>
        if (path[i][j] &gt; max) max = path[i][j];<br>
   }<br>
}<br>
</code></pre>

<b>Step 7</b>: For any pairs of organisms that are unconnected <i>(<code>path[i][j]</code> = UNCONNECTED)</i>, set the shortest path length between them <i>(<code>path[i][j]</code>)</i> to: <i>maximum shortest path length</i> + 1<br>
<br>
The reason for this is so that the algorithm is tolerant of a small number of unconnected nodes<br>
<br>
<b>Step 8</b>: Find average path length between members of a group as follows:<br>
<br>
For all members of a group (ex. Mammals) take the sum of all <code>path[i][j]</code> such that i,j are both mammals and i != j, then divide the result by the number of paths used in the summation.<br>
<br>
<b>Step 9</b>: Find average path length between members of a group and non-members as follows:<br>
<br>
For all members of a group (ex. Mammals) take the sum of all <code>path[i][j]</code> such that i is a mammal and j is a non-mammal, and divide the result by the number of paths used in the summation.<br>
<br>
<b>Final Step</b>: Compute:<br>
<br>
Average path length between group members and non-members <i>(result of Step 9)</i> <i><b>divided by</b></i> Average path between group members <i>(result of Step 8)</i>

This gives the result of this algorithm <i>(see comments below for discussion of using <code>1-(in-group/out-group</code>))</i>

Since the path lengths between group members should be less than the path lengths between group members and non-group members, larger scores are better.<br>

Randomly grouped graphs would score around a 1.0<br>
Lower than 1.0 is worse than random<br>
Greater than 1.0 is better than random<br>

Based on current available student data the grouping scores are classified as follows:<br>
<br>
scores <= 1.0 are bad (red)<br>
1.0 < scores < 1.25 are poor to OK (yellow)<br>
scores >= 1.25 are good (green)<br>
<br>
(The colors refer to the background colors corresponding to these scores in the  AdminApplication data table)<br>
<br>
While this score is a relatively good indicator of grouping, the score is a bit arbitrary.<br>
<br>
Also, an improperly constructed tree can score higher than a properly constructed tree.  For example, a proper tree should not have organisms directly connected to each other.  A properly constructed tree will have a minimum distance of 2 between any pair of organisms.  However, a tree with mammals connected directly to each other would have an average distance between mammals closer to 1, and therefore would score higher.  Indeed trees that score highest on this score are improper trees, but the groupings are very good.