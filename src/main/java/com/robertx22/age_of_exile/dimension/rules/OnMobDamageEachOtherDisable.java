package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class OnMobDamageEachOtherDisable {

    public static void onHurtEvent(AttackInformation event) {

        if (event.getAttackerEntity() instanceof PlayerEntity == false || event.getAttackerEntity() == null) {
            if (event.getTargetEntity() instanceof PlayerEntity == false) {
                if (WorldUtils.isMapWorldClass(event.getTargetEntity().world)) {
                    event.setAmount(0);

                    if (event.getAttackerEntity() instanceof MobEntity) {
                        MobEntity mob = (MobEntity) event.getAttackerEntity();
                        if (mob.getTarget() instanceof PlayerEntity == false) {
                            mob.setTarget(null);
                        }
                    }

                }
            }
        }
    }
}