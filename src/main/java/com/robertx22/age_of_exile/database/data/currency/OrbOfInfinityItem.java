package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfInfinityItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/orb_of_infinity";
    }

    private static final String name = SlashRef.MODID + ":currency/orb_of_infinity";

    public OrbOfInfinityItem() {
        super(name);
    }

    @Override
    public int getTier() {
        return 5;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);
        gear.up.regenerate(gear.getRarity());
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE);
    }

    @Override
    public float getInstability() {
        return 100;
    }

    @Override
    public String getRarityRank() {
        return IRarity.RARE_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Infinity";
    }

    @Override
    public String locDescForLangFile() {
        return "Resets the gear's upgrade level and rerolls slots.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(CurrencyItems.ORB_OF_INFINITY.get())
            .define('#', SlashItems.MYTHIC_ESSENCE.get())
            .define('t', CurrencyItems.ORB_OF_DISORDER.get())
            .define('v', Items.DIAMOND)
            .define('o', SlashItems.GOLDEN_ORB.get())
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ooo")
            .unlockedBy("player_level", trigger());
    }

}