package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalSpellDamage extends ElementalStat {

    public static MapWrapper<Elements, ElementalSpellDamage> MAP = new MapWrapper<>();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalSpellDamage) x));
        return list;
    }

    public ElementalSpellDamage(Elements element) {
        super(element);
        this.group = StatGroup.ELEMENTAL;
        this.statEffect = new Effect();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalSpellDamage(element);
    }

    @Override
    public String GUID() {
        return "spell_" + this.getElement().guidName + "_damage";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_spell_damage";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement()
            .dmgName + " Skill Damage";

    }

    @Override
    public String locDescForLangFile() {
        return "Increases damage of spells of that element.";
    }

    private static class Effect extends BaseDamageIncreaseEffect {

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {

            return effect.attackType.isSpell() && effect.GetElement() != null && effect.GetElement()
                .equals(stat.getElement());

        }

    }

}

