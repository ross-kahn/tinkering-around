// NOTE: ingredient_data elements must be in the exact same order
// 		 as the images are specified in the .lua

static const int AIR_INDEX = 0;
static const int FIRE_INDEX = 1;
static const int EARTH_INDEX =2;
static const int WATER_INDEX = 3;

static const int d_batwhiskers [4] = {3,3,0,0};
static const int d_dragonsbreath [4] = {4,2,0,0};
static const int d_gravedirt [4] = {2,0,3,0};
static const int d_gargoylesweat[4] = {2,0,0,3};
static const int d_willowwood [4] = {0,2,3,0};
static const int d_eyeofnewt [4] = {0,0,2,2};
static const int d_mermaidhair [4] = {0,0,1,4};
static const int d_vampireblood[4] = {1,0,1,4};
static const int d_blackcatfat [4] = {1,3,0,3};
static const int d_lizardscale [4] = {0,1,3,0};
static const int d_mandrakeroot [4] = {0,3,4,1};
static const int d_mummifiedtoenail [4] = {2,0,3,1};
static const int d_trolltooth [4] = {1,2,4,0};
static const int d_witchhazel [4] = {1,1,3,2};

static const int p_beginner [4] = {5,5,5,5};

static const int *ingredient_data [14] = {d_batwhiskers, d_blackcatfat, d_dragonsbreath, d_eyeofnewt, d_gargoylesweat, d_gravedirt, d_lizardscale, d_mandrakeroot, d_mermaidhair, d_mummifiedtoenail, d_trolltooth, d_vampireblood, d_willowwood, d_witchhazel};
static const int *potion_data[1] = {p_beginner};
