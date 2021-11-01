package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.ItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.ItemDefault;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class CurrencyItem extends Item implements ExileRegistry<CurrencyItem>, IRarity, ICurrencyItemEffect, IWeighted, IAutoLocDesc, IAutoLocName, IAutoModel {

    public ItemType itemTypesUsableOn = ItemType.GEAR;

    public static TextFormatting nameColor = TextFormatting.RED;

    public abstract String GUID();

    public CurrencyItem(String name) {
        super(new ItemDefault().stacksTo(64)
            .tab(CreativeTabs.GemRuneCurrency));
    }

    public abstract int getTier();

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public final boolean canItemBeModified(LocReqContext context) {

        if (this.itemTypesUsableOn.isType(context.stack) == false) {
            return false;
        }

        if (context.isGear()) {

            GearItemData gear = (GearItemData) context.data;

            if (this.getInstability() > 0) {

                if (gear.getInstability() >= ServerContainer.get().MAX_INSTABILITY.get()) {
                    return false;
                }
            }
        }
        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locDescLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString() + ".desc";
    }

    public abstract int getWeight();

    @Override
    public int Weight() {
        return getWeight();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip,
                                ITooltipFlag flagIn) {

        tooltip.add(
            this.locDesc()
                .withStyle(TextFormatting.YELLOW));

        tooltip.add(ItemType.getTooltipString(this.itemTypesUsableOn));

        TooltipUtils.addEmpty(tooltip);

        tooltip.add(
            Words.Item_modifiable_in_station.locName()
                .withStyle(TextFormatting.BLUE));

        if (this.getInstability() > 0) {
            tooltip.add(Words.Instability.locName()
                .withStyle(TextFormatting.RED)
                .append(": " + getInstability()));

        }

        if (this.getBreakChance() > 0) {
            tooltip.add(Words.BreakChance.locName()
                .append(": " + (int) getBreakChance())
                .withStyle(TextFormatting.RED));
        }

        TooltipUtils.addEmpty(tooltip);

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.CURRENCY_ITEMS;
    }

    @Override
    public String getRarityRank() {
        return IRarity.UNCOMMON;
    }

    @Override
    public Rarity getRarity() {
        return ExileDB.MobRarities()
            .get(getRarityRank());
    }

}
