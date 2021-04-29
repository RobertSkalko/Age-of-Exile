package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.player.PlayerEntity;

public class OnMobDamageEachOtherDisable {

    public static void onHurtEvent(AttackInformation event) {

        if (event.getAttackerEntity() instanceof PlayerEntity == false || event.getAttackerEntity() == null) {
            if (event.getTargetEntity() instanceof PlayerEntity == false) {
                if (WorldUtils.isDungeonWorld(event.getTargetEntity().world)) {
                    event.setAmount(0);
                }
            }
        }
    }
}