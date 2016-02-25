/*
 * Sifteo SDK Example.
 */

#include <sifteo.h>
#include <sifteo/menu.h>
#include "assets.gen.h"
#include "include/logic/beastFactory.h"
#include "include/logic/cardFactory.h"
#include "include/logic/beast.h"
#include "include/logic/card.h"
#include "include/logic/player.h"
#include "include/logic/game.h"
#include "include/logic/utilities.h"
#include "include/beastImage.h"
#include "include/beastCube.h"
#include "hand.h"

using namespace Sifteo;

static AssetSlot MainSlot = AssetSlot::allocate()
    .bootstrap(Monsters);

static AssetSlot DeadSlot = AssetSlot::allocate()
    .bootstrap(OtherGroup);

static AssetSlot PotionSlot = AssetSlot::allocate()
    .bootstrap(PotionGroup);

static AssetSlot AssetSlot = AssetSlot::allocate()
    .bootstrap(BetterflowAssets);



static Metadata M = Metadata()
    .title("Beastmaker")
    .package("beastmaker", "1.0")
    .icon(Icon)
    .cubeRange(0, CUBE_ALLOCATION);


void main()
{
    Game game = Game();
    CardList cards;
	//Player playerOne;
    BeastCubeManager beastCube(&game);
	Hand gameHand(0);
	//Hand::playerOneCards = (CardFactory::getCards(5));
	//Hand::playerTwoCards = (CardFactory::getCards(4));
	//Hand::convertToMenu(1, Hand::playerOneCards);
    Hand::drawHand();
	Menu m(Hand::handBuffer, &gAssets, Hand::playerOneHand);
	m.anchor(0);

    //Make beasts    
    for(int cube = 1; cube < CubeSet::connected().count(); ++cube) {
        LOG("###---Player %i---\n", cube);

        //CardList c = (CardFactory::getCards(2));  
        CardFactory::getCards(&cards, 2);

        for(int i = 0; i < cards.count(); i++)
        {
            LOG("###Card for beast creation: %i %i\n", cards[i].type, cards[i].power);
        }
         
        Card beastCards[] = {cards.pop_back(), cards.pop_back()};
        Beast m = BeastFactory::createBeast(beastCards, 2);

        LOG("###Beast created:\n Type: %i\n Attack:%i\n Health:%i\n Resolve:%i\n Anger:%i\n\n",
                m.type, m.attack, m.health, m.resolve, m.anger);

        beastCube.init(cube, m);     
        cards.clear();
    }

	struct MenuEvent e;
    uint8_t item;
    int target = 0;


    while(1)
	{
        System::paint();
		
	while (m.pollEvent(&e)) {
		if(game.newTurn)
		{
		    //LOG("New turn!\n");
            //Hand::drawCard(game.activePlayerIndex + 1);
			//if(game.activePlayerIndex == 0)
			//	m.init(Hand::handBuffer, &gAssets, Hand::playerOneHand);
			//else
			//	m.init(Hand::handBuffer, &gAssets, Hand::playerTwoHand);
            Hand::drawHand();
			game.newTurn = false;
		}
            switch (e.type) {

                case MENU_ITEM_PRESS:
            //        // Game Buddy is not clickable, so don't do anything on press
            //        if (e.item >= 3) {
            //            // Prevent the default action
            //            continue;
            //        } else {
            //            m.anchor(e.item);
            //        }
            //        if (e.item == 4) {
            //            static unsigned randomIcon = 0;
            //            randomIcon = (randomIcon + 1) % e.item;
            //            m.replaceIcon(e.item, Hand::playerOneHand[randomIcon].icon, Hand::playerOneHand[randomIcon].label);
            //        }
                    if(target != 0)
                    {
						Card c;
						c = Hand::playerOneCards[e.item];
                        game.cardAction(&beastCube.beasts[target], &c);
                        beastCube.refresh(target);

                        game.newTurn = true;
                    }
                    break;

                case MENU_EXIT:
                    // this is not possible when pollEvent is used as the condition to the while loop.
                    // NOTE: this event should never have its default handler skipped.
                    ASSERT(false);
                    break;

                case MENU_NEIGHBOR_ADD:
                    //LOG("found cube %d on side %d of menu (neighbor's %d side)\n",
                         //e.neighbor.neighbor, e.neighbor.masterSide, e.neighbor.neighborSide);
                    target = e.neighbor.neighbor;
                    break;

                case MENU_NEIGHBOR_REMOVE:
                    //LOG("lost cube %d on side %d of menu (neighbor's %d side)\n",
                    //     e.neighbor.neighbor, e.neighbor.masterSide, e.neighbor.neighborSide);

                    if(e.neighbor.neighbor == target)
                    {
                        target = 0;
                    }
                    break;

                case MENU_ITEM_ARRIVE:
                    LOG("arriving at menu item %d\n", e.item);
                    Hand::currentCardIndex = item = e.item;
                    break;

                case MENU_ITEM_DEPART:
                    //LOG("departing from menu item %d, scrolling %s\n", item, e.direction > 0 ? "forward" : "backward");
                    break;

                case MENU_PREPAINT:
                    // do your implementation-specific drawing here
                    // NOTE: this event should never have its default handler skipped.
                    break;

                case MENU_UNEVENTFUL:
                    // this should never happen. if it does, it can/should be ignored.
                    ASSERT(false);
                    break;
            }

            m.performDefault();
        }

        // Handle the exit event (so we can re-enter the same Menu)
        ASSERT(e.type == MENU_EXIT);
        m.performDefault();

        //LOG("Selected Game: %d\n", e.item);
	}
}
