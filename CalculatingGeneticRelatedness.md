## Calculating Genetic Relatedness Using a Shortest Path Graph Algorithm ##

Given the following 4 assumptions, I think the genetic relatedness of two organisms can be calculated independent of graph structure and directionality

### Assumption 1: Each link represents one unit of genetic difference ###

I believe the genetic relatedness of two organisms can be defined as the number of links between two organisms in a tree

In TBS edges are unweighted, for this reason I will assume all edges can be considered to be one unit of genetic difference

### Assumption 2: Directionality does not affect relatedness of two organisms ###

For example, in the following graph:

```
A ---> B ---> C ---> D
```

A is 3 units of genetic difference from D

If the directions are reversed:

```
A <--- B <--- C <--- D
```

A is still 3 units of genetic difference from D

In an improperly constructed graph the following relation may occur:

```
A --> B <--- C --> D
```

In this case I believe the best guess for the genetic difference of A and D is still 3 units

In the bidirectional case

```
A <--> B
```

The only possible physical explanation is that:

1. a clone of B evolved from A<br>
2. a clone of A evolved from B<br>

This is absurd, but the genetic difference from <code>A --&gt; B</code> and <code>B --&gt; A</code> is still 1<br>
<br>
<h3>Assumption 3: If there are two or more separate paths between two organism, use shortest path for relatedness</h3>

Given the following improperly constructed graph<br>
<br>
<pre><code>A --&gt; B --&gt; C<br>
|     |<br>
D --&gt; E<br>
</code></pre>

The distance between A and B is 1 and 3, however a best guess is that the difference between A and B is 1, and that<br>
the path <code>A --&gt; D --&gt; E --&gt; B</code> includes backwards evolution<br>
<br>
<h3>Assumption 4: Define a finite number for distance between unconnected nodes</h3>

This assumption is needed in order to calculate a meaningful average relatedness from a set of trees<br>
<br>
Generally if there is no path between two nodes, the distance between them is considered infinite. However, even if only one student<br>
has two unconnected nodes, then the average distance between those nodes will be infinite.  Therefore, I propose that the distance<br>
between two unconnected nodes in a tree be calculated as:<br>
<br>
maximum (non-infinite) path length + 1<br>
<br>
The same value can be used for the path between used and unused organism nodes.<br>
<br>
In the event that a student has no links at all, assign a distance of 1 between all nodes.<br>
<br>
<h3>Given these 4 assumptions:</h3>

Given two nodes, Dijkstra's algorithm will calculate the minimum path distance between them.  This can be used to generate a relationship grid for each student tree.<br>
<br>
For example:<br>
<br>
<table><thead><th>Node</th><th>Cat</th><th>Spider</th><th>Mouse</th><th>Fish</th><th>Lizard</th></thead><tbody>
<tr><td>Cat </td><td>0  </td><td>3     </td><td>1    </td><td>4   </td><td>1     </td></tr>
<tr><td>Spider</td><td>3  </td><td>0     </td><td>1    </td><td>1   </td><td>1     </td></tr>
<tr><td>Mouse</td><td>1  </td><td>1     </td><td>0    </td><td>1   </td><td>2     </td></tr>
<tr><td>Fish</td><td>4  </td><td>1     </td><td>1    </td><td>0   </td><td>1     </td></tr>
<tr><td>Lizard</td><td>1  </td><td>1     </td><td>2    </td><td>1   </td><td>0     </td></tr></tbody></table>

Given that all trees use the same organism nodes, an average of multiple trees can be calculated