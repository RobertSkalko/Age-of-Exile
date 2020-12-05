package com.robertx22.age_of_exile.player_skills;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public interface IReqSkillLevel {

    public default boolean canPlayerCraft(PlayerEntity player) {

        if (player == null) {
            return false;
        }

        if (getSkillLevelMultiNeeded() > Load.Unit(player)
            .getLevel()) {
            return false;
        }
        return true;

    }

    public PlayerSkillEnum getItemSkillType();

    float getSkillLevelMultiNeeded();

    public default int getSkillLevelRequired() {
        return (int) (GameBalanceConfig.get().MAX_LEVEL * getSkillLevelMultiNeeded());
    }
}
