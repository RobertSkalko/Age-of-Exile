package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsaw;
import com.robertx22.world_of_exile.main.CommonInit;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class PreInit implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        CommonInit.registerStructure(new DungeonDimensionJigsaw());
    }
}
