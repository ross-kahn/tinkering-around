using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JustCodingThings
{
    class Program
    {
        static void Main(string[] args)
        {
            Puzzles solutions = new Puzzles();
            while (true)
            {
                Console.WriteLine("Please enter your choice of programs: ");
                Console.WriteLine("1. Mutiples of 3 and 5");
                Console.WriteLine("2. Even Fibonacci Numbers");
                Console.WriteLine("3. Triangle Path");
                Console.WriteLine("4. Heap Test");
                Console.WriteLine("5. Merge Test");
                Console.WriteLine("Enter q to quit");

                string input = Console.ReadLine().Trim();
                switch (input)
                {
                    case "1":
                        Console.Clear();
                        solutions.MultiplesOf3And5(1000);
                        Console.WriteLine("");
                        break;

                    case "2":
                        Console.Clear();
                        solutions.EvenFibonacciNumbers();
                        Console.WriteLine("");
                        break;

                    case "3":
                        Console.Clear();
                        solutions.TrianglePath();
                        Console.WriteLine("");
                        break;

                    case "4":
                        Console.Clear();
                        rtkHeap.heapTest();
                        Console.WriteLine("");
                        break;

                    case "5":
                        Console.Clear();
                        rtkMerge.MergeTest();
                        Console.WriteLine("");
                        break;

                    case "q":
                        return;

                    default:
                        Console.WriteLine("Please enter a valid number.");
                        break;
                }
            }
        }
    }
}
