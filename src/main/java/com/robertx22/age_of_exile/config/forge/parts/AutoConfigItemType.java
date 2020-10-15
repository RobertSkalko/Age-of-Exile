package com.robertx22.age_of_exile.config.forge.parts;

import com.robertx22.age_of_exile.aoe_data.database.groups.GearRarityGroupAdder;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AutoConfigItemType {

    public int MAX_LEVEL;

    public double POWER_REQ_MIN;
    public double POWER_REQ_MAX;

    public int MIN_RARITY = 0; // TODO REWORK THIS
    public int MAX_RARITY = 100;

    public boolean CAN_BE_SALVAGED = true;

    public AutoConfigItemType(float reqmin, float req, int maxlvl) {
        MAX_LEVEL = maxlvl;
        POWER_REQ_MIN = reqmin;
        POWER_REQ_MAX = req;
    }

    public AutoConfigItemType noSalvage() {
        this.CAN_BE_SALVAGED = false;
        return this;
    }

    public AutoConfigItemType maxRarity(int rar) {
        this.MAX_RARITY = rar;
        return this;
    }

    public boolean isInRange(float power) {
        return power >= POWER_REQ_MIN && POWER_REQ_MAX >= power;
    }

    public List<CompatibleItem> getAutoCompatibleItems(float power, Item item, GearSlot slot) {

        List<CompatibleItem> list = new ArrayList<>();

        try {
            SlashRegistry.GearTypes()
                .getFilterWrapped(x -> {

                    if (x.getGearSlot()
                        .GUID()
                        .equals(slot
                            .GUID())) {

                        if (power < x.getLevelRange()
                            .getStartPercent()) {
                            return false;
                        }
                        if (power > x.getLevelRange()
                            .getEndPercent()) {
                            return false;
                        }

                        CompatibleItem comp = CompatibleItem.getDefaultAuto(item, x);
                        comp.rarities = GearRarityGroupAdder.NORMAL_KEY.GUID();
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
