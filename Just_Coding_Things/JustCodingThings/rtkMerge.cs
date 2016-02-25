using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JustCodingThings
{
    class rtkMerge
    {
        public static void MergeTest()
        {
            List<int> numbers = new List<int>();
            List<int> result;
            Random randomizer = new Random();

            for (int i = 0; i < 19; i++)
            {
                numbers.Add(randomizer.Next(15));
            }

            rtkMerge merger = new rtkMerge();

            Console.WriteLine("\n\nMERGING: " + merger.printList(numbers));
            result = merger.mergeSort(numbers,0);

            Console.WriteLine("\n\nFINAL RESULT: " + merger.printList(result));
        }

        private List<int> mergeSort(List<int> source, int level)
        {
            if (source.Count <= 1)
            {
                return source;
            }

            int midIndex = source.Count / 2;    // Don't subtract 1 here
            List<int> left = source.GetRange(0, midIndex);  // Essentially count is the index + 1
            List<int> right = source.GetRange(midIndex, source.Count - left.Count); // Start from the midIndex + 1
            Console.WriteLine("\n" + new String('\t', level) + " Bef: " + printList(left, right));

            left = mergeSort(left, level+1);
            right = mergeSort(right, level+1);

            Console.WriteLine("\n" + new String('\t',level) + " Aft: " + printList(left, right));

            return merge(left, right);
        }

        private List<int> merge(List<int> leftList, List<int> rightList)
        {
            List<int> result = new List<int>();

            

            while (leftList.Count > 0 && rightList.Count > 0)
            {
                if (leftList[0] <= rightList[0])
                {
                    result.Add(leftList[0]);
                    leftList.RemoveAt(0);
                }
                else
                {
                    result.Add(rightList[0]);
                    rightList.RemoveAt(0);
                }
            }

            while (leftList.Count > 0)
            {
                result.Add(leftList[0]);
                leftList.RemoveAt(0);
            }

            while (rightList.Count > 0)
            {
                result.Add(rightList[0]);
                rightList.RemoveAt(0);
            }
            return result;
        }

        private string printList(List<int> source)
        {
            return printList(source, new List<int>());
        }
             
        private string printList(List<int> first, List<int> second)
        {
            StringBuilder result = new StringBuilder();
            if (first.Count > 0)
            {
                result.Append("F[" + first[0]);
                for (int index = 1; index < first.Count; index++)
                {
                    result.Append(", " + first[index]);
                }
                result.Append("]  ");
            }

            if (second.Count > 0)
            {
                result.Append("|| S[" + second[0]);
                for (int index = 1; index < second.Count; index++)
                {
                    result.Append(", " + second[index]);
                }
                result.Append("]");
            }

            return result.ToString();
        }

    }
}
