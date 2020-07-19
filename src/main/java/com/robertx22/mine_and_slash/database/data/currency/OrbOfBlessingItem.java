package com.robertx22.mine_and_slash.database.data.currency;

import com.robertx22.mine_and_slash.database.data.currency.base.CurrencyItem;
import com.robertx22.mine_and_slash.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.GearEnumLocReq;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.interfaces.IRenamed;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfBlessingItem extends CurrencyItem implements ICurrencyItemEffect, IRenamed, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/number_reroll";
    }

    @Override
    public int getWeight() {
        return 500;
    }

    private static final String name = Ref.MODID + ":currency/number_reroll";

    @Override
    public List<String> oldNames() {
        return Arrays.asList(Ref.MODID + ":number_reroll");
    }

    public OrbOfBlessingItem() {

        super(name);

    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        for (IRerollable rel : gear.GetAllRerollable()) {
            rel.RerollNumbers(gear);
        }
        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, GearEnumLocReq.REROLL_NUMBERS, SimpleGearLocReq.IS_NOT_UNIQUE);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Rare;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb Of Blessing";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls all numbers on a gear";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.CURRENCIES.ORB_OF_BLESSING)
            .input('#', ModRegistry.MISC_ITEMS.CRYSTALLIZED_ESSENCE)
            .input('t', ModRegistry.CURRENCIES.ORB_OF_TURBULENCE)
            .input('v', Items.COAL)
            .input('o', ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE)
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ovo")
            .criterion("player_level", trigger());
    }

}