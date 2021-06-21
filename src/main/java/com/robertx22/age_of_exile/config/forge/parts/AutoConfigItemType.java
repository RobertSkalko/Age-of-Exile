package com.robertx22.age_of_exile.config.forge.parts;

import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AutoConfigItemType {

    public int MIN_LEVEL;
    public int MAX_LEVEL;

    public double POWER_REQ_MIN;
    public double POWER_REQ_MAX;

    public boolean CAN_BE_SALVAGED = true;

    public AutoConfigItemType(float reqmin, float req, int minlvl, int maxlvl) {
        MAX_LEVEL = maxlvl;
        POWER_REQ_MIN = reqmin;
        POWER_REQ_MAX = req;
        this.MIN_LEVEL = minlvl;
    }

    public AutoConfigItemType noSalvage() {
        this.CAN_BE_SALVAGED = false;
        return this;
    }

    public boolean isInRange(float power) {
        return power >= POWER_REQ_MIN && POWER_REQ_MAX >= power;
    }

    public List<CompatibleItem> getAutoCompatibleItems(Item item, GearSlot slot) {

        List<CompatibleItem> list = new ArrayList<>();

        try {

            ExileDB.GearTypes()
                .getFilterWrapped(x -> {

                    if (x.getGearSlot()
                        .GUID()
                        .equals(slot
                            .GUID())) {

                        if (!x.getLevelRange()
                            .isLevelInRange(MIN_LEVEL) && !x.getLevelRange()
                            .isLevelInRange(MAX_LEVEL)) {
                            return false;
                        }

                        CompatibleItem comp = CompatibleItem.getDefaultAuto(item, x);
                        comp.rarities = GearRarityGroups.NON_UNIQUE_ID;
                        comp.can_be_salvaged = CAN_BE_SALVAGED;
                        comp.item_type = x.GUID();

                        list.add(comp);

                        return true;

                    }

                    return false;

                });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }
}
