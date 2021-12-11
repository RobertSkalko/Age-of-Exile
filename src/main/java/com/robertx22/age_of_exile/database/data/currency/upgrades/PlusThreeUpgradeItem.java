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
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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
        if (RandomUtils.roll(50)) {
            gear.onUpgrade(ctx.player, UpgradeData.SlotType.UP3);
        } else {
            if (gear.up.bless == GearBlessingType.DESTROY_PROTECT) {
                gear.up.bless = GearBlessingType.NONE;
                gear.up.regenerate(gear.getRarity());
                ctx.player.displayClientMessage(Words.FailedButSafe.locName()
                    .withStyle(TextFormatting.GOLD), false);
            } else {
                ctx.player.displayClientMessage(new StringTextComponent(TextFormatting.RED + "Upgrade failed, item was destroyed in the process."), false);
                return new ItemStack(Items.GUNPOWDER);
            }
        }

        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_BLUE_UPGRADE_SLOTS);
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
        return "50% chance to upgrade a blue slot, 50% chance to destroy item";
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(CurrencyItems.PLUS_TWO_UPGRADE.get(), 9);
        return fac.unlockedBy("player_level", trigger());
    }
}
