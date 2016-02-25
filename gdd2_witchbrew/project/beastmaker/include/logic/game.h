#pragma once

#include <sifteo/array.h>
#include "player.h"


#define HUNKER_DAMAGE 0
#define HUNKER_PROTECTION 0.5

#define CARD_ATTACK_MULTIPLIER 2
#define CARD_HEAL_MULTIPLIER 2
#define CARD_RESOLVE_MULTIPLIER 1

enum TurnPhase { PotionOne=0, PotionTwo, Clash };

///
//Game will be the workhorse class. This keeps track of every Player object,
//and contains the hooks for battling monster and actually playing the game.
//Methods that implement actual game logic as opposed to Sifteo implementation
//should go here. 
//
//For example, Sifteo code should determine which beast is next to which defender
//during beast clash, but should pass those to beastClash as opposed to dealing with
//game logic itself.
class Game
{
    public:

        Game()
        {
        }

		int activePlayerIndex = 0;
		
		bool newTurn = true;

		TurnPhase phase = PotionOne;

        ///
        //Initiate clash between the attacker and the defender. 
        //This will handle the anger events and damage dealt.
        void beastClash(Beast* attacker, Beast* defender)
        {
            attacker->damage(defender);
            defender->damage(attacker);

			// Move to next player's turn
			nextTurn();
        }

		// Moves the turn to the next phase, if it is the last phase, moves to the next player
		void nextPhase()
		{
			if(phase == PotionOne)
				phase = PotionTwo;
			else if(phase == PotionTwo)
				phase = Clash;
			else 
				nextTurn();
		}
		
		// Moves on to the next player's turn
		void nextTurn()
		{
			phase = PotionOne;
			if(activePlayerIndex == 0)
				activePlayerIndex = 1;
			else
				activePlayerIndex = 0;
			newTurn = true;
		}


        void cardAction(Beast* beast, Card* card)
        {
			if(phase == PotionOne || phase == PotionTwo)
			{
				switch(card->type)
				{
				    case Fire:
				        LOG("Fire card on beast: %i - %i health\n", beast->health, card->power);
				       beast->health -= card->power * CARD_ATTACK_MULTIPLIER;
				       break;
				    case Earth:
				       LOG("Earth card on beast: %i + %i health\n", beast->health, card->power);
				       beast->health += card->power * CARD_HEAL_MULTIPLIER;
				        break;
				   case Water:
				       LOG("Water card on beast: %i + %i resolve\n", beast->resolve, card->power);
				       beast->resolve += card->power * CARD_RESOLVE_MULTIPLIER;
				       break;
				   default:
				        LOG("default case in game::cardAction\n");
				       break;
			   }
			   nextPhase();
			}
			

        }


        ///
        //Check every player's list of beasts to determine if anyone
        //has won yet. If someone has, return their player index. Otherwise, return -1.
        //
        //The game is over when at only at most one player has any living beasts. The
        //winning player is currently defined as the one with the least dead beast.
        //
        //Currently, if two least dead beasts tie, the first one found wins.
        //int whoWon()
        //{
        //    int maxHealth = -1000;
        //    int winningPlayer = -1;
        //    int numDead = 0;
        //    for(int i = 0; i < players.count(); ++i)
        //    {

        //        //Get a direct reference to the player object to make referencing easier.
        //        //Important note: Player p = players[i] would not work.
        //        //p would be a copy of player[i], not a reference to it.
        //        Player* p = &players[i];

        //        bool dead = true;
        //        for(int j = 0; j < p->beasts.count(); ++j)
        //        {
        //            Beast* b = &(p->beasts[j]);
        //            
        //            if(b->health > maxHealth)
        //            {
        //                maxHealth = b->health;
        //                winningPlayer = i;
        //            }

        //            if(b->health > 0)
        //                dead = false;
        //        }

        //        if(dead)
        //            numDead++;
        //            
        //    }
        //    

        //    //If there are two or more living players, 
        //    //the game is not over and there is no winning player...yet.
        //    if((players.count() - numDead) > 1)
        //    {
        //        winningPlayer = -1;
        //    }

        //    return winningPlayer;
        //}

        
        //Sifteo::Array<Player, 2> players;

};
