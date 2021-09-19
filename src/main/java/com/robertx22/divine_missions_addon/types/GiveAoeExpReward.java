package com.robertx22.divine_missions_addon.types;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.divine_missions.database.RewardTypeIds;
import com.robertx22.divine_missions.database.db_types.RewardType;
import com.robertx22.divine_missions.main.DivineMissions;
import com.robertx22.divine_missions.saving.RewardData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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
    public IFormattableTextComponent getTranslatable(RewardData rewardData) {
        return new StringTextComponent(rewardData.count + " ").append(DivineMissions.ofTranslation("aoe_exp"))
            .withStyle(TextFormatting.GREEN);
    }
}
