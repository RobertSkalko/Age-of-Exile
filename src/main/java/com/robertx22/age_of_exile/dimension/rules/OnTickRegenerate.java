package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class OnTickRegenerate {

    public static void regen(int ticks, LivingEntity en) {

        if (en.tickCount % 50 != 0) {
            return;
        }
        if (en instanceof PlayerEntity) {
            return;
        }

        if (en.getMaxHealth() == en.getHealth()) {
            return;
        }
        if (WorldUtils.isMapWorldClass(en.level) == false) {
            return;
        }

        EntityData unitdata = Load.Unit(en);

        unitdata.tryRecalculateStats();

        RestoreResourceEvent restore = EventBuilder.ofRestore(en, en, ResourceType.health, RestoreType.regen, 0)
            .build();
        restore.Activate();
    }

}
