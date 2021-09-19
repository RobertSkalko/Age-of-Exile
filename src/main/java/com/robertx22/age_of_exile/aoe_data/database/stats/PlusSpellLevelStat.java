package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.IAfterStatCalc;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;

public class PlusSpellLevelStat extends BaseDatapackStat implements IAfterStatCalc {

    public static String SER_ID = "plus_spell_lvl";

    public String spell;

    transient String locname;

    public PlusSpellLevelStat(Spell spell) {
        this(spell.GUID());
        this.locname = "To " + spell.locName + " Level";
    }

    public PlusSpellLevelStat(String spell) {
        super(SER_ID);

        this.id = "plus_to_" + spell;
        this.spell = spell;

        this.is_perc = true;
        this.min = 0;
        this.scaling = StatScaling.NONE;
    }

    @Override
    public void affectUnit(EntityData unitdata, StatData statdata) {
        Load.spells(unitdata.getEntity())
            .getSpellsData()
            .addToLevelsFromStat(this.spell, (int) statdata.getValue());

    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}