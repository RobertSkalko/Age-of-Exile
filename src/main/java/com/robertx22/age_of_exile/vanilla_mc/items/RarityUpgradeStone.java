package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.ModifyResult;
import com.robertx22.age_of_exile.database.data.currency.base.ResultItem;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

public class RarityUpgradeStone extends Item implements ICurrencyItemEffect, IGUID {

    public RarityUpgradeStone() {
        super(new Item.Properties().stacksTo(64)
            .tab(CreativeTabs.GemRuneCurrency));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {

        if (this.allowdedIn(group)) {

            for (GearRarity rar : ExileDB.GearRarities()
                .getList()) {
                if (rar.canUpgradeRarityIfUpgradeLevelIsHighEnough()) {
                    stacks.add(rar.getRarityUpgradeStack());
                }
            }

        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {

            GearRarity rar = GearRarity.getRarityFromEssence(stack);

            if (rar != null) {
                tooltip.clear();
                tooltip.add(rar.locName()
                    .withStyle(rar.textFormatting())
                    .append(" ")
                    .append(Words.UpgradeStone.locName())
                    .withStyle(rar.textFormatting()));

                tooltip.add(new StringTextComponent("Upgrades a +" + rar.upgrade_lvl_to_increase_rar + " ").append(rar.locName())
                    .append(" to ")
                    .append(rar.getHigherRarity()
                        .locName()));

                tooltip.add(new StringTextComponent("Click on an item to upgrade"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String GUID() {
        return "rarity/upgrade_stone/base";
    }

    @Override
    public boolean canItemBeModified(LocReqContext context) {

        GearRarity rar = GearRarity.getRarityFromEssence(context.Currency);

        if (context.isGear()) {
            GearItemData gear = (GearItemData) context.data;
            if (rar != null && gear.up.getUpgradeLevel() >= rar.upgrade_lvl_to_increase_rar) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ResultItem modifyItem(LocReqContext context) {
        if (context.effect.canItemBeModified(context)) {

            ItemStack copy = context.stack.copy();

            if (context.isGear()) {
                GearItemData gear = Gear.Load(copy);
                gear.upgradeToHigherRarity();
                Gear.Save(copy, gear);
            }

            return new ResultItem(copy, ModifyResult.SUCCESS);
        } else {
            return new ResultItem(ItemStack.EMPTY, ModifyResult.NONE);
        }

    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {
        return null;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(); // todo
    }

}

