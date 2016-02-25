#pragma once

//CONSTANTS
#define MIN_CARD_VALUE 5
#define MAX_CARD_VALUE 20

#include <sifteo/array.h>
#include "utilities.h"
#include "card.h"

class CardFactory
{
    public:

        ///
        //@param    number  The number of cards you wish to get.
        static CardList getCards(int number)
        {
            CardList cards = CardList(); 
            for(int i = 0; i < number; i++)
            {
                //determine type
                CardType type = (CardType)(U::random(0, NUM_CARD_TYPES - 1));
                
                //MIN_CARD_VALUE <= random power <= MAX_CARD_VALUE
                int power = MIN_CARD_VALUE + U::random(0, MAX_CARD_VALUE - MIN_CARD_VALUE);
                cards.push_back(Card(type, power));  
            }
            
            return cards;
        }
        
        static void getCards(CardList* cards, int num)
        {
            cards->clear();
            for(int i = 0; i < num; i++)
            {
                //determine type
                CardType type = (CardType)(U::random(0, NUM_CARD_TYPES - 1));
                
                //MIN_CARD_VALUE <= random power <= MAX_CARD_VALUE
                int power = MIN_CARD_VALUE + U::random(0, MAX_CARD_VALUE - MIN_CARD_VALUE);
                cards->push_back(Card(type, power));  
            }
        }
       
};
