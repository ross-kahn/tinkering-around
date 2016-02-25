using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JustCodingThings
{
    class rtkHeap
    {
        private List<int> elements;
        public int count { get; private set; }

        public static void heapTest()
        {
            int[] lines = { 4, 6, 9, 2, 3, 4, 5, 6, 7, 81, 2, 9, 2, 2 };

            rtkHeap heap = new rtkHeap();

            foreach (int value in lines)
            {
                Console.WriteLine("Pushing: " + value);
                Console.ReadKey();
                heap.push(value);
            }
            

            while (heap.count > 0)
            {
                Console.WriteLine("Popping: " + heap.pop());
                Console.ReadKey();
            }
        }

        public rtkHeap()
        {
            elements = new List<int>();
            count = 0;
        }

        public void push(int value)
        {
            elements.Add(value);
            count = elements.Count;
            heapify();
        }

        public int pop()
        {
            if (elements.Count > 0)
            {
                int lastIndex = elements.Count - 1;
                int retVal = elements[0];

                elements[0] = elements[lastIndex];
                elements.RemoveAt(lastIndex);
                count = elements.Count;

                heapify();
                
                return retVal;
            }
            else
            {
                return 0;
            }
            
        }

        private void heapify()
        {
            for (int i = elements.Count - 1; i > 0; i--)
            {
                int parentPos = (i+1) / 2 - 1;
                if (parentPos < 0) {parentPos = 0;}

                if (elements[parentPos] > elements[i]){
                    int temp = elements[i];
                    elements[i] = elements[parentPos];
                    elements[parentPos] = temp;
                }
            }
        }

        public override string ToString()
        {

            return base.ToString();
        }

        private int findBase()
        {
            int i = 1;
            int sum = 0;
            while (i <= count)
            {
                sum = sum + i;

                if (sum == count){ return i;}
                else{i++;}
            }
            return 0;
        }
    }
}
