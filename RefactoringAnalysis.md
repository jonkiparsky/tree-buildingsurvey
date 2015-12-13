These are just some of the main things that I have accomplished in my refactoring of the Models & Student Controller:

# Model #
  * Removed a lot of redundant code from the `Student` and `Admin` models and in turn moved it into the `TBSModel` superclass
  * Moved all non-database persisted information such as sizing and positioning of objects out of the models and into the view or into the initial loading of the Applet.
  * Renamed fields such as `modelElements` to simply `elements` for a more straight forward definition of field as well as usage of less memory due to shorter name
    * Possibly be looking into renaming `OrganismNodes` to `StaticNodes` for scalability's sake since not all uses will involve organisms
  * Reworked the parsing of saved trees to avoid loading information of an element when it is not in the tree, because the information has already been set from resetModel() method
  * Setup `OrganismNodes` (`StaticNodes`) by use of only one loop and then added them to the list of elements in the Model, thus avoiding redundant looping
  * Eventually look at moving `buttonStates` information to the view since this is information that is not persisted to the database or saved tree
  * **Removed all model modification code into one simple utility class, `ModelUtils`, for better readability and debugging.**
  * Added boolean flags to the model for quicker calculation of `inTreeElements` and whether a tree has connections or branch nodes.
  * Updated Node code for `getX()` & `getY()` so that correct information will be returned whether a node is in the tree or not
  * Removed place holder for Immortal Branch Node and replaced by making use of `inTree` property on nodes

# Controller #
  * Consolidated `draggedNode` & `selectedNode` information into one field on the Model, `selectedElement` and a boolean property on the Node object, `beingDragged`
  * Made use of `ModelUtils` methods to clear up some of the confusion of code within the `handleMouseButtonPressed()` & `handleMouseQuestionPressed()` methods, also moved code managing Student Model's history to the `ModelUtils` class

# Possible Future Refactoring #
  * Move `creatingLabel()` code to `ModelUtils`, since labeling is an action upon the model
  * Move the `modifyOutOfBounds` to the View, because this method is dependent upon the sizing of the view
  * Moving modification of Student Model's status message to the view, because this is primarily a view feature
  * **Removing the immortal branch node from the initial list of elements in the model and replacing it will a simple `Rectangle` object for clicking and dragging by the user.  There is no actual need for this place holder especially in the tree string that gets persisted to the database.  Unfortunately this is change that will probably not be put into effect until a future version of the application because our version is so heavily interwinded with that placeholder.**