package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.player_skills.ingredient.data.IngredientData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class IngredientItem extends AutoItem {

    public String id;
    public String locname;

    public IngredientItem(String id, String locname) {
        super(new Properties().tab(CreativeTabs.Materials));
        this.id = id;
        this.locname = locname;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {

        if (this.allowdedIn(group)) {

            if (this != IngredientItems.BLACKPEARL.get()) {
                return; // fill tab is called for EVERY item, so we will only allow 1 item to fill it.
            }

            for (SlashIngredient ing : ExileDB.Ingredients()
                .getList()) {

                for (int tier = 1; tier < LevelUtils.getMaxTier(); tier++) {

                    IngredientData data = new IngredientData();
                    data.tier = tier;
                    data.id = ing.GUID();

                    ItemStack stack = new ItemStack(data.getIngredient()
                        .getItem());
                    StackSaving.INGREDIENTS.saveTo(stack, data);
                    stacks.add(stack);

                }
            }

        }
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return id;
    }
}
