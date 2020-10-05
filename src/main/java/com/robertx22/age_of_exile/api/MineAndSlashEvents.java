package com.robertx22.age_of_exile.api;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.event_hooks.entity.damage.DamageEventData;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.saveclasses.unit.GearData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class MineAndSlashEvents {

    // called before stat calculation to gather all the gears. Add it here if you say use a different jewerly mod
    // that isn't compatible with one i use
    public static class CollectGearStacksEvent {

        public List<GearData> gears;

        public LivingEntity entity;
        public DamageEventData data;

        public CollectGearStacksEvent(LivingEntity entity, List<GearData> gears, DamageEventData data) {
            this.entity = entity;
            this.gears = gears;
            this.data = data;

            EntityCap.UnitData unitdata = Load.Unit(entity);

            gears.addAll(CollectGearEvent.getAllGear(data, entity, unitdata));

            gears.removeIf(x -> !x.isUsableBy(unitdata));

        }

    }

}