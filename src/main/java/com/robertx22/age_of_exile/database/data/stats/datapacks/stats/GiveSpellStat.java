package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class GiveSpellStat extends DatapackStat {

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
    }

    String spellname;

    public String spell = "";

    public Spell getSpell() {
        return SlashRegistry.Spells()
            .get(spell);
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
    public String locNameForLangFile() {
        return "Unlocks " + spellname;
    }

}
