package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemSword;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<LevelRange, Item> SWORDS = new HashMap<>();
    public HashMap<LevelRange, Item> AXES = new HashMap<>();

    private Identifier id(String id) {
        return new Identifier(Ref.MODID, id);
    }

    public BaseGearTypeItemRegister() {

        LevelRanges.allNormal()
            .forEach(x -> {

                SWORDS.put(x, register(ITEM, id("weapon/sword/sword" + x.id_suffix), new ItemSword("Sword")));
                AXES.put(x, register(ITEM, id("weapon/axe/axe" + x.id_suffix), new ItemSword("Sword")));

            });

    }

}
