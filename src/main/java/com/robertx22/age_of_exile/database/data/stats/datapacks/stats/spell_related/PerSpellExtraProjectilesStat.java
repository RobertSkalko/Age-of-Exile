package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base.DatapackSpellStat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public class PerSpellExtraProjectilesStat extends DatapackSpellStat implements IExtraStatEffect {

    public static String SER_ID = "extra_projectiles";

    public PerSpellExtraProjectilesStat(Spell spell) {
        super(SER_ID);
        this.spell = spell.GUID();
        this.spellname = spell.locNameForLangFile();

        this.id = "extra_" + spell.GUID() + "_projectiles";
        this.is_percent = false;
    }

    public PerSpellExtraProjectilesStat(String spell) {
        super(SER_ID);
        this.spell = spell;

        this.is_percent = false;
    }

    String spellname;

    public String spell = "";

    public Spell getSpell() {
        return Database.Spells()
            .get(spell);
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc.per_spell_extra_proj";
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public String locDescForLangFile() {
        return "Adds x amount of projectiles to spell";
    }

    @Override
    public String locNameForLangFile() {
        return "Extra " + spellname + " Projectiles";
    }

    @Override
    public IStatEffect getEffect() {
        return EFF;
    }

    static Effect EFF = new Effect();

    static class Effect extends BaseSpellCalcEffect {
        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            try {
                DatapackSpellStat es = (DatapackSpellStat) stat;
                if (effect.spell_id.equals(es.spell)) {
                    effect.data.extraProjectiles = (int) data.getAverageValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return effect;
        }
    }

}
