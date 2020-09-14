package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UniqueGearItemRegister {

    public static void registerAll() {
        SlashRegistry.UniqueGears()
            .getSerializable()
            .forEach(x -> of(x));
    }

    static Item of(UniqueGear uniq) {
        Item c = UniqueGear.getBaseItemForRegistration(uniq);
        if (c == null) {
            try {
                throw new Exception("Unique of type " + uniq.getBaseGearType()
                    .GUID() + " doesn't have a matching Item setup.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Registry.register(Registry.ITEM, new Identifier(Ref.MODID, uniq.getGeneratedResourceID()), c);
    }
}
