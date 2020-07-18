package com.robertx22.mine_and_slash.database.data.currency;

import com.robertx22.mine_and_slash.database.data.currency.base.CurrencyItem;
import com.robertx22.mine_and_slash.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.GearEnumLocReq;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
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
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_NOT_UNIQUE, GearEnumLocReq.AFFIXES);
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
        return shaped(ModItems.ORB_OF_TURBULENCE.get())
            .input('#', SimpleMatItem.CRYSTALLIZED_ESSENCE)
            .input('t', ModItems.ORB_OF_TRANSMUTATION.get())
            .input('v', Items.GLISTERING_MELON_SLICE)
            .input('o', ModItems.RARE_MAGIC_ESSENCE.get())
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ooo")
            .criterion("player_level", trigger());
    }

}