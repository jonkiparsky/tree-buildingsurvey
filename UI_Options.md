As a part of this Tree project, we will be separating the user interface from the underlying representation of the data. This will allow us to present the Tree quiz to students in various forms, in order to determine how manner of presentation affects student response. As this is ultimately an experimental model, we assume that presentations will be offered in an A/B condition format, offering one discrete variable which can be altered independent of other conditions. This assumption is subject to modification by the client.

The precise forms that will ultimately be implemented are to be determined in consultation with the client. Below, we list several options for consideration.

## Variable: links between nodes are directional ##

In the current implementation, nodes are linked by non-directional lines. One condition that has been suggested for experimentation is the use of directional lines, indicated by an arrowhead. This condition maybe paired with a non-directional variant.

### User stories of two conditions ###
  * In the Non-Directional case, the student selects two nodes (internal, connecting nodes and/or "Organism" nodes) and indicates that a connection is to be made. The connection is indicated on the screen by a simple line. The connection in the data set will retain the priority of selection, so that information will be available to the researcher, although it will not be represented on the screen.
  * In the Directional case, the student will select the origin node and the target node, and the on-screen representation will indicate the target node with an arrow on the connecting line. Internal representation will not be affected.
### Notes and observations (please initial): ###
  * It is assumed that no direction will be given as to the semantics of the arrows. Is this correct?-jk




## Variable: ease of creating nodes ##

To determine the effect of user interface on student response, vary the ease with which nodes may be created. In the current implementation, it has been reported that the process of node creation is not intuitive, and it has been suggested that this may affect the students' output. To test this, we propose to devise a more intuitive interface and pair that interface against the current one. As a variant suggestion, a more intuitive interface might be paired against a deliberately obscured version of the current interface.

### Description of the two conditions ###
  * In the Obscure Interface condition, students are presented with the current interface, or a deliberately obscured version of same.
  * In the Transparent Interface condition, the current interface will be varied by modifying the method of node creation in some fashion to be determined. Drag-and-drop has been suggested, and seems likely, but we're open to other options.

### Notes and observations ###
  * For "current interface" above, it is possible that we will modify other aspects of the UI. The important thing is that the final product be capable of presenting two UIs which differ from each other only in the means of creating a node. -jk

## Variable: editing of objects(nodes, links) ##

Currently user interface characteristic which could be affecting student response is the ease with which it takes in editing nodes. The current implementation has no ability to edit nodes or links made by the user.  The only option is to delete the object and start again from scratch.  A possible option to increase the ease of editing would be to add clickability to nodes & connections.

### Notes and observations ###
  * One option for nodes would be to have a menu of options be displayed when the user double clicks it.
  * The option would be that, going along with directional links option, the user could double click a link to reverse the direction.

## Variable: presence or absence of text field in nodes ##
> The current interface solicits a text label on creating a Node. Does this affect the students' judgement about the task, and therefore about the correct answers?

### Conditions: ###
  * Two conditions, identical except in the form of the Node object; one condition, as in the present arrangement, solicits a label when creating a node, the other woud simply show a solid object which has a parent and two descendants.

## Discussion ##
> When the program asks the student for a text label in a node, it's implicitly asking for an answer that the student is probably not prepared to give, if they have the correct answer. Furthermore, it's not clear what the question is, and the question can't be stated (is this true?) without prejudicing the experiment. It can be said that the text label gives the student a chance to justify their choice, but it's possible that by asking the justification, the survey steers the student towards answers they know how to justify (ie, presence of wings) rather than ones they suspect but haven't the language for (ie, presence of nipples). -jk


## 2X2 testing ##
If the purpose of varying the UI that the student sees is to determine the conditions which elicit the best performance on the Phylogeny Survey - without prompting the correct answers - then it seems reasonable to prepare a model whereby students may be assigned one of four conditions, as indicated by the 2X2 matrix below. If more than two variables are isolated for testing, as we might hope they would be, the program should be designed to allow the researcher to set up different oppositions of conditions without touching the code. As I envision it, the variables would be coded into the objects, with switches. If a 2X2 is the appropriate experimental paradigm, the variables to be tested could be selected by the experimenter, and the code would assign students randomly to one of the four conditions generated by the confluence of the two variables. (See the StoryOfEdwin for a fleshed-out version of this feature)


| | Obscure|Easy|
|:|:-------|:---|
| Directional |        |    |
| Non-Directional |        |    |