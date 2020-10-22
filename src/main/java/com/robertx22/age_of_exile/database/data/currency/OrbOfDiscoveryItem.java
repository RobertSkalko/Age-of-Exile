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
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfDiscoveryItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/orb_of_discovery";
    }

    public static final String ID = Ref.MODID + ":currency/orb_of_discovery";

    @Override
    public int getWeight() {
        return 400;
    }

    public OrbOfDiscoveryItem() {

        super(ID);

    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.sockets.max_sockets++;
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.CAN_GET_MORE_SOCKETS);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public float getInstability() {
        return 10;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Crown of Discovery";
    }

    @Override
    public String locDescForLangFile() {
        return "Adds additional socket.";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.CURRENCIES.CROWN_OF_DISCOVERY)
            .input('t', ModRegistry.MISC_ITEMS.T1_DUST)
            .input('v', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .input('o', Items.DIAMOND)
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .criterion("player_level", trigger());
    }

}