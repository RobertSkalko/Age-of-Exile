package com.robertx22.age_of_exile.database.data.salvage_recipes;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemRequirement {

    public static String LEVEL = "lvl";
    public static String RARITY = "rarity";

    public String req_type = "";
    public String req_str = "";
    public Integer req_num = 0;

    public static ItemRequirement rarity(String rar) {
        ItemRequirement r = new ItemRequirement();
        r.req_type = RARITY;
        r.req_str = rar;
        return r;
    }

    public boolean matches(ItemStack stack) {

        if (req_type.equals("item")) {
            return stack.getItem() == Registry.ITEM.get(new ResourceLocation(req_str));
        }
        if (req_type.equals("tag")) {
            return stack.getItem()
                .is(ItemTags.getAllTags()
                    .getTag(new ResourceLocation(req_str)));
        }
        GearItemData gear = Gear.Load(stack);

        if (gear != null) {
            if (req_type.equals(RARITY)) {
                return gear.rarity.equals(req_str);
            }
            if (req_type.equals(LEVEL)) {
                return gear.lvl >= req_num;
            }
        }

        return false;
    }
}
