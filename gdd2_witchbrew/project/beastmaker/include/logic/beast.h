#pragma once

#include <sifteo/array.h>
#include <sifteo/string.h>

using namespace Sifteo;

//Representation of Beast stats and actions. Use beast.type to identify
//the type of monster (this is the index of the beast's name in beastFactory.h).
//
//Beasts are not meant to be created directly using this class. Instead, 
//use beastFactory::createBeast (requires list of cards).
class Beast
{
    public:
        Beast():attack(0), health(0), resolve(0), anger(0), type(-1), name("")
        {}

        /// Parameter constructor
        //@param    n       name of monster
        //@param    t       BeastID (index of name)
        //@param    a       attack strength
        //@param    r       resolve strength
        //@param    ang     anger (beast volatility)
        Beast(const char* n, int t, int a, int h, int r, int ang):
            attack(a), health(h), resolve(r), anger(ang), type(t), name(n)
        {}


        ///
        //This beast attacks defender! It is very effective!
        //
        //Represents beast clash. This beast's attack is subtracted
        //from defender's health. Critical hit calculation should also
        //occur here.
        void damage(Beast* defender)
        {
            defender->health -= attack;    
        }

        ///
        //Return name of monster.
        const char* getName()
        {
            return name;
        }


        bool isDead()
        {
            return health <= 0;
        }

        //Attack strength
        int attack;

        //Health points
        int health;

        //Resolve strength
        int resolve;

        //Anger (beast volatility)
        int anger;

        //BeastID (index of this monster's name in BeastFactory::monsterNames
        int type;

    protected:
        const char* name;
};

//Predefined Sifteo list of Beasts
typedef Sifteo::Array<Beast, 10> BeastList;
