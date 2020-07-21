package com.robertx22.mine_and_slash.saveclasses.item_classes;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.SkillGemRarity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.SkillGem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.DataItemType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

@Storable
public class SkillGemData implements ICommonDataItem<SkillGemRarity> {

// todo add affixes and stuff

    @Store
    public String spell_id = "";

    @Store
    public int rarity = 0;

    @Store
    public int level = 1;

    @Store
    public int stat_percents = 0;

    public SkillGemData() {

    }

    @Override
    public DataItemType getDataType() {
        return DataItemType.SKILL_GEM;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        SkillGem.Save(stack, this);
    }

    public BaseSpell getSpell() {
        return SlashRegistry.Spells()
            .get(spell_id);
    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

        BaseSpell spell = SlashRegistry.Spells()
            .get(spell_id);

        ctx.tooltip
            .add(spell.getLocName());

        ctx.tooltip.addAll(spell.GetTooltipString(new TooltipInfo(ClientOnly.getPlayer()), this));

        ctx.tooltip.add(TooltipUtils.rarity(getRarity()));
        ctx.tooltip.add(new SText(Formatting.YELLOW + "Level: " + level));
    }

    @Override
    public ItemStack getSalvageResult(float salvageBonus) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getRarityRank() {
        return rarity;
    }

    @Override
    public SkillGemRarity getRarity() {
        return Rarities.SkillGems.get(rarity);
    }

    @Override
    public int getTier() {
        return 0;
    }

    public ItemStack toItemStack() {
        ItemStack stack = new ItemStack(SlashRegistry.Spells()
            .get(spell_id)
            .getItem());

        this.saveToStack(stack);

        return stack;
    }

}
