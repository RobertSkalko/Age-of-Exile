package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base.DatapackSpellStat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public class PerSpellProjectileSpeedStat extends DatapackSpellStat implements IExtraStatEffect {

    public static String SER_ID = "spell_proj_speed";

    public PerSpellProjectileSpeedStat(Spell spell) {
        super(SER_ID);
        this.spell = spell.GUID();
        this.spellname = spell.locNameForLangFile();

        this.id = spell.GUID() + "_proj_speed";
        this.is_percent = true;
    }

    public PerSpellProjectileSpeedStat(String spell) {
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
        return "Changes projectile speed of spell";
    }

    @Override
    public String locNameForLangFile() {
        return spellname + " Projectile Speed";
    }

    @Override
    public IStatEffect getEffect() {
        return EFF;
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc.per_spell_proj_speed";
    }

    static Effect EFF = new Effect();

    private static class Effect extends BaseSpellCalcEffect {
        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            try {
                DatapackSpellStat es = (DatapackSpellStat) stat;
                if (es.spell.equals(effect.spell_id)) {
                    effect.data.add(SpellModEnum.PROJECTILE_SPEED, data);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return effect;
        }
    }

}

