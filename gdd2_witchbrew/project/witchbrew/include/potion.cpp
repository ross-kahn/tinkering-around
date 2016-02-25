//Put important things here

#pragma once
#include <sifteo.h>
#include <sifteo/menu.h>
#include "assets.gen.h"
using namespace Sifteo;

//CLASS Potion
class Potion
{
		int waterGoal, earthGoal, fireGoal, airGoal;
		int curWater, curEarth, curFire, curAir;
	public:
		Potion (int, int, int, int);
		Potion();

		int water () {return curWater;}
		int air () {return curAir;}
		int earth () {return curEarth;}
		int fire () {return curFire;}
		//if any current value is over the goal, returns true
		bool over () {return ((curWater > waterGoal) || (curEarth > earthGoal) || (curFire > fireGoal) || (curAir > airGoal));} 
		//if all current values are equal to the goal, return true
		bool won () {return ((curWater == waterGoal) && (curEarth == earthGoal) && (curFire == fireGoal) && (curFire == fireGoal));}
		//add ingredient's values to the potion
		void add(Ingredient x) 
		{
			curWater += x.getWater();
			curEarth += x.getEarth();
			curFire += x.getFire();
			curAir += x.getAir();
			print();
		}
		//returns true if this potion has goals
		bool exists()
		{
			return ((waterGoal > 0) && (fireGoal > 0) && (earthGoal > 0) && (airGoal > 0));
		}

		void print()
		{
			LOG("%s\n", "Potion:");
			LOG("\tCURRENT \tAir=%i \tEarth=%i \tFire=%i \tWater=%i\n", curAir, curEarth, curFire, curWater);
			LOG("\tGOAL\t \tAir=%i \tEarth=%i \tFire=%i \tWater=%i\n", airGoal, earthGoal, fireGoal, waterGoal);
		}
};

Potion::Potion(int water, int earth, int fire, int air)
{
	waterGoal = water;
	earthGoal = earth;
	fireGoal = fire;
	airGoal = air;
	curWater = 0; 
	curEarth = 0; 
	curFire = 0;
	curAir = 0;
}

Potion::Potion()
{
	waterGoal = 0;
	earthGoal = 0;
	fireGoal = 0;
	airGoal = 0;
	curWater = 0; 
	curEarth = 0; 
	curFire = 0;
	curAir = 0;
}