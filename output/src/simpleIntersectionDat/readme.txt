The codes may look messy. I will fix them and add more comments later.

The class lists are created by intersection.java, which runs based on some pre-processed lists.
To create those lists, do the following:

1. Run the file CreatList.java (you need to remove the lines that comment out the codes, of course).

2. Run the first part of the file  CreatCurveList.java. (one that says "Create and clean word lists").
Again, I commented out the codes, so you need to first un-comment them (but leave the second part unchanged).
Run the file four times with "n" being "1", "2", "3", and "4".

3. Run the second part of the code (one that says "Create clean curve lists").
Now you comment out the first part of the code, and un-comment the second part.
Again, run the file four times with "n" being "1", "2", "3", and "4".
For the case n = 4, the program may take several minutes, as the output is huge.

The code from line 54 - line 61 only works for the case n = 4. Changing them when n = 1, 2, 3 is pretty easy.
However, if you don't want to do so, just replace them with the code from line 31 - line 34 (one that says "Bad code for easy cases").
This bad code works for the case n = 4 too, but it will be much slower.

4. Now open the file intersection.java. Call the method PantsSimInterEqClass(i) for i = 1, 2, 3, and 4.
Again, the case i = 4 may take several minutes.
If you just want to find the equivalence class of a particular curve, call the method PantsSimInterEqWith(word) instead.
I included that method in the main method, and commented it out, so you can just un-comment it.