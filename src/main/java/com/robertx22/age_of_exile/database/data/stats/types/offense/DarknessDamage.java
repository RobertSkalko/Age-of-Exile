package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DarknessDamage extends Stat {

    public static String GUID = "darkness_dmg";

    private DarknessDamage() {
        this.min_val = 0;
        this.scaling = StatScaling.NONE;
        this.statEffect = new DarknessDamage.Effect();
        this.isLongStat = true;
    }

    public static DarknessDamage getInstance() {
        return DarknessDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Damage increase starts at mob spawn light level: 7. Maximum damage at 0 light level.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return SpecialStats.format("You deal up to " + SpecialStats.VAL1 + "% more damage when surrounded by darkness.");
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {

            float extra = data.getAverageValue() - 100;

            int lightlevel = effect.source.world.getLightLevel(effect.source.getBlockPos());

            if (lightlevel < 7) {

                float darkness = 7 - lightlevel;

                float multi = darkness / 7F;
                float inc = (extra + 100) * multi;

                effect.increaseByPercent(inc);
            }
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return true;
        }
    }

    private static class SingletonHolder {
        private static final DarknessDamage INSTANCE = new DarknessDamage();
    }
}


