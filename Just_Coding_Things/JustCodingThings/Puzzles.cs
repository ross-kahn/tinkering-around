﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JustCodingThings
{
    class Puzzles
    {
        /**
         *  If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
         *  Find the sum of all the multiples of 3 or 5 below 1000
         */
        public void MultiplesOf3And5(int endNum)
        {
            int sum = 0;
            for (int i = 1; i < endNum; i++)
            {
                if ((i % 3 == 0) || (i % 5 == 0)) { sum = sum + i; }
            }
            Console.WriteLine("The sum of multiples of 3 and 5 less than " + endNum + " is " + sum);
        }

        /**
         * Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2, the first 10 terms will be:
         *                                                  1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
         * By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms.
         **/
        public void EvenFibonacciNumbers()
        {
            int sum = 0;
            int fib1 = 1;
            int fib2 = 1;
            int newFib = 0;
            while (newFib <= 4000000)
            {
                newFib = fib1 + fib2;
                fib1 = fib2;
                fib2 = newFib;
                if (newFib % 2 == 0) { sum = sum + newFib; }
            }
            Console.WriteLine("The sum of even fibonacci numbers less than or equal to 4,000,000 is: " + sum);
        }

        public void TrianglePath()
        {
            string[] lines = System.IO.File.ReadAllLines(@"C:\Users\rkahn\Desktop\Documents\Visual Studio 2013\Projects\JustCodingThings\triangles_hard.txt");

            int maxNum = 0;
            int lineIndex = 0;

            int option1;
            int option2;

            int[] currentLine = { };
            int[] nextLine = { };

            if (lines.Length > 0)
            {
                currentLine = lines[0].Trim().Split().Select(int.Parse).ToArray<int>();
            }

            for (int i = 0; i < lines.Length - 1; i++)
            {
                nextLine = lines[i + 1].Trim().Split().Select(int.Parse).ToArray<int>();

                for (lineIndex = 0; lineIndex < nextLine.Length; lineIndex++)
                {
                    if (lineIndex == 0)
                    {
                        nextLine[0] = nextLine[0] + currentLine[0];     // Left edge
                    }
                    else if (lineIndex == nextLine.Length - 1)
                    {
                        nextLine[nextLine.Length - 1] = nextLine[nextLine.Length - 1] + currentLine[currentLine.Length - 1];    // Right edge
                    }
                    else
                    {
                        option1 = nextLine[lineIndex] + currentLine[lineIndex];
                        option2 = nextLine[lineIndex] + currentLine[lineIndex - 1];
                        nextLine[lineIndex] = option1 > option2 ? option1 : option2;    // Choose the larger of the two
                    }
                }
                currentLine = nextLine;
            }

            // Find largest number
            foreach (int val in nextLine)
            {
                if (val > maxNum) { maxNum = val; }
            }

            Console.WriteLine(maxNum);
        }
    }
}
