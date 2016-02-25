This program was built and tested using Java version 1.6.0_25.

To run from command line, CD to the working directory of this file. Compile using the command

$ javac spotify/*.java

To run using the test input file, use the command

$ java spotify/Main < testInput.txt 


Thanks!
-Ross

-----------------------------------------------------
testInput.txt

s# = Stockholm
L# = London
p# = Project number

	p1  p2  p3  p4  p5  p6
S1	x       x       x
S2          x 
S3                  x
S4                          x
L1	x   x  
L2              x   x  
L3                      x   x

Optimal: L1, L2, L3
Greedy: S1, S2, S3, S4