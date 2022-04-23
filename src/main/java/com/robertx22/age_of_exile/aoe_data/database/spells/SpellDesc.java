package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.stream.Collectors;

public class SpellDesc {

    public static String NEWLINE = "[LINE]";

    public static List<String> getTooltip(LivingEntity caster, Spell spell) {

        String tip = CLOC.translate(spell.locDesc());

        for (ValueCalculation calc : ExileDB.ValueCalculations()
            .getList()) {
            String id = "[calc:" + calc.id + "]";

            // todo
            ///   tip = tip.replace(id, CLOC.translate(calc.getShortTooltip(Load.Unit(caster))));
        }

        List<String> list = TooltipUtils.cutIfTooLong(tip);

        list = list.stream()
            .map(x -> TextFormatting.GRAY + x)
            .collect(Collectors.toList());

        return list;

    }
}
