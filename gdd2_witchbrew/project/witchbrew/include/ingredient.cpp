//Put important things here
#pragma once
#include <sifteo.h>
#include <sifteo/menu.h>
#include "assets.gen.h"
using namespace Sifteo;

//CLASS INGREDIENT
class Ingredient 
{
		int fireValue, waterValue, airValue, earthValue;
	public:
		Ingredient(int,int,int,int);
		Ingredient();
		//RETURNS so potion class can read the values and adjust
		int getFire(){return fireValue;}
		int getWater(){return waterValue;}
		int getAir(){return airValue;}
		int getEarth(){return earthValue;}

		void print()
		{
			LOG("INGREDIENT\n");
			LOG("\tAIR=%i \tEARTH=%i \tFIRE=%i \tWATER=%i\n", airValue, earthValue, fireValue, waterValue);
		}
};

Ingredient::Ingredient (int water, int earth, int fire, int air)
{
	fireValue = fire;
	waterValue = water;
	airValue = air;
	earthValue = earth;
}

Ingredient::Ingredient()
{
	fireValue = 0;
	waterValue = 0;
	airValue = 0;
	earthValue = 0;
}