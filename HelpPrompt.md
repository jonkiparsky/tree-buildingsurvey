# Introduction #
Displays the help window, which can be switched among several categories of help.
Strings are read from properties files, and can be changed without touching the code.**"Introduction" is read from "instructions.properties", property name "instrIntro" "Instructions" tab is read from "instructions.properties", properties "InstrDir", "InstrDir2", and "InstrDir3". "Button Info" is read from "help.properties". "Survey Status" is generated based on the current state of the Model. (model.surveyStatus)
(**this is not strictly true, there are still a few strings which are hard-coded)

Inherits from [Prompt](Prompt.md)

# Details #

The paintComponent of this method is pretty hairy, and could bear to be factored out a little, but it's reasonably comprehensible if taken bit by bit and read with reference to the Prompt.java class.

Otherwise, this class only has one other method, to put help text and header in the Prompt. It looks like this could be factored into methods of Prompt.