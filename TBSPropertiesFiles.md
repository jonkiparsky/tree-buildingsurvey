# Introduction #

Properties files are used to store all literal strings and data used in the applet and accompanying AdminApplication. This allows for easy modification of instructions and help strings, as well as changing the set of organisms the student works with.
Properties files live in the source, under src/tbs/properties


# Properties Files: #
## Strings: ##
instructions.properties: A collection of strings, displayed on startup and when the student selects "instructions" under the help tab. The instructions themselves are divided into three long strings. When modifying these, be careful to check that modified strings display neatly in the instructions prompt.

TODO: check how to modify number of InstrDir strings

help.properties: Strings displayed when "help" is selected. To add help items [what](do.md)

status.properties: Strings relating to the current state of the model and currently selected button.

resizewarning.properties: The string warning students that their applet window is too small to display all of the applet buttons.

yesno.properties: Confirm string for undo action

questions.properties: The post-test written questions probing the student's understanding of the task. Includes both open-response and radio-button question, although the latter are not currently included in the task.

admin.properties: Strings used when running in admin mode.

analysis.properies: Strings displayed when running analysis routines.

colorchooser.properties: Strings associated with the color chooser (used in displaying optimal groupings)


## Data ##
organisms.properties: Data defining the organisms presented to the student. Format is
Name=imagefile, major grouping, minor grouping
where Name is the string associated with the organism, imagefile is the name of the file in bin/images, and major and minor grouping are used in [analysis](Analysis.md). Major grouping indicates Vertebrate/Invertebrate, minor grouping indicates mammal or non-mammal vertebrate (NMV) for the vertebrate grouping.

TODO: how to add minor grouping for invertebrates?