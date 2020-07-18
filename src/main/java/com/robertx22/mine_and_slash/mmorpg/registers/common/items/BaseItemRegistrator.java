package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public abstract class BaseItemRegistrator {

    <T extends Item & IGUID> T item(T c) {
        Registry.register(Registry.ITEM, new Identifier(Ref.MODID, c.GUID()), c);
        return c;

    }

    <T extends Item> T item(T c, String id) {
        Registry.register(Registry.ITEM, new Identifier(Ref.MODID, id), c);
        return c;
    }

    Item of(Item c, BaseGearType slot) {

        Registry.register(Registry.ITEM, new Identifier(Ref.MODID, slot.family()
            .name()
            .toLowerCase(Locale.ROOT) + "/" + slot.GUID()), c);

        return c;

    }

}
