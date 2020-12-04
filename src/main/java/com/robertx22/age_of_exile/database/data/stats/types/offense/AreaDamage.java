package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class AreaDamage extends Stat {

    public static String GUID = "area_dmg";

    private AreaDamage() {
        this.scaling = StatScaling.NONE;
        this.statEffect = new Effect();
    }

    public static AreaDamage getInstance() {
        return AreaDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects dmg done by area of effect abilities. Think meteor or other large aoe spells";
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
        return "Spell Area of Effect Damage";
    }

    private static class Effect extends BaseDamageIncreaseEffect {

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            if (effect instanceof SpellDamageEffect) {
                SpellDamageEffect sd = (SpellDamageEffect) effect;
                return effect.attackType.isSpell() && sd.getSpell()
                    .getConfig().tags.contains(SkillGemTag.AREA);
            }
            return false;

        }

    }

    private static class SingletonHolder {
        private static final AreaDamage INSTANCE = new AreaDamage();
    }
}
