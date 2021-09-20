package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.GearEnumLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
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

public class OrbOfTurbulence extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/reroll_affix_numbers";
    }

    private static final String name = SlashRef.MODID + ":currency/reroll_affix_numbers";

    public OrbOfTurbulence() {

        super(name);

    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        gear.affixes.getAllAffixesAndSockets()
            .forEach(x -> x.RerollNumbers(gear));

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public int getWeight() {
        return 200;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_NOT_UNIQUE, GearEnumLocReq.AFFIXES);
    }

    @Override
    public float getInstability() {
        return 20;
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Turbulence";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls all affix numbers.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(CurrencyItems.ORB_OF_TURBULENCE.get())
            .define('#', SlashItems.CRYSTALLIZED_ESSENCE.get())
            .define('t', CurrencyItems.ORB_OF_TRANSMUTATION.get())
            .define('v', Items.GLISTERING_MELON_SLICE)
            .define('o', SlashItems.T4_DUST())
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ooo")
            .unlockedBy("player_level", trigger());
    }

}