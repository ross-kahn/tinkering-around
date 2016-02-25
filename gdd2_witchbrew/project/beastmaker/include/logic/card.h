#pragma once

#define MULT_POWER_SHAKE 2
#define MULT_VOLAT_SHAKE 4

#include <sifteo/array.h>

//These enum values can be referenced as words but easily convered to ints
//In any file including card.h, simply reference Fire, Earth, or Water.
enum CardType { Fire=0, Earth, Water, NUM_CARD_TYPES=3 };

//Description of each card type
static const char* CardTypeDescriptions[] = {"Essence of Fire", "Essence of Vitality", "Essence of Resolve"};


///
//Representation of card stats. Do not create this class directly -- use CardFactory::getCards instead.
class Card
{
    public:
        Card(): type(Fire), power(0), power_multiplier(1), volatility_multiplier(1), shaken(false)
        {}
        
        ///
        //Create a card of given type and power. Do not use this manually -- use CardFactory::getCards.
        Card(CardType t, int p): 
            type(t), basePower(p), power(p), power_multiplier(1), volatility_multiplier(1), shaken(false)
        {}

        
        const char* getTypeDescription()
        {
            return CardTypeDescriptions[type];
        }


        ///
        //Pass true to apply shaken multipliers, or false to undo shaken multipliers.
        //
        //@param    shake   true to shake this card, false to set to unshaken
        void shake()
        {
         //   if(shaken)
         //   {
         //       power_multiplier += MULT_POWER_SHAKE;
         //       volatility_multiplier += MULT_VOLAT_SHAKE;
         //   }
         //   else
         //   {
         //       power_multiplier -= MULT_POWER_SHAKE;
         //       volatility_multiplier -= MULT_VOLAT_SHAKE;
         //   }

         //   shaken = !shaken;
            if(shaken)
            {
                power = basePower;
            }
            else
            {
                power = basePower * MULT_POWER_SHAKE;
            }

            shaken = !shaken;
        }


        CardType type;

        //base power of card
        int basePower;

        //current power of card
        int power;

        //Base power * power_multiplier = actual power
        int power_multiplier;

        //volatility is simply power for now
        int volatility_multiplier;

        //is the card in a shaken state?
        bool shaken;
    protected:
        
        


};

typedef Sifteo::Array<Card, 10, uint8_t> CardList;

