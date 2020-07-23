package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.exiled_lib.registry.ISlashRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.datasaving.ItemType;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ITiered;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.ItemDefault;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public abstract class CurrencyItem extends Item implements ISlashRegistryEntry<CurrencyItem>, ISalvagable,
    ICurrencyItemEffect, IWeighted, ITiered, IAutoLocDesc, IAutoLocName, IAutoModel {

    public ItemType itemTypesUsableOn = ItemType.GEAR;

    public static Formatting nameColor = Formatting.RED;

    public abstract String GUID();

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

        tooltip.add(Styles.YELLOWCOMP()
            .append(this.locDesc()));

        String test = this.locDescLangFileGUID();

        tooltip.add(ItemType.getTooltipString(this.itemTypesUsableOn));

        TooltipUtils.addEmpty(tooltip);

        tooltip.add(Styles.BLUECOMP()
            .append(Words.Item_modifiable_in_station.locName()));

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
        return Rarities.Mobs.get(getRarityRank());
    }

    @Override
    public ItemStack getSalvageResult(float salvageBonus) {

        int min = 1;
        int max = 2;

        min = tryIncreaseAmount(salvageBonus, min);
        max = tryIncreaseAmount(salvageBonus, max);

        int amount = RandomUtils.RandomRange(min, max);

        Item item = ItemUtils.randomMagicEssence();
        ItemStack stack = new ItemStack(item);
        stack.setCount(amount);

        return stack;
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return true;
    }

}
