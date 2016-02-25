#pragma once

#include <sifteo/array.h>
#include "beast.h"
#include "card.h"

class Player
{
    public:
        Player()
        {}
        
        ///
        //Creates a player with the given hand of cards and beasts
        //under control. Do not create this class manually -- use Game::addPlayer instead.
        Player(CardList c, BeastList b): hand(c), beasts(b) {}

        //Player's current hand of cards
        CardList hand;

        //Player's army of beasts
        BeastList beasts;

    protected:
};
