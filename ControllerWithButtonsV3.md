## Behavior Of Controller With Buttons (Version 3) ##
<br>
<b>MOUSE BEHAVIOR:</b><br>
<br>
selectedElement = previously selected element OR null<br>
clickedElement = element user most recently clicked on OR null if empty space clicked on<br>
ANY (STATE) = {SELECT, ADD, LABEL, LINK}<br>
DEFAULT STATE = SELECT<br>
ANY (ELEMENT) = {CONNECTION, EMPTYNODE, ORGANISMNODE}<br>
NODE = {EMPTYNODE, ORGANISMNODE}<br>
<br>
<table><thead><th>STATE</th><th>selectedElement</th><th>clickedElement</th><th>ACTION</th></thead><tbody>
<tr><td>SELECT</td><td>null           </td><td>null          </td><td>NONE  </td></tr>
<tr><td>SELECT</td><td>null           </td><td>ANY           </td><td> selectedElement=clickedElement</td></tr>
<tr><td>ADD  </td><td>ANY or null    </td><td>null          </td><td>model.add(new <code>EmptyNode</code>)</td></tr>
<tr><td>ADD  </td><td>ANY or null    </td><td>ANY           </td><td>NONE  </td></tr>
<tr><td>LINK </td><td>NODE           </td><td>null          </td><td>cancelConnection()</td></tr>
<tr><td>LINK </td><td>null           </td><td>NODE          </td><td>createConnection(x, y), NOTE 1</td></tr>
<tr><td>LINK </td><td>NODE           </td><td>NODE          </td><td>createConnection(x, y), NOTE 1</td></tr>
<tr><td>LABEL</td><td>null           </td><td>null          </td><td>NONE  </td></tr>
<tr><td>LABEL</td><td>ANY            </td><td>null          </td><td>rename(selectedElement)</td></tr>
<tr><td>LABEL</td><td>null           </td><td>ANY           </td><td>rename(clickedElement)</td></tr></tbody></table>

NOTES:<br>
1. createConnection(x, y) behavior is based on selectedElement<br>
<blockquote>if selectedElement==null, selectedElement = clickedElement<br>
if selectedElement!=null:<br>
selectedElement.addConnection(selectedElement, clickedElement)<br>
clickedElement.addConnection(selectedElement, clickedElement)<br>
<br>
<b>BUTTON BEHAVIOR:</b><br></blockquote>

<table><thead><th>BUTTON PRESSED</th><th>selectedElement</th><th>ACTION</th></thead><tbody>
<tr><td>SELECT        </td><td>ANY or null    </td><td>NONE  </td></tr>
<tr><td>ADD           </td><td>ANY or null    </td><td>NONE  </td></tr>
<tr><td>DELETE        </td><td>null           </td><td>STATE = SELECT</td></tr>
<tr><td>DELETE        </td><td>ANY            </td><td>selectedElement.removeFromTree(), STATE = SELECT</td></tr>
<tr><td>LINK          </td><td>CONNECTION or null</td><td>NONE  </td></tr>
<tr><td>LINK          </td><td>NODE           </td><td>createConnection(x, y), NOTE 1</td></tr>
<tr><td>LABEL         </td><td>NONE           </td><td>STATE = LABEL</td></tr>
<tr><td>LABEL         </td><td>ANY            </td><td>rename(selectedElement)</td></tr>
<tr><td>UNLINK        </td><td>null           </td><td>STATE = SELECT</td></tr>
<tr><td>UNLINK        </td><td>NODE           </td><td>selectedElement.unlink(), STATE = SELECT</td></tr>
<tr><td>UNLINK        </td><td>CONNECTION     </td><td>deleteConnection(selectedElement), STATE = SELECT</td></tr>
<tr><td>CLEAR         </td><td>ANY or null    </td><td>delete all tree elements</td></tr>