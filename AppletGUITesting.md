This was intended to test a preliminary draft of the model: it's completely outdated and should be replaced by new testing protocols. I'll undertake to draft that. Shooting for 11/20, but I'll let it slip if there's more critical items.



# Behavior #

The model should perform intuitively under the following conditions. Some of these are more arbitrary than intuitive, but for the purposes of testing, intuitive is as stated, if stated.

  * X, Y increasing and decreasing: should work in all directions.
  * No drag: create W X W dot, where W is line width.
  * Offscreen drag - expansion of rectangle should cease at edge of frame when mouse leaves frame. Maximum coordinate value is width or height of frame, minimum is zero. Visually, border of rectangle should run alongside frame border (both should be visible, contrasting colors please). (if it helps, jog those max and min values in a hair)
  * Re-entering frame: start rectangle at point O (Origin). Drag down and right until mouse leaves frame. Bring mouse back into frame. Is behavior correct when mouse re-enters? Check all four sides. Correct behavior: second x,y coordinate pair is at final mouse release point, if it's within the frame.
  * Out-of-frame: if there is visible tracking of the mouse, does it behave correctly when the mouse is out of frame?
  * Is the frame size responsive to the user's window size? Does it resize if the user resizes? (This may be an issue in deployment: White assumed a large monitor, larger than most machines, and certainly larger than typical laptops)
  * Do the doors align flush with their frames? Do the locking mechanisms engage cleanly? If so, check your work: you built a cabinet, when we wanted an applet.