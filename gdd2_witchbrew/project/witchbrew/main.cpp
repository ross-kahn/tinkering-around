/*
 * Sifteo SDK Example.
 */
#include <sifteo.h>
#include <sifteo/menu.h>
#include "assets.gen.h"
#include "include/ingredient.cpp"
#include "include/potion.cpp"
#include "include/sensorlistener.cpp"
#include "include/witchbrew.cpp"

using namespace Sifteo;

// CONSTANTS DEFINED IN witchbrew.cpp

static AssetSlot MainSlot = AssetSlot::allocate()
    .bootstrap(BetterflowAssets);

static Metadata M = Metadata()
    .title("Witch's Brew")
    .package("witchbrew", "1.0")
    .icon(Icon)
    .cubeRange(numCubes);

void main()
{	
	WitchBrew wb;
	wb.init();
	static SensorListener sensors (wb);

	sensors.install();

	while(1){
		// Draw the background
		System::paint();
		System::finish();
	}

	/**
	Potion potion (5,5,5,5);
	Ingredient ing1 (2,0,2,0);
	Ingredient ing2 (0,2,0,2);
	Ingredient ing3 (3,3,0,0);
	Ingredient ing4 (0,0,3,3);
	potion.print();
	ing1.print();
	ing2.print();
	ing3.print();
	ing4.print();

	potion.add(ing1);
	LOG("-------\nGAME WON? ==> %i\n-------\n", potion.won());

	potion.add(ing2);
	LOG("-------\nGAME WON? ==> %i\n-------\n", potion.won());

	potion.add(ing3);
	LOG("-------\nGAME WON? ==> %i\n-------\n", potion.won());

	potion.add(ing4);
	LOG("-------\nGAME WON? ==> %i\n-------\n", potion.won());

	**/
}