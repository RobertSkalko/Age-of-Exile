package com.robertx22.divine_missions_addon.types;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.divine_missions.database.RewardTypeIds;
import com.robertx22.divine_missions.database.db_types.RewardType;
import com.robertx22.divine_missions.main.DivineMissions;
import com.robertx22.divine_missions.saving.RewardData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class GiveAoeExpReward extends RewardType {

    public GiveAoeExpReward() {
        super(RewardTypeIds.AOE_EXP);
    }

    @Override
    public void giveReward(PlayerEntity player, RewardData data) {

        int exp = LevelUtils.scaleExpReward(data.count, Load.Unit(player)
            .getLevel());

        Load.Unit(player)
            .GiveExp(player, exp);
    }

    @Override
    public MutableText getTranslatable(RewardData rewardData) {
        return new LiteralText(rewardData.count + " ").append(DivineMissions.ofTranslation("aoe_exp"))
            .formatted(Formatting.GREEN);
    }
}
