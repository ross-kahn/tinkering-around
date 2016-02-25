#include <sifteo.h>
#include "logic/beast.h"

class BeastCubeManager
{
    public:
        BeastCubeManager(Game* g): game(g)
        {
        }

        void init(CubeID cube, Beast beast)
        {
            Events::cubeTouch.set(&BeastCubeManager::onCubeTouch, this);
            Events::neighborAdd.set(&BeastCubeManager::onNeighborAdd, this);
            Events::neighborRemove.set(&BeastCubeManager::onNeighborRemove, this);
    
            vid[cube].attach(cube);
            beasts[cube] = beast;
            cubeTargets[cube] = -1;
            
            refresh(cube);
        }
        
        void refresh(int cube)
        {
            Beast* beast = &beasts[cube];
            
            if(beast->health > 0)
            {
                vid[cube].initMode(BG0_SPR_BG1);
                vid[cube].bg0.image(vec(0,0), getBeastImage(beast->type));

                vid[cube].bg1.fillMask(vec(0,10), vec(16,6));
                vid[cube].bg1.fill(Font);
                String<11> str;
                str << "Attack:" << beast->attack;
                vid[cube].bg1.text(vec(0, 10), Font, str.c_str());
                str.clear();

                str << "Resolve:" << beast->resolve;
                vid[cube].bg1.text(vec(0, 12), Font, str.c_str());
                str.clear();

                str << "Health:" << beast->health;
                vid[cube].bg1.text(vec(0, 14), Font, str.c_str());
            }
            else
                killBeast(cube);
        }
        
        Beast beasts[CUBE_ALLOCATION];

    private:


        void onCubeTouch(unsigned id)
        {
            LOG("Cube %i: touch event\n", id);

            CubeID cube(id);

            if(cube.isTouching())
            {
                if(cubeTargets[id] >= 0)
                {
                    Beast* targetBeast = &beasts[cubeTargets[id]];

                    //can't have null object in current beasts array, 
                    //so checking to see if beast has attack in order to
                    //see if the target is a beast cube
                    if(targetBeast->health > 0 && beasts[id].health > 0)
                    {
                        game->beastClash(&beasts[id], targetBeast);
                        
                        refresh(id);
                        refresh(cubeTargets[id]);
                    }
                }
            }
        } 

        void onNeighborAdd(unsigned first, unsigned firstSide, unsigned second, unsigned secondSide)
        {
            //LOG("Cube %i: neighbored with %i\n", first, second);
            cubeTargets[first] = second;
            cubeTargets[second] = first;
        }

        void onNeighborRemove(unsigned first, unsigned firstSide, unsigned second, unsigned secondSide)
        {
            //LOG("Cube %i: de-neighbored with %i\n", first, second);
            if(cubeTargets[first] == second)
            {
                cubeTargets[first] = -1;
            }

            if(cubeTargets[second] == first)
            {
                cubeTargets[second] = -1;
            }
        }

        void killBeast(unsigned id)
        {
            LOG("Kilt beast %i\n", id);
            vid[id].bg0.image(vec(0,0), Dead);
            vid[id].bg1.erase();
        }


        Game* game;
        VideoBuffer vid[3];
        int cubeTargets[3];

};

