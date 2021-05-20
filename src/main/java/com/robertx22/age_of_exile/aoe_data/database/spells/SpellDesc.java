package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.library_of_exile.utils.CLOC;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class SpellDesc {

    public static String NEWLINE = "[LINE]";

    public static List<String> getTooltip(Spell spell) {
        return Arrays.asList(StringUtils.split(CLOC.translate(spell.locDesc()), NEWLINE));
    }
}
