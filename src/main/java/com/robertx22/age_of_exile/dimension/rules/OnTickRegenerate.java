package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.RegenEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class OnTickRegenerate {

    public static void regen(int ticks, LivingEntity en) {

        if (en.age % 50 != 0) {
            return;
        }
        if (en instanceof PlayerEntity) {
            return;
        }

        if (en.getMaxHealth() == en.getHealth()) {
            return;
        }
        if (WorldUtils.isDungeonWorld(en.world) == false) {
            return;
        }

        EntityCap.UnitData unitdata = Load.Unit(en);

        unitdata.getResources()
            .shields.onTicksPassed(50);

        unitdata.tryRecalculateStats();

        RegenEvent event = new RegenEvent(en, en, ResourceType.HEALTH);
        event.Activate();
    }

}
