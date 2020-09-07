package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class CalculatedSpellData implements ITooltipList {

// todo add affixes and stuff

    @Store
    public String spell_id = "";

    @Store
    public int level = 1;

    public CalculatedSpellData() {

    }

    public BaseSpell getSpell() {
        return SlashRegistry.Spells()
            .get(spell_id);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();
        try {

            if (!SlashRegistry.Spells()
                .isRegistered(spell_id)) {
                return list;
            }

            BaseSpell spell = SlashRegistry.Spells()
                .get(spell_id);

            if (spell == null) {
                return list;
            }

            if (spell.getElement() == null) {
                System.out.println(spell.GUID());
            }

            list
                .add(new LiteralText(spell.getElement().format + spell.getElement().icon + " ").append(spell.getLocName()));

            list.addAll(spell.GetTooltipString(info, this));

            if (!Screen.hasShiftDown()) {
                list.add(new LiteralText(Formatting.BLUE + "")
                    .append(Words.AltDescShiftDetails.locName()
                        .formatted(Formatting.BLUE)));
            }

            TooltipUtils.removeDoubleBlankLines(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
