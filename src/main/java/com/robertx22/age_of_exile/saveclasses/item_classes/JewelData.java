package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.uncommon.datasaving.Jewel;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.DataItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;

@Storable
public class JewelData implements ITooltip, ICommonDataItem {

    @Store
    public AffixData affix;

    public JewelData() {

    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

        TooltipInfo info = new TooltipInfo(ctx.data);

        ctx.tooltip.add(TooltipUtils.level(affix.level));

        ctx.tooltip.add(new SText(""));

        MutableText txt = affix.affixType == Affix.Type.prefix ? Words.Prefix.locName() : Words.Suffix.locName();
        ctx.tooltip.add(txt.append(new LiteralText(": ").append(affix.getAffix()
            .locName())));

        ctx.tooltip
            .addAll(this.affix.GetTooltipString(info, null));

    }

    public void randomize(int level) {

        this.affix = new AffixData(RandomUtils.roll(50) ? Affix.Type.prefix : Affix.Type.suffix);

        this.affix.level = MathHelper.clamp(RandomUtils.RandomRange(level - 5, level + 2), 1, ModConfig.get().Server.MAX_LEVEL);

        this.affix.baseAffix = SlashRegistry.Affixes()
            .getFilterWrapped(x -> x.type == affix.affixType)
            .random()
            .GUID();

        this.affix.tier = RandomUtils.weightedRandom(affix.getAffix().tierMap.values()).tier;
        this.affix.percent = RandomUtils.RandomRange(20, 100);

    }

    @Override
    public DataItemType getDataType() {
        return DataItemType.JEWEL;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        Jewel.Save(stack, this);
    }

    @Override
    public ItemStack getSalvageResult(float salvageBonus) {
        return new ItemStack(ModRegistry.MISC_ITEMS.MAGIC_ESSENCE);
    }

    @Override
    public int getRarityRank() {
        return 0;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(0);
    }

    @Override
    public int getTier() {
        return 0;
    }
}
