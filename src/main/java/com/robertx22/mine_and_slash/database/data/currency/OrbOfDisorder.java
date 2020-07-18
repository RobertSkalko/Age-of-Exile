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
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfDisorder extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/randomize_affixes";
    }

    private static final String name = Ref.MODID + ":currency/randomize_affixes";

    public OrbOfDisorder() {

        super(name);

    }

    @Override
    public int getWeight() {
        return 250;
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.affixes.randomize(gear);
        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_NOT_UNIQUE, GearEnumLocReq.AFFIXES, SimpleGearLocReq.HAS_SUFFIX);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Common;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb Of Disorder";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls All affixes";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.MISC_ITEMS.ORB_OF_DISORDER)
            .input('#', SimpleMatItem.INFUSED_IRON)
            .input('t', ModRegistry.MISC_ITEMS.ORB_OF_TRANSMUTATION)
            .input('v', Items.GOLD_NUGGET)
            .input('o', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
            .pattern("o#o")
            .pattern("oto")
            .pattern("vvv")
            .criterion("player_level", trigger());
    }

}
