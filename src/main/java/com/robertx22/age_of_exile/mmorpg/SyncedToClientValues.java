package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.dimension.teleporter.MapsData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class SyncedToClientValues {

    public static MapsData mapsData = new MapsData();

    public static int areaLevel = 1;

    public static PlayerSkillEnum skillJustLeveled = null;

    public static int ticksToShowSkillLvled = 0;
}
