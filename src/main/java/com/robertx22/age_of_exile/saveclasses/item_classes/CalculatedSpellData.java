package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class CalculatedSpellData {

    public String spell_id = "";

    public int level = 1;

    SpellStatsCalculationEvent.CalculatedSpellConfiguration config;

    public CalculatedSpellData() {

    }

    public static CalculatedSpellData create(SkillGemData gem, LivingEntity caster, Spell spell, SpellStatsCalculationEvent.CalculatedSpellConfiguration spellConfig) {
        CalculatedSpellData data = new CalculatedSpellData();
        data.spell_id = spell.GUID();
        data.config = spellConfig;
        data.level = gem == null ? 1 : gem.lvl;
        return data;

    }

    public Spell getSpell() {
        return Database.Spells()
            .get(spell_id);
    }

    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();
        try {

            if (!Database.Spells()
                .isRegistered(spell_id)) {
                return list;
            }

            Spell spell = Database.Spells()
                .get(spell_id);

            if (spell == null) {
                return list;
            }

            list
                .add(new LiteralText("").append(spell.locName()));

            list.addAll(spell.GetTooltipString(null, info, this));

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
