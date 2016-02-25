/*
 * Sifteo SDK Example.
 */

#include <sifteo.h>
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

using namespace Sifteo;

const static int numCubes = CUBE_ALLOCATION;

static AssetSlot MainSlot = AssetSlot::allocate()
    .bootstrap(Monsters);

static AssetSlot DeadSlot = AssetSlot::allocate()
    .bootstrap(OtherGroup);

static Metadata M = Metadata()
    .title("Beastmaker")
    .package("beastmaker", "1.0")
    .icon(Icon)
    .cubeRange(0, CUBE_ALLOCATION);






void main()
{
    BeastCubeManager beastCube;
    

    for(CubeID cube : CubeSet::connected())
    {
        //LOG("###---Player %i---\n", cube);

        CardList c = (CardFactory::getCards(2));  

        for(int i = 0; i < c.count(); i++)
        {
            LOG("###%i %i\n", c[i].type, c[i].power);
        }
         
        Card beastCards[] = {c.pop_back(), c.pop_back()};
        Beast m = BeastFactory::createBeast(beastCards, 2);

        LOG("###Player %i beast\nType: %i\nAttack:%i\nHealth:%i\nResolve:%i\nAnger:%i\n\n",
                -1, m.type, m.attack, m.health, m.resolve, m.anger);

        beastCube.init(cube, m);     

    }

    while(1)
        System::paint();
    
}
