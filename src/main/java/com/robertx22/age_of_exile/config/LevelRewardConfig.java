package com.robertx22.age_of_exile.config;

import com.robertx22.age_of_exile.mmorpg.Ref;

public class LevelRewardConfig {

    public String loot_table_id = "";
    public int for_level = 0;
    public String exec_command = "";

    public LevelRewardConfig(int for_level) {
        this.loot_table_id = Ref.id("level_rewards/" + for_level)
            .toString();
        this.for_level = for_level;
    }

    public LevelRewardConfig() {
    }
}
