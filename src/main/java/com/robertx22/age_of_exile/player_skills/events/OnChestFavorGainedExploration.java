package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class OnChestFavorGainedExploration {

    public static void run(LootInfo info, PlayerEntity player, int favor) {

        PlayerSkill exploration = ExileDB.PlayerSkills()
            .get(PlayerSkillEnum.EXPLORATION.id);

        RPGPlayerData skills = Load.playerRPGData(player);

        skills.professions.addExp(player, PlayerSkillEnum.EXPLORATION, favor * 2);
        List<ItemStack> list = exploration.getExtraDropsFor(player, favor, LevelUtils.levelToSkillTier(info.level));

        SkillDropEvent effect = new SkillDropEvent(player, PlayerSkillEnum.EXPLORATION, list);
        effect.Activate();

        effect.extraDrops.forEach(x -> PlayerUtils.giveItem(x, player));

    }
}
