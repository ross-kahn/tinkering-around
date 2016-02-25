#pragma once
#include <sifteo.h>
#include <sifteo/menu.h>
#include "assets.gen.h"
#include "include/constants.h"

static const unsigned cauldronID = 1;
static const unsigned numCubes = 3;				// Min and Max number of cubes
static const CubeSet allCubes(0, numCubes);		// Set of all cubes
static VideoBuffer vid[numCubes];				// Array of videobuffers, size equal to number of cubes


class WitchBrew{
	CubeID cauldron;
	int curIngredientOf[numCubes];
	int numIngredients;
	Ingredient allIngredients[14];
	Potion allPotions[1];
	Potion currentPotion;

public:
	void init(){

		attachImages();
		makeIngredients();
		makePotions();
		currentPotion = allPotions[0];
	}
	
	void attachImages()
	{
		int i=0;
		for(CubeID cube: allCubes){
			if(i==cauldronID){
				cauldron = cube;
				vid[cauldronID].initMode(BG0_BG1);
				vid[cauldronID].attach(cauldron);

				vid[cauldronID].bg0.image(vec(0,0), BaseCauldron);
				//vid[cauldron].bg1.image(vec(0,0), LevelGoal1);
				//vid[cauldronID].bg1.fillMask( vec(5, 10), vec(1, 7) );
				
			}else{
				vid[i].initMode(BG0);
				vid[i].attach(cube);

				vid[i].bg0.image(vec(0,0), Ingredients[0]);
				curIngredientOf[i] = 0;
			}
			i++;
		}
		int ing =0;
		for(AssetImage ai: Ingredients){
			ing++;
		}
		numIngredients = ing;
	}
	
	void drawPotionProgress()
	{

		int airY = 10;
		int earth1Y = 11;
		int earth2Y = 12;
		int fireY = 13;
		int water1Y = 14;
		int water2Y = 15;

		vid[cauldron].bg1.eraseMask();

		//vid[cauldronID].bg1.fillMask( vec(5, 10), vec(1, 7) );
		vid[cauldronID].bg1.fillMask( vec(1, 10), vec(currentPotion.air(), 1) );
		vid[cauldronID].bg1.fillMask( vec(1, 11), vec(currentPotion.earth(), 2) );
		vid[cauldronID].bg1.fillMask( vec(1, 13), vec(currentPotion.fire(), 1) );
		vid[cauldronID].bg1.fillMask( vec(1, 14), vec(currentPotion.water(), 2) );

		//vid[cauldron].bg1.eraseMask();

		// Setting up mask to make bar usable
		int ind = 1;
		while(ind <= currentPotion.fire())
		{
			if(ind > 14){break;}
			vid[cauldron].bg1.image(vec(ind,fireY), FireTile);
			ind++;
		}

		ind=1;
		while (ind <= currentPotion.water())
		{
			if(ind > 14){break;}
			vid[cauldron].bg1.image(vec(ind,water1Y), Water1Tile);
			vid[cauldron].bg1.image(vec(ind,water2Y), Water2Tile);
			ind++;
		}

		ind=1;
		while (ind <= currentPotion.air())
		{
			if(ind > 14){break;}
			vid[cauldron].bg1.image(vec(ind,airY), AirTile);
			ind++;
		}

		ind=1;
		while (ind <= currentPotion.earth())
		{
			if(ind > 14){break;}
			vid[cauldron].bg1.image(vec(ind,earth1Y), Earth1Tile);
			vid[cauldron].bg1.image(vec(ind,earth2Y), Earth2Tile);
			ind++;
		}
	}

	void makeIngredients()
	{	
		int index = 0;
		for(const int *ingArray : ingredient_data)
		{
			Ingredient newIngredient (ingArray[WATER_INDEX], 
									ingArray[EARTH_INDEX], 
									ingArray[FIRE_INDEX], 
									ingArray[AIR_INDEX]	);
			allIngredients[index] = newIngredient;
			index++;
		}
	}
	
	void makePotions()
	{
		int index =0;
		for(const int *potArray : potion_data)
		{
			Potion newPotion (potArray[WATER_INDEX], 
									potArray[EARTH_INDEX], 
									potArray[FIRE_INDEX], 
									potArray[AIR_INDEX]	);
			allPotions[index] = newPotion;
			index++;
		}
	}
	
	void addIngredientToPotion(int cube1Num, int cube2Num)
	{


		if (cube1Num != cauldronID && cube2Num != cauldronID)
		{
			return;
		}

		int ingredientCube;
		if(cube1Num == cauldronID)
		{
			ingredientCube = cube2Num;
		}
		else
		{
			ingredientCube = cube1Num;
		}
		LOG("ADDING INGREDIENT FROM %i: ", ingredientCube);
		Ingredient ingredient = allIngredients[curIngredientOf[ingredientCube]];
		ingredient.print();
		currentPotion.add(ingredient);
		drawPotionProgress();

//##############################################################################################################
		if(currentPotion.won())
		{
			vid[cauldronID].bg1.eraseMask();
			vid[cauldronID].bg0.image( vec(0,0), WinScreen);
		}
		else if(currentPotion.over())
		{
			init();
		}
	}

	void cycleIngredient(int cubeNum, int cycle){
		if(cubeNum == cauldronID){		// We don't want the cauldron to change image
			return;
		}
	
		int curIndex = curIngredientOf[cubeNum];
		int ingIndex = curIndex + cycle;
		
		if( ingIndex >= (numIngredients - 1) ){	// Upper bound wrap
			ingIndex = 0;
		
		}else if( ingIndex <= 0){		// Lower bound wrap
			ingIndex = numIngredients - 1;
		}

		vid[cubeNum].bg0.image(vec(0,0), Ingredients[ingIndex]);
		curIngredientOf[cubeNum] = ingIndex;
		
		LOG("---CURRENT INGREDIENT INDEX: %i---\n", ingIndex);
	}

};