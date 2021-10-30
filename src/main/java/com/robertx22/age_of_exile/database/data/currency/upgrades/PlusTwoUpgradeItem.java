package com.robertx22.age_of_exile.database.data.currency.upgrades;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.UpgradeData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlusTwoUpgradeItem extends CurrencyItem implements ICurrencyItemEffect, IShapelessRecipe {

    @Override
    public String GUID() {
        return "currency/plus_two_upgrade";
    }

    public static final String ID = SlashRef.MODID + ":currency/plus_two_upgrade";

    @Override
    public int getWeight() {
        return 100;
    }

    public PlusTwoUpgradeItem() {
        super(ID);
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        if (gear.up.isNextSlotGold() || RandomUtils.roll(75)) {
            gear.onUpgrade(UpgradeData.SlotType.N2);
        } else {
            gear.onUpgrade(UpgradeData.SlotType.M1);
        }
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_UPGRADE_SLOTS);
    }

    @Override
    public String getRarityRank() {
        return IRarity.RARE_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Extraordinary Upgrade Stone";
    }

    @Override
    public String locDescForLangFile() {
        return "75% chance of +2, else -1";
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(CurrencyItems.PLUS_ONE_UPGRADE.get(), 9);
        return fac.unlockedBy("player_level", trigger());
    }
}