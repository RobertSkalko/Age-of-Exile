package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfUniqueBlessingItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/reroll_unique_numbers";
    }

    private static final String name = SlashRef.MODID + ":currency/reroll_unique_numbers";

    public OrbOfUniqueBlessingItem() {

        super(name);

    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public int getWeight() {
        return 100;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        gear.uniqueStats.RerollNumbers(gear);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_UNIQUE);
    }

    @Override
    public float getInstability() {
        return 50;
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb Of Unique Blessing";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls unique stat numbers.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(ModRegistry.CURRENCIES.ORB_OF_UNIQUE_BLESSING)
            .define('#', ModRegistry.MISC_ITEMS.GOLDEN_ORB)
            .define('t', ModRegistry.CURRENCIES.ORB_OF_BLESSING)
            .define('v', Items.GOLD_INGOT)
            .define('o', ModRegistry.MISC_ITEMS.T4_DUST())
            .pattern("o#o")
            .pattern("oto")
            .pattern("v#v")
            .unlockedBy("player_level", trigger());
    }

}