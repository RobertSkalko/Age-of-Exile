package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.GearEnumLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfTurbulence extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/reroll_affix_numbers";
    }

    private static final String name = Ref.MODID + ":currency/reroll_affix_numbers";

    public OrbOfTurbulence() {

        super(name);

    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {

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
    public int getTier() {
        return 4;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Epic;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Turbulence";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls all affix numbers";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.CURRENCIES.ORB_OF_TURBULENCE)
            .input('#', ModRegistry.MISC_ITEMS.CRYSTALLIZED_ESSENCE)
            .input('t', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .input('v', Items.GLISTERING_MELON_SLICE)
            .input('o', ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE)
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ooo")
            .criterion("player_level", trigger());
    }

}