package com.robertx22.mine_and_slash.saveclasses.item_classes;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Jewel;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.DataItemType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;

@Storable
public class JewelData implements ITooltip, ICommonDataItem {

    @Store
    public AffixData affix;

    public JewelData() {

    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

        TooltipInfo info = new TooltipInfo(ctx.data);
        GearItemData gear = Gear.Load(ctx.stack);

        ctx.tooltip.add(TooltipUtils.level(affix.level));

        ctx.tooltip.add(new SText(""));

        ctx.tooltip
            .addAll(this.affix.GetTooltipString(info, null));

    }

    public void randomize(int level) {

        this.affix = new AffixData(RandomUtils.roll(50) ? Affix.Type.prefix : Affix.Type.suffix);

        this.affix.level = level;

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
        return new ItemStack(ModItems.MAGIC_ESSENCE.get());
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
