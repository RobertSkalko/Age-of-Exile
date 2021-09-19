package com.robertx22.divine_missions_addon.types;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.divine_missions.database.RewardTypeIds;
import com.robertx22.divine_missions.database.db_types.RewardType;
import com.robertx22.divine_missions.main.DivineMissions;
import com.robertx22.divine_missions.saving.RewardData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class GiveFavorReward extends RewardType {

    public GiveFavorReward() {
        super(RewardTypeIds.FAVOR);
    }

    @Override
    public void giveReward(PlayerEntity playerEntity, RewardData rewardData) {
        Load.playerRPGData(playerEntity).favor
            .addFavor(rewardData.count);
    }

    @Override
    public MutableText getTranslatable(RewardData rewardData) {
        return new LiteralText(rewardData.count + " ").append(DivineMissions.ofTranslation("favor"))
            .formatted(Formatting.LIGHT_PURPLE);
    }
}
