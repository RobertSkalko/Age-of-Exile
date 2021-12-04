package com.robertx22.addon.divine_missions.types;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.divine_missions.database.RewardTypeIds;
import com.robertx22.divine_missions.database.db_types.RewardType;
import com.robertx22.divine_missions.main.DivineMissions;
import com.robertx22.divine_missions.saving.RewardData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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
    public IFormattableTextComponent getTranslatable(RewardData rewardData) {
        return new StringTextComponent(rewardData.count + " ").append(DivineMissions.ofTranslation("favor"))
            .withStyle(TextFormatting.LIGHT_PURPLE);
    }
}
