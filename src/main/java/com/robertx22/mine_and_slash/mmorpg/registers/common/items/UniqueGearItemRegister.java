package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UniqueGearItemRegister {

    public static void registerAll() {
        SlashRegistry.UniqueGears()
            .getSerializable()
            .forEach(x -> of(x));
    }

    static Item of(IUnique uniq) {
        Item c = IUnique.getBaseItemForRegistration(uniq);
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
