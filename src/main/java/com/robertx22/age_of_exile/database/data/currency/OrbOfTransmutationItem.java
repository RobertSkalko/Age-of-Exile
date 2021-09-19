package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfTransmutationItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/orb_of_transmutation";
    }

    public static final String ID = Ref.MODID + ":currency/orb_of_transmutation";

    @Override
    public int getWeight() {
        return 3000;
    }

    public OrbOfTransmutationItem() {

        super(ID);
    }

    @Override
    public int getTier() {
        return 1;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        GearBlueprint gearPrint = new GearBlueprint(stack.getItem(), gear.lvl);
        gearPrint.gearItemSlot.set(gear.gear_type);
        gearPrint.rarity.set(gear.getRarity()
            .getHigherRarity());
        gearPrint.level.set(gear.lvl);

        GearItemData newgear = gearPrint.createData();
        gear.WriteOverDataThatShouldStay(newgear);

        ItemStack result = ItemStack.EMPTY;

        result = stack;
        Gear.Save(result, newgear);

        return result;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_HIGHER_RARITY, SimpleGearLocReq.IS_COMMON);
    }

    @Override
    public String getRarityRank() {
        return IRarity.COMMON_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb Of Transmutation";
    }

    @Override
    public String locDescForLangFile() {
        return "Transform common item into higher rarity.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .define('t', Items.REDSTONE)
            .define('v', Items.COAL)
            .define('o', ModRegistry.MISC_ITEMS.T0_DUST())
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .unlockedBy("player_level", trigger());
    }

}