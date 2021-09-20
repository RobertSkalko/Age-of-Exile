package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ClearInstabilityItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/clear_instability";
    }

    @Override
    public int getTier() {
        return 1;
    }

    public static final String ID = SlashRef.MODID + ":currency/clear_instability";

    @Override
    public int getWeight() {
        return 0;
    }

    public ClearInstabilityItem() {
        super(ID);
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.setInstability(gear.getInstability() - 500);
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE);
    }

    @Override
    public float getInstability() {
        return 0;
    }

    @Override
    public float getBreakChance() {
        return 25;
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Cooling";
    }

    @Override
    public String locDescForLangFile() {
        return "Clears 500 instability from an item.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
            .define('t', CurrencyItems.CLEAR_RUNES.get())
            .define('v', SlashItems.T4_DUST())
            .define('o', ProfessionItems.CONDENSED_ESSENCE_MAP.get(SkillItemTier.TIER4)
                .get())
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .unlockedBy("player_level", trigger());
    }

}