#pragma once

#include "card.h"
#include "beast.h"
#include "utilities.h"

///
//These constants are used to define base monster stats
//Given a card, the type of card determines whether to add to ATTACK, HEALTH, or RESOLVE
//The formula is as follows:
//X_BASE + card.power * X_MULTIPLIER
#define ATTACK_BASE 30
#define ATTACK_MULTIPLIER 1
#define HEALTH_BASE 100
#define HEALTH_MULTIPLIER 3
#define RESOLVE_BASE 10
#define RESOLVE_MULTIPLIER 1
#define ANGER_BASE 0
#define ANGER_MULTIPLIER 1

///
//Currently monsters are divided by primary and secondary color types. 
//These constants define how many monsters there are for each primary color
//and how many for each secondary color beneath the primary.
#define BEASTS_PER_SECONDARY 3
#define BEASTS_PER_PRIMARY 9

///
//Hardcoded list of beast names. The index of the monster's name in this
//array is the type (BeastID) of the monster.
static const char* beastNames[] = {
"Pure Energy Being",					//0
"Plasma Demon",							//1
"Crakehound",							//2
"Slagwurm",								//3
"Werewolf",								//4
"Magma Blob",							//5
"Fire Drake",							//6
"Phoenix",								//7
"Fireflies",							//8
"Sludge Behemoth",						//9
"Assassin Spider",						//10
"Mountain Wolf",						//11
"Diamond Chrysalid",					//12
"Man-eating Plant",						//13
"Wirrakir Axeman",						//14
"Tengu",								//15
"Sea Urchin",							//16
"Flying Squirrel",						//17
"Planes Walker",						//18
"Vampire",								//19
"Emperor Wasps",						//20
"Faceless One",							//21
"Doppleganger",							//22
"Will-o'-the-Wisps",					//23
"Chaos Wanderer",						//24
"Banshee",								//25
"Fairy Archer"};						//26


class BeastFactory
{
    public:

        ///
        //An arbitrary number of cards will be accepted.
        static Beast createBeast(const Card cards[], int numCards)
        {

            
            int primaryPower = 0;
            int primaryType = 0;
            int secondaryPower = 0;
            int secondaryType = 0;
            int attack = ATTACK_BASE;
            int health = HEALTH_BASE;
            int resolve = RESOLVE_BASE;
            int anger = ANGER_BASE;

            for(int i = 0; i < numCards; i++)
            {
                const Card* c = &(cards[i]);
            
                
                //if current card is more powerful than primary
                //  set secondary to former primary card
                //  set primary card to current card
                //else if current card is more powerful than secondary
                //  set secondary to current card
                if(c->power > primaryPower)
                {
                    secondaryPower = primaryPower;
                    secondaryType = primaryType;
                    primaryPower = c->power;
                    primaryType = c->type;
                }
                else if(c->power > secondaryPower)
                {
                    secondaryPower = c->power;
                    secondaryType = c->type;
                }

                //Add to card's power type.
                //Card power is multiplied by constant multipliers 
                //(example: health is x3 when added in creation.)
                switch(c->type)
                {
                    case Fire:
                        attack += c->power * ATTACK_MULTIPLIER;
                        break;
                    case Earth:
                        health += c->power * HEALTH_MULTIPLIER;
                        break;
                    case Water:
                        resolve += c->power * RESOLVE_MULTIPLIER;
                        break;
                    default:
                        LOG("\n###default in beastFactory cardType -- unknown type\n");
                        break;
                }

                //no matter the card type, add to anger
                anger += c->power * ANGER_MULTIPLIER;
            }

            //set both to zero to lock to Red/red types
            //primaryType = 0;
            //secondaryType = 0;

			
            //Using a flat array to represent the primary/secondary relationship
            //This beastTypeIndex is the beast's type.
            //Example: beastID of Assassin Spider, Green/Red type
            //primaryType = Green = 1 * BEASTS_PER_PRIMARY
            //This skips over monsters of primary type Red (0-8) to start at index 9.
            //secondaryType = Red = 0 * BEASTS_PER_PRIMARY
            //Since Red is the first type, this does not skip any monsters. Our index is 9 + 0 = 9;
            //The last step is to pick one of the Green/Red types at random. If U::random returns 
            //1, our index is 9 + 1 = 10, which is the Assassin Spider.        
            int beastTypeIndex = primaryType * BEASTS_PER_PRIMARY + 
                                   secondaryType * BEASTS_PER_SECONDARY +
                                   U::random(0, BEASTS_PER_SECONDARY - 1); 

            return Beast(beastNames[beastTypeIndex], beastTypeIndex, attack, health, resolve, anger);
        } 
};
