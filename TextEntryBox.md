# Introduction #

Displays a text entry box. This one has some tricky stuff for text handling.
Not clear where called

Inherits from [Prompt](Prompt.md)

# Details #

keyPressed(KeyEvent e)

keystrokes are handled by a long if chain, which nests down to three levels. Sorry.
Keystrokes handled are: Delete, left and right arrows, Enter, alphanumeric.

TO INVESTIGATE:
> - distinction between keyTyped and keyPressed?
> - use of timer?



Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages