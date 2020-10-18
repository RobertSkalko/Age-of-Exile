package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base.DatapackSpellStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.mmorpg.Ref;

public class GiveSpellStat extends DatapackSpellStat {

    public static String SER_ID = "give_spell";

    public GiveSpellStat(Spell spell) {
        super(SER_ID);
        this.spell = spell.GUID();
        this.spellname = spell.locNameForLangFile();
        this.id = "unlock_" + spell.GUID();
    }

    public GiveSpellStat(String spell) {
        super(SER_ID);
        this.spell = spell;
        this.id = "unlock_" + spell;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.JUST_NAME;
    }

    @Override
    public String locDescForLangFile() {
        return "Unlocks the spell.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc.unlock_spell";
    }

    @Override
    public String locNameForLangFile() {
        return "Unlocks " + spellname;
    }

}
