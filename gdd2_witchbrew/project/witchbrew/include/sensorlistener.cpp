#pragma once
#include <sifteo.h>
#include "assets.gen.h"
#include "witchbrew.cpp"
using namespace Sifteo;

static TiltShakeRecognizer motion[CUBE_ALLOCATION];

class SensorListener {

	WitchBrew controller;

public:
	SensorListener(WitchBrew wb);

    struct Counter {
        unsigned touch;
        unsigned neighborAdd;
        unsigned neighborRemove;
    } counters[CUBE_ALLOCATION];

    void install()
    {
        Events::cubeAccelChange.set(&SensorListener::onAccelChange, this);
		Events::neighborAdd.set(&SensorListener::onNeighborAdd, this);

        // Handle already-connected cubes
        for (CubeID cube : CubeSet::connected())
            onConnect(cube);
    }

private:
	void onConnect(unsigned id)
    {
        CubeID cube(id);
        uint64_t hwid = cube.hwID();

        bzero(counters[id]);
        LOG("Cube %d connected\n", id);

        motion[id].attach(id);

        // Draw initial state for all sensors
        onAccelChange(cube);
    }

	void onAccelChange(unsigned id)
    {
        CubeID cube(id);
        auto accel = cube.accel();

        unsigned changeFlags = motion[id].update();
        if (changeFlags) {
            // Tilt/shake changed

			auto tilt = motion[id].tilt;
			if (tilt.x > 0){
				//LOG("ACCEL: TILT RIGHT %i\n", tilt.x);
				controller.cycleIngredient(id, tilt.x);
			}else if(tilt.x < 0){
				//LOG("ACCEL: TILT LEFT %i\n", tilt.x);
				controller.cycleIngredient(id, tilt.x);
			}

        }

    }

    void onNeighborAdd(unsigned firstID, unsigned firstSide, unsigned secondID, unsigned secondSide)
    {
        //LOG("Neighbor Add: %02x:%d - %02x:%d\n", firstID, firstSide, secondID, secondSide);
		controller.addIngredientToPotion(firstID, secondID);
  
    }

};

SensorListener::SensorListener (WitchBrew wb)
{
	controller = wb;
}