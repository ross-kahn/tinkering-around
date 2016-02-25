#pragma once

#include <sifteo.h>
#include <sifteo/array.h>
#include <sifteo/menu.h>
#include "include/logic/cardFactory.h"
#include "assets.gen.h"
#include "include/logic/card.h"

using namespace Sifteo;

// The assets for the menu
		static struct MenuAssets gAssets = {&BgTile, &Footer, &LabelEmpty, {&Tip0, &Tip1, &Tip2, NULL}};


///
//This class is the menu used to display hands on a cube
class Hand
{
    public:

        static TiltShakeRecognizer motion;
		
        
        // The video buffer for the cube displaying the hand
		static VideoBuffer handBuffer;

        static CardList playerOneCards;

		//static CardList playerTwoCards;

        // The menu items to populate the menu for player one's hand
        static MenuItem playerOneHand[10];

		// The menu items to populate the menu for player two's hand
		//static MenuItem playerTwoHand[10];

		// ID of the cube displayin the hand
		static CubeID handCube;

        static int currentCardIndex; 

        
        Hand(CubeID handCubeID)
        {
			handCube = handCubeID;
			handBuffer.initMode(BG0);
			handBuffer.attach(handCube);
			handBuffer.bg0.erase(StripeTile);

            motion.attach(handCubeID);
            
            Events::cubeAccelChange.set(&Hand::onAccelChange, this);
		}

        Card* getCard(int index)
        {
            return &playerOneCards[index];
        }

        void onAccelChange(unsigned id)
        {
            unsigned changeFlags = motion.update();
            if(changeFlags)
            {
                LOG("motion changeFlag %i\n", changeFlags);
                if(motion.shake)
                {
                    getCard(Hand::currentCardIndex)->shake();
                    LOG("Deck has been shook! Card power is...%i!\n", getCard(Hand::currentCardIndex)->power);
                }
            }                
        }


		static void drawCard(int player)
		{
			CardList tempList = CardFactory::getCards(1);
			Card tempCard = tempList[0];
			if(player == 1)
			{
				playerOneCards.push_back(tempCard);
				//convertToMenu(1, playerOneCards);
			}
	//		else
	//		{
	//			playerTwoCards.push_back(tempCard);
	//			convertToMenu(2, playerTwoCards);
	//		}
		}

        static void drawHand()
        {
            CardFactory::getCards(&Hand::playerOneCards, 5);
            Hand::convertToMenu(1, &Hand::playerOneCards);
        }
		
        static void convertToMenu(int player, CardList* passedListPtr)
		{   
            CardList passedList = *passedListPtr;
            int i = passedList.count();
			if(passedList[2].type == 0)
			{
				i++;
			}
			playerOneHand[i] = (MenuItem){&red5, NULL};
			//if(player == 1){
			//	playerOneCards = passedList;
			for(int i = 0; i < passedList.count(); i++)
			{
				if(passedList[i].type == 0)
				{
					switch(passedList[i].power){

					case 5:
						playerOneHand[i] = (MenuItem){&red5, NULL};
						break;
					case 6:
						playerOneHand[i] = (MenuItem){&red6, NULL};
						break;
					case 7:
						playerOneHand[i] = (MenuItem){&red7, NULL};
						break;
					case 8:
						playerOneHand[i] = (MenuItem){&red8, NULL};
						break;
					case 9:
						playerOneHand[i] = (MenuItem){&red9, NULL};
						break;
					case 10:
						playerOneHand[i] = (MenuItem){&red10, NULL};
						break;
					case 11:
						playerOneHand[i] = (MenuItem){&red11, NULL};
						break;
					case 12:
						playerOneHand[i] = (MenuItem){&red12, NULL};
						break;
					case 13:
						playerOneHand[i] = (MenuItem){&red13, NULL};
						break;
					case 14:
						playerOneHand[i] = (MenuItem){&red14, NULL};
						break;
					case 15:
						playerOneHand[i] = (MenuItem){&red15, NULL};
						break;
					case 16:
						playerOneHand[i] = (MenuItem){&red16, NULL};
						break;
					case 17:
						playerOneHand[i] = (MenuItem){&red17, NULL};
						break;
					case 18:
						playerOneHand[i] = (MenuItem){&red18, NULL};
						break;
					case 19:
						playerOneHand[i] = (MenuItem){&red19, NULL};
						break;
					case 20:
						playerOneHand[i] = (MenuItem){&red20, NULL};
						break;
					default:
						break;
				}


			}
			else if (passedList[i].type == 1)
			{
				switch(passedList[i].power){

				case 5:
					playerOneHand[i] = (MenuItem){&green5, NULL};
					break;
				case 6:
					playerOneHand[i] = (MenuItem){&green6, NULL};
					break;
				case 7:
					playerOneHand[i] = (MenuItem){&green7, NULL};
					break;
				case 8:
					playerOneHand[i] = (MenuItem){&green8, NULL};
					break;
				case 9:
					playerOneHand[i] = (MenuItem){&green9, NULL};
					break;
				case 10:
					playerOneHand[i] = (MenuItem){&green10, NULL};
					break;
				case 11:
					playerOneHand[i] = (MenuItem){&green11, NULL};
					break;
				case 12:
					playerOneHand[i] = (MenuItem){&green12, NULL};
					break;
				case 13:
					playerOneHand[i] = (MenuItem){&green13, NULL};
					break;
				case 14:
					playerOneHand[i] = (MenuItem){&green14, NULL};
					break;
				case 15:
					playerOneHand[i] = (MenuItem){&green15, NULL};
					break;
				case 16:
					playerOneHand[i] = (MenuItem){&green16, NULL};
					break;
				case 17:
					playerOneHand[i] = (MenuItem){&green17, NULL};
					break;
				case 18:
					playerOneHand[i] = (MenuItem){&green18, NULL};
					break;
				case 19:
					playerOneHand[i] = (MenuItem){&green19, NULL};
					break;
				case 20:
					playerOneHand[i] = (MenuItem){&green20, NULL};
					break;
				default:
					break;
				}

			}
			else
			{
				switch(passedList[i].power){
				case 5:
					playerOneHand[i] = (MenuItem){&blue5, NULL};
					break;
				case 6:
					playerOneHand[i] = (MenuItem){&blue6, NULL};
					break;
				case 7:
					playerOneHand[i] = (MenuItem){&blue7, NULL};
					break;
				case 8:
					playerOneHand[i] = (MenuItem){&blue8, NULL};
					break;
				case 9:
					playerOneHand[i] = (MenuItem){&blue9, NULL};
					break;
				case 10:
					playerOneHand[i] = (MenuItem){&blue10, NULL};
					break;
				case 11:
					playerOneHand[i] = (MenuItem){&blue11, NULL};
					break;
				case 12:
					playerOneHand[i] = (MenuItem){&blue12, NULL};
					break;
				case 13:
					playerOneHand[i] = (MenuItem){&blue13, NULL};
					break;
				case 14:
					playerOneHand[i] = (MenuItem){&blue14, NULL};
					break;
				case 15:
					playerOneHand[i] = (MenuItem){&blue15, NULL};
					break;
				case 16:
					playerOneHand[i] = (MenuItem){&blue16, NULL};
					break;
				case 17:
					playerOneHand[i] = (MenuItem){&blue17, NULL};
					break;
				case 18:
					playerOneHand[i] = (MenuItem){&blue18, NULL};
					break;
				case 19:
					playerOneHand[i] = (MenuItem){&blue19, NULL};
					break;
				case 20:
					playerOneHand[i] = (MenuItem){&blue20, NULL};
					break;
				default:
					break;
				}

				}
			}
			
			for(int x = passedList.count(); x < 10; x++)
			{
				playerOneHand[x] = (MenuItem){NULL, NULL};
			}
			
			
			
	//		if(player == 2){
	//		playerTwoCards = passedList;
	//		for(int i = 0; i < passedList.count(); i++)
	//		{
	//			if(passedList[i].type == 0)
	//			{
	//				switch(passedList[i].power){

	//				case 5:
	//					playerTwoHand[i] = (MenuItem){&red5, NULL};
	//					break;
	//				case 6:
	//					playerTwoHand[i] = (MenuItem){&red6, NULL};
	//					break;
	//				case 7:
	//					playerTwoHand[i] = (MenuItem){&red7, NULL};
	//					break;
	//				case 8:
	//					playerTwoHand[i] = (MenuItem){&red8, NULL};
	//					break;
	//				case 9:
	//					playerTwoHand[i] = (MenuItem){&red9, NULL};
	//					break;
	//				case 10:
	//					playerTwoHand[i] = (MenuItem){&red10, NULL};
	//					break;
	//				case 11:
	//					playerTwoHand[i] = (MenuItem){&red11, NULL};
	//					break;
	//				case 12:
	//					playerTwoHand[i] = (MenuItem){&red12, NULL};
	//					break;
	//				case 13:
	//					playerTwoHand[i] = (MenuItem){&red13, NULL};
	//					break;
	//				case 14:
	//					playerTwoHand[i] = (MenuItem){&red14, NULL};
	//					break;
	//				case 15:
	//					playerTwoHand[i] = (MenuItem){&red15, NULL};
	//					break;
	//				case 16:
	//					playerTwoHand[i] = (MenuItem){&red16, NULL};
	//					break;
	//				case 17:
	//					playerTwoHand[i] = (MenuItem){&red17, NULL};
	//					break;
	//				case 18:
	//					playerTwoHand[i] = (MenuItem){&red18, NULL};
	//					break;
	//				case 19:
	//					playerTwoHand[i] = (MenuItem){&red19, NULL};
	//					break;
	//				case 20:
	//					playerTwoHand[i] = (MenuItem){&red20, NULL};
	//					break;
	//				default:
	//					break;
	//			}


	//		}
	//		else if (passedList[i].type == 1)
	//		{
	//			switch(passedList[i].power){

	//			case 5:
	//				playerTwoHand[i] = (MenuItem){&green5, NULL};
	//				break;
	//			case 6:
	//				playerTwoHand[i] = (MenuItem){&green6, NULL};
	//				break;
	//			case 7:
	//				playerTwoHand[i] = (MenuItem){&green7, NULL};
	//				break;
	//			case 8:
	//				playerTwoHand[i] = (MenuItem){&green8, NULL};
	//				break;
	//			case 9:
	//				playerTwoHand[i] = (MenuItem){&green9, NULL};
	//				break;
	//			case 10:
	//				playerTwoHand[i] = (MenuItem){&green10, NULL};
	//				break;
	//			case 11:
	//				playerTwoHand[i] = (MenuItem){&green11, NULL};
	//				break;
	//			case 12:
	//				playerTwoHand[i] = (MenuItem){&green12, NULL};
	//				break;
	//			case 13:
	//				playerTwoHand[i] = (MenuItem){&green13, NULL};
	//				break;
	//			case 14:
	//				playerTwoHand[i] = (MenuItem){&green14, NULL};
	//				break;
	//			case 15:
	//				playerTwoHand[i] = (MenuItem){&green15, NULL};
	//				break;
	//			case 16:
	//				playerTwoHand[i] = (MenuItem){&green16, NULL};
	//				break;
	//			case 17:
	//				playerTwoHand[i] = (MenuItem){&green17, NULL};
	//				break;
	//			case 18:
	//				playerTwoHand[i] = (MenuItem){&green18, NULL};
	//				break;
	//			case 19:
	//				playerTwoHand[i] = (MenuItem){&green19, NULL};
	//				break;
	//			case 20:
	//				playerTwoHand[i] = (MenuItem){&green20, NULL};
	//				break;
	//			default:
	//				break;
	//			}

	//		}
	//		else
	//		{
	//			switch(passedList[i].power){
	//			case 5:
	//				playerTwoHand[i] = (MenuItem){&blue5, NULL};
	//				break;
	//			case 6:
	//				playerTwoHand[i] = (MenuItem){&blue6, NULL};
	//				break;
	//			case 7:
	//				playerTwoHand[i] = (MenuItem){&blue7, NULL};
	//				break;
	//			case 8:
	//				playerTwoHand[i] = (MenuItem){&blue8, NULL};
	//				break;
	//			case 9:
	//				playerTwoHand[i] = (MenuItem){&blue9, NULL};
	//				break;
	//			case 10:
	//				playerTwoHand[i] = (MenuItem){&blue10, NULL};
	//				break;
	//			case 11:
	//				playerTwoHand[i] = (MenuItem){&blue11, NULL};
	//				break;
	//			case 12:
	//				playerTwoHand[i] = (MenuItem){&blue12, NULL};
	//				break;
	//			case 13:
	//				playerTwoHand[i] = (MenuItem){&blue13, NULL};
	//				break;
	//			case 14:
	//				playerTwoHand[i] = (MenuItem){&blue14, NULL};
	//				break;
	//			case 15:
	//				playerTwoHand[i] = (MenuItem){&blue15, NULL};
	//				break;
	//			case 16:
	//				playerTwoHand[i] = (MenuItem){&blue16, NULL};
	//				break;
	//			case 17:
	//				playerTwoHand[i] = (MenuItem){&blue17, NULL};
	//				break;
	//			case 18:
	//				playerTwoHand[i] = (MenuItem){&blue18, NULL};
	//				break;
	//			case 19:
	//				playerTwoHand[i] = (MenuItem){&blue19, NULL};
	//				break;
	//			case 20:
	//				playerTwoHand[i] = (MenuItem){&blue20, NULL};
	//				break;
	//			default:
	//				break;
	//			}

	//			}
	//		}
	//		
	//		for(int x = passedList.count(); x < 10; x++)
	//		{
	//			playerTwoHand[x] = (MenuItem){NULL, NULL};
	//		}
	//		}
	//		
		}
        


    protected:
        
        


};

int Hand::currentCardIndex = -1;

TiltShakeRecognizer Hand::motion;

CardList Hand::playerOneCards; 

  //CardList Hand::playerTwoCards; 

MenuItem Hand::playerOneHand[10];

//MenuItem Hand::playerTwoHand[10];

 VideoBuffer Hand::handBuffer;

 CubeID Hand::handCube;
