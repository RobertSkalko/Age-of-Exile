package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.CalculatedSpellDataSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

@Storable
public class CalculatedSpellData {

// todo add affixes and stuff

    @Store
    public String spell_id = "";

    @Store
    public int level = 1;

    public CalculatedSpellData() {

    }

    public void saveToStack(ItemStack stack) {
        CalculatedSpellDataSaving.Save(stack, this);
    }

    public BaseSpell getSpell() {
        return SlashRegistry.Spells()
            .get(spell_id);
    }

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

}
