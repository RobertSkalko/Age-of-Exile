package com.robertx22.mine_and_slash.database.data.stats.types.misc;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SpellDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealPower;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAffectsStats;

public class HealToSpellDmgStat extends Stat implements IAffectsStats {

    private HealToSpellDmgStat() {
    }

    public static HealToSpellDmgStat getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void affectStats(EntityCap.UnitData data, StatData statData) {
        StatData heal = data.getUnit()
            .peekAtStat(HealPower.getInstance());

        StatData spelldmg = data.getUnit()
            .getCreateStat(SpellDamage.getInstance());

        heal.addFullyTo(spelldmg);
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.JUST_NAME;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Adds all your increased healing to your total spell damage.";
    }

    @Override
    public String locNameForLangFile() {
        return "Increased Healing applies to Spell Damage";
    }

    @Override
    public String GUID() {
        return "heal_to_dmg";
    }

    private static class SingletonHolder {
        private static final HealToSpellDmgStat INSTANCE = new HealToSpellDmgStat();
    }
}
