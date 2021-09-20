package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class LeafOfChangeItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/reroll_primary_stats_numbers";
    }

    @Override
    public int getWeight() {
        return 1000;
    }

    private static final String name = SlashRef.MODID + ":currency/reroll_primary_stats_numbers";

    public LeafOfChangeItem() {

        super(name);

    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        gear.baseStats.RerollNumbers(gear);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_PRIMARY_STATS);
    }

    @Override
    public String getRarityRank() {
        return EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Leaf of Change";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls implicit stat numbers.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(ModRegistry.CURRENCIES.LEAF_OF_CHANGE)
            .define('#', SlashItems.GOLDEN_ORB.get())
            .define('t', ModRegistry.CURRENCIES.ORB_OF_BLESSING)
            .define('v', Items.GOLD_INGOT)
            .define('o', ModRegistry.MISC_ITEMS.T3_DUST())
            .pattern("#t#")
            .pattern("tvt")
            .pattern("oto")
            .unlockedBy("player_level", trigger());
    }

}