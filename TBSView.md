# Introduction #

TBSView is the parent class for StudentView and AdminView, the classes responsible for presenting the model to the user.

# Methods #

The following methods are defined in TBSView

Constructor


Getters:
getAppletCursor
getButtons
getVerticalBar
getYOffset
> public void setYOffset(int yo) {
> > yOffset = yo;
> > }


> public JScrollBar getHorizontalBar() {
> > return horizontalBar;

> }

> public int getXOffset() {
> > return screenPrintMode ? 0 : xOffset;

> }

> // sets the start of viewable tree area
> public void setXOffset(int xo) {
> > xOffset = xo;

> }


> public Boolean getScreenPrintMode()

> public int getMaxX()
> > getMaxY

getMaxY


Tooltips - methods involved in handling tooltips


> updateTooltip(String name, Point location)

> isTooltipRunning()

getDisplayAllTooltips()  - are we showing all tooltips right now?

setDisplayAllTooltips - turn tooltips on or off

toggleDisplayAllTooltips() - toggle "display all tooltips"


positionModelElements
positionButtons


Rendering
> public abstract void renderButtons(Graphics2D g2);

> public abstract void renderStudents(Graphics2D g2);

> public abstract void renderElements(Graphics2D g2);

> public abstract void renderScreenString(Graphics2D g2);

> public void renderUnselectedModelElements(Graphics2D g2) {

renderOrganismNode(Graphics2D g2, OrganismNode on) {

> renderOrganismNodeInfo(Graphics2D g2, OrganismNode on) {

renderEmptyNode(Graphics2D g2, EmptyNode en) {
> public void renderSelectedModelElements(Graphics2D g2) {

> public void renderSelectedNode(Graphics2D g2, Node n) {

> public void renderConnection(Graphics2D g2, Line2D line, Color color) {

> public void renderTooltip(Graphics2D g2) {


paintComponent

paintComponent() {

> public void paintComponent(Graphics g) {


print

print()