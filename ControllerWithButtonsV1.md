# Proposed Behavior Of Controller With Buttons #

I think it might be useful to records the user's actions<br>
Also, most programs have an undo button<br>
However if this is too hard we can just leave out the undo, actions recording<br>

<pre><code>Stack undoActions<br>
Stack allActions // every time an action occurs it should be added to this<br>
Node selectedNode<br>
<br>
ACTIONS = ADD, MOVE, LABEL, CONNECT, DELETE, UNDO, NO_ACTION, IMPOSSIBLE<br>
<br>
if(Node is moved within tree) {<br>
  performAction(MOVE, x, y)<br>
  undoActions.add(MOVE, oldX - x, oldY - y)<br>
}<br>
<br>
if(node is dragged from left panel to right panel) {<br>
  performAction(ADD, node)<br>
  undoActions.add(DELETE, node);<br>
}<br>
<br>
Buttons = {label, connect, delete, undo, 1, 2, 3, 4}<br>
Buttons on the left = {label, connect, delete, undo}<br>
Buttons on the right = {1, 2, 3, 4}<br>
States = {label, connect, delete}<br>
ModelElements = {EmptyNode, OrganismNode, Connection}<br>
<br>
if label is clicked: state = label<br>
if connect is clicked: state = connect<br>
if delete is clicked {<br>
  state = delete<br>
  if(selectedNode != null) {<br>
    undoActions.add(ADD, selectedNode)<br>
    performAction(DELETE, selectedNode);<br>
  }<br>
}<br>
if undo is clicked {<br>
  if undoActions is not empty {<br>
    peformAction(undoableActions.pop)<br>
  }<br>
}<br>
<br>
if {1 ,2, 3, 4} clicked: Switch to corresponding GUI<br>
<br>
FOR ALL ACTIONS != NO_ACTION {<br>
  undoActions.push((opposite of) ACTION)<br>
  performAction(ACTION, args[])<br>
}<br>
<br>
IF ACTION == IMPOSSIBLE: print error<br>
<br>
</code></pre>

<h2><i>STATE TABLE:</h2>
clickedElement = element user most recently clicked on<br></i><table><thead><th>STATE</th><th>MODELELEMENTCLICKED</th><th>selectedNode</th><th>ACTION</th></thead><tbody>
<tr><td>null </td><td> any               </td><td> any or null </td><td> SelectedNode=clickedElement</td></tr>
<tr><td>label</td><td><code>EmptyNode</code></td><td>null        </td><td>LABELEMPTYNODE, String label</td></tr>
<tr><td>label</td><td><code>OrganismNode</code></td><td>null        </td><td>NO_ACTION</td></tr>
<tr><td>     </td><td>                   </td><td>            </td><td> or: selectedNode = clickedElement, state=null</td></tr>
<tr><td>label</td><td><code>Connection</code></td><td>null        </td><td>NO_ACTION</td></tr>
<tr><td>     </td><td>                   </td><td>            </td><td> or: selectedNode = clickedElement, state=null</td></tr>
<tr><td>connect</td><td><code>EmptyNode</code></td><td>null        </td><td>selectedNode = clickedElement</td></tr>
<tr><td>connect</td><td><code>OrganismNode</code></td><td>null        </td><td>selectedNode = clickedElement</td></tr>
<tr><td>connect</td><td><code>Connection</code></td><td>null        </td><td>NO_ACTION</td></tr>
<tr><td>delete</td><td><code>EmptyNode</code></td><td>null        </td><td>DELETE, clickedElement</td></tr>
<tr><td>delete</td><td><code>OrganismNode</code></td><td>null        </td><td>DELETE, clickedElement</td></tr>
<tr><td>delete</td><td><code>Connection</code></td><td>null        </td><td>DELETE, clickedElement</td></tr>
<tr><td>delete</td><td>null               </td><td> any        </td><td> delete selectedNode</td></tr>
<tr><td>label</td><td><code>EmptyNode</code></td><td>selectedNode</td><td>LABELEMPTYNODE, String lable</td></tr>
<tr><td>label</td><td><code>OrganismNode</code></td><td>selectedNode</td><td>NO_ACTION</td></tr>
<tr><td>label</td><td><code>Connection</code></td><td>selectedNode</td><td>NO_ACTION</td></tr>
<tr><td>connect</td><td><code>EmptyNode</code></td><td>selectedNode</td><td>CONNECT, fromNode = selectedNode, toNode = clickedNode (toNode != selectedNode)</td></tr>
<tr><td>connect</td><td><code>OrganismNode</code></td><td>selectedNode</td><td>CONNECT, fromNode = selectedNode, toNode = clickedNode (toNode != selectedNode)</td></tr>
<tr><td>connect</td><td><code>Connection</code></td><td>selectedNode</td><td>NO_ACTION</td></tr>
<tr><td>delete</td><td><code>EmptyNode</code></td><td>selectedNode</td><td>IMPOSSIBLE</td></tr>
<tr><td>delete</td><td><code>OrganismNode</code></td><td>selectedNode</td><td>IMPOSSIBLE</td></tr>
<tr><td>delete</td><td><code>Connection</code></td><td>selectedNode</td><td>IMPOSSIBLE</td></tr></tbody></table>
