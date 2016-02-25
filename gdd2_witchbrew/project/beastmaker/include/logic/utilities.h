#pragma once

#include <sifteo.h>


///
//This class can be used for useful static functions that don't 
//quite fit anywhere else.
class U
{

    public:

        ///
        //Return a number between min and max, inclusive. 
        //
        //This is defined here, theoretically so you don't
        //need to create a new Sifteo::Random every time. 
        //That didn't work very well though. 
        static int random(int min, int max)
        {
            Sifteo::Random rand;

            return rand.randint(min, max);
        }

    private:
        //static Sifteo::Random rand;

};

