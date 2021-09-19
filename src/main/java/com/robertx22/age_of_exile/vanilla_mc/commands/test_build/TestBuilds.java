package com.robertx22.age_of_exile.vanilla_mc.commands.test_build;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

import java.util.HashMap;

public class TestBuilds {

    public static HashMap<EquipmentSlotType, BaseGearType> getGearsFor(BaseGearType.SlotTag tag, PlayerEntity player) {

        int lvl = Load.Unit(player)
            .getLevel();

        HashMap<EquipmentSlotType, BaseGearType> map = new HashMap<>();

        ExileDB.GearTypes()
            .getList()
            .forEach(x -> {
                if (x.getLevelRange()
                    .isLevelInRange(lvl)) {
                    if (x.getTags()
                        .contains(tag)) {
                        map.put(x.getVanillaSlotType(), x);
                    }
                }
            });

        return map;

    }

}
