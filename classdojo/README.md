classdojo-tech-screen
=====================

A set of JavaScript challenges

The scenario:
----------------
 
Someone has written a really bad image format to store our avatars (they haven't really, but bear with us).
The format is a JavaScript object containing a 1d array of CSS-style hex strings and some additional metadata.
 
Use this format to complete the challenges below!
 
 
Here are the challenges:
--------------------------
 
- Challenge 1: Write a function to print out the separate RGB values for each pixel in image1. 
(extra credit: use a module or library to output some standard image format representation [png/bmp/jpg] of the image)
 
- Challenge 2: Write a function which takes image1 and returns a copy rotated 90 degrees clockwise.
(extra credit: allow arbitrary rotation)
 
- Challenge 3: Write a function with the signature: (image1, image2, merge_amount), which returns an object 
in the format above with each pixel of image1 and image2 merged according to merge amount, such that 
merge_amount=0 is all image1, merge_amount=1 is all image2 and merge_amount=0.5 is a 50% mix of the two images. 
(extra credit: implement more than one blending mode)

