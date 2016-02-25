#pragma once

#include <sifteo/asset/image.h>


///
//Helper function to get the image for a beast from its type (BeastID).
//Currently requires hardcoding.
//Note this is returning Sifteo AssetImages defined in assets.lua.
static Sifteo::AssetImage getBeastImage(int beastType)
{
	//must hard code conversion between number and name
    switch(beastType)
    {
        case 0:
            return PureEnergyBeing;
            break;
        case 1:
            return PlasmaDemon;
            break;
        case 2:
            return Crakehound;
            break;
		case 3:
            return Slagwurm;
            break;
		case 4:
            return Werewolf;
            break;
		case 5:
            return MagmaBlob;
            break;
		case 6:
            return FireDrake;
            break;
		case 7:
            return Phoenix;
            break;
		case 8:
            return Fireflies;
            break;
		case 9:
            return SludgeBehemoth;
            break;
        case 10:
            return AssassinSpider;
            break;
		case 11:
            return MountainWolf;
            break;
		case 12:
            return DiamondChrysalid;
            break;
		case 13:
            return ManEatingPlant;
            break;
        case 14:
            return WirrakirAxeman;
            break;
		case 15:
            return Tengu;
            break;
		case 16:
            return SeaUrchin;
            break;
		case 17:
            return FlyingSquirrel;
            break;
		case 18:
            return PlanesWalker;
            break;
		case 19:
            return Vampire;
            break;
		case 20:
            return EmperorWasp;
            break;
		case 21:
            return FacelessOne;
            break;
		case 22:
            return Doppleganger;
            break;
		case 23:
            return WillOTheWisp;
            break;
		case 24:
            return ChaosWanderer;
            break;
        case 25:
            return Banshee;
            break;
        case 26:
            return FairyArcher;
            break;        
        
        default:
            return Icon; 
            break;
    }
} 
