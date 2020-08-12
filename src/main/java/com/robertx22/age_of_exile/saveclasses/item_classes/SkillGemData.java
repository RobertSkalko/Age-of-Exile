package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.SkillGem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.DataItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
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

        try {
            TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());

            if (!SlashRegistry.Spells()
                .isRegistered(spell_id)) {
                return;
            }

            BaseSpell spell = SlashRegistry.Spells()
                .get(spell_id);

            if (spell == null) {
                return;
            }

            if (spell.getElement() == null) {
                System.out.println(spell.GUID());
            }

            ctx.tooltip
                .add(new LiteralText(spell.getElement().format + spell.getElement().icon + " ").append(spell.getLocName()));

            ctx.tooltip.addAll(spell.GetTooltipString(info, this));

            ctx.tooltip.add(getRarity().locName()
                .append(" Level " + level + " item")
                .formatted(getRarity().textFormatting()));

            if (!Screen.hasShiftDown()) {
                ctx.tooltip.add(new LiteralText(Formatting.BLUE + "")
                    .append(Words.AltDescShiftDetails.locName()
                        .formatted(Formatting.BLUE)));
            }

            TooltipUtils.removeDoubleBlankLines(ctx.tooltip);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ItemStack getSalvageResult(float salvageBonus) {
        Item item = ItemUtils.randomMagicEssence();
        ItemStack stack = new ItemStack(item);
        return stack;
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
