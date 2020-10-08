package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.ItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.ItemDefault;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public abstract class CurrencyItem extends Item implements ISlashRegistryEntry<CurrencyItem>, ISalvagable,
    ICurrencyItemEffect, IWeighted, ITiered, IAutoLocDesc, IAutoLocName, IAutoModel {

    public ItemType itemTypesUsableOn = ItemType.GEAR;

    public static Formatting nameColor = Formatting.RED;

    public abstract String GUID();

    @Override
    public StationType forStation() {
        return StationType.MODIFY;
    }

    public CurrencyItem(String name) {
        super(new ItemDefault().maxCount(64));
    }

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
            if (this.getInstability() > 0) {
                GearItemData gear = (GearItemData) context.data;
                if (gear.sealed) {
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
        return Registry.ITEM.getId(this)
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
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip,
                              TooltipContext flagIn) {

        tooltip.add(
            this.locDesc()
                .formatted(Formatting.YELLOW));

        tooltip.add(ItemType.getTooltipString(this.itemTypesUsableOn));

        TooltipUtils.addEmpty(tooltip);

        tooltip.add(
            Words.Item_modifiable_in_station.locName()
                .formatted(Formatting.BLUE));

        if (this.getInstability() > 0) {
            tooltip.add(Words.Instability.locName()
                .formatted(Formatting.RED)
                .append(": " + getInstability()));

        }

        TooltipUtils.addEmpty(tooltip);

    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.CURRENCY_ITEMS;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public Rarity getRarity() {
        return SlashRegistry.MobRarities()
            .get(getRarityRank());
    }

    @Override
    public List<ItemStack> getSalvageResult(float salvageBonus) {

        int min = 1;
        int max = 2;

        min = tryIncreaseAmount(salvageBonus, min);
        max = tryIncreaseAmount(salvageBonus, max);

        int amount = RandomUtils.RandomRange(min, max);

        Item item = ItemUtils.randomMagicEssence();
        ItemStack stack = new ItemStack(item);
        stack.setCount(amount);

        return Arrays.asList(stack);
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return true;
    }

}
