package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
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

public class OrbOfCorruption extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/orb_of_corruption";
    }

    private static final String name = SlashRef.MODID + ":currency/orb_of_corruption";

    public OrbOfCorruption() {
        super(name);
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        gear.affixes.addCorruptAffix(gear);

        gear.c = true;

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public float getBreakChance() {
        return 50;
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_NOT_CORRUPTED);
    }

    @Override
    public float getInstability() {
        return 0;
    }

    @Override
    public String getRarityRank() {
        return IRarity.RARE_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Corruption";
    }

    @Override
    public String locDescForLangFile() {
        return "Corrupts item, changing it unpredictably.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(CurrencyItems.ORB_OF_CORRUPTION.get())
            .define('#', SlashItems.MYTHIC_ESSENCE.get())
            .define('t', CurrencyItems.ORB_OF_TRANSMUTATION.get())
            .define('v', Items.EMERALD)
            .define('o', SlashItems.GOLDEN_ORB.get())
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ooo")
            .unlockedBy("player_level", trigger());
    }

}