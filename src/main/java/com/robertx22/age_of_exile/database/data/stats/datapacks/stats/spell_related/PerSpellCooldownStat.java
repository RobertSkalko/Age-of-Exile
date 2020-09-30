package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related;

import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base.DatapackSpellStat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class PerSpellCooldownStat extends DatapackSpellStat implements IStatEffects {

    public static String SER_ID = "spell_cooldown";

    public PerSpellCooldownStat(Spell spell) {
        super(SER_ID);
        this.spell = spell.GUID();
        this.spellname = spell.locNameForLangFile();

        this.id = spell.GUID() + "_cooldown";
        this.is_percent = true;
    }

    public PerSpellCooldownStat(String spell) {
        super(SER_ID);
        this.spell = spell;

        this.is_percent = true;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public String locDescForLangFile() {
        return "Changes cooldown of spell";
    }

    @Override
    public String locNameForLangFile() {
        return spellname + " Cooldown";
    }

    @Override
    public IStatEffect getEffect() {
        return EFF;
    }

    static Effect EFF = new Effect();

    private static class Effect extends BaseSpellCalcEffect {
        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            try {
                DatapackSpellStat es = (DatapackSpellStat) stat;
                if (es.spell.equals(effect.spell_id)) {
                    effect.data.add(SpellModEnum.COOLDOWN, data);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return effect;
        }
    }

}

