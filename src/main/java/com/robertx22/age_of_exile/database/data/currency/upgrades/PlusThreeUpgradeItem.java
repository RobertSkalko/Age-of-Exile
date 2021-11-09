package com.robertx22.age_of_exile.database.data.currency.upgrades;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
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

public class PlusThreeUpgradeItem extends CurrencyItem implements ICurrencyItemEffect, IShapelessRecipe {

    @Override
    public String GUID() {
        return "currency/plus_three_upgrade";
    }

    public static final String ID = SlashRef.MODID + ":currency/plus_three_upgrade";

    @Override
    public int getWeight() {
        return 25;
    }

    public PlusThreeUpgradeItem() {
        super(ID);
    }

    @Override
    public int getTier() {
        return 5;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        if (gear.up.isNextSlotGold() || RandomUtils.roll(70)) {
            gear.onUpgrade(ctx.player, UpgradeData.SlotType.N3);
        } else {
            gear.onUpgrade(ctx.player, UpgradeData.SlotType.M2);
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
        return nameColor + "Supreme Upgrade Stone";
    }

    @Override
    public String locDescForLangFile() {
        return "70% chance of +3, else -2";
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(CurrencyItems.PLUS_TWO_UPGRADE.get(), 9);
        return fac.unlockedBy("player_level", trigger());
    }
}
