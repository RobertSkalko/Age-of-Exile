package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.repair_kits.RepairKitItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class RepairKitsRegister extends BaseItemRegistrator {

    public HashMap<Integer, RepairKitItem> KIT_MAP = new HashMap<>();

    public RepairKitItem TIER_0 = of(new RepairKitItem(0, "Shabby Repair Kit", 500, false, Items.IRON_INGOT));
    public RepairKitItem TIER_1 = of(new RepairKitItem(1, "Iron Repair Kit", 1000, false, Items.IRON_BLOCK));
    public RepairKitItem TIER_2 = of(new RepairKitItem(2, "Gold Repair Kit", 2000, false, Items.GOLD_BLOCK));
    public RepairKitItem TIER_3 = of(new RepairKitItem(3, "Diamond Repair Kit", 5000, false, Items.DIAMOND_BLOCK));

    RepairKitItem of(RepairKitItem c) {
        Registry.register(Registry.ITEM, new Identifier(Ref.MODID, c.GUID()), c);
        KIT_MAP.put(c.tier, c);
        return c;

    }
}
