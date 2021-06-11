package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CrystalOfPurificationItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/crystal_of_purification";
    }

    public static final String ID = Ref.MODID + ":currency/crystal_of_purification";

    @Override
    public int getWeight() {
        return 0;
    }

    public CrystalOfPurificationItem() {
        super(ID);
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.s = false;
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_SEALED);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public float getInstability() {
        return 0;
    }

    @Override
    public String getRarityRank() {
        return IRarity.MAGICAL_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Crystal of Unsealing";
    }

    @Override
    public String locDescForLangFile() {
        return "Unseals the item.";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('t', ModRegistry.CURRENCIES.CRYSTAL_OF_TRUTH)
            .input('v', ModRegistry.GEAR_MATERIALS.ARCANA)
            .input('o', ModRegistry.MISC_ITEMS.T3_DUST())
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .criterion("player_level", trigger());
    }

}