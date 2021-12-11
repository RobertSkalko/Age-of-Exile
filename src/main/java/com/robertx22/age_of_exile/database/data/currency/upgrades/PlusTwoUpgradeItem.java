package com.robertx22.age_of_exile.database.data.currency.upgrades;

import com.robertx22.age_of_exile.database.data.currency.GearBlessingType;
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
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        if (RandomUtils.roll(75)) {
            gear.onUpgrade(ctx.player, UpgradeData.SlotType.UP2);
        } else {
            if (gear.up.bless == GearBlessingType.UP_WIPE_PROTECT) {
                gear.up.bless = GearBlessingType.NONE;
                ctx.player.displayClientMessage(Words.FailedButSafe.locName()
                    .withStyle(TextFormatting.GOLD), false);
            } else {
                gear.up.regenerate(gear.getRarity());
                ctx.player.displayClientMessage(new StringTextComponent(TextFormatting.RED + "Failure, all upgrades wiped."), false);
            }
        }
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_GREEN_UPGRADE_SLOTS);
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
        return "75% chance to upgrade a green slot. 25% chance to wipe all upgrades";
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(CurrencyItems.PLUS_ONE_UPGRADE.get(), 9);
        return fac.unlockedBy("player_level", trigger());
    }
}