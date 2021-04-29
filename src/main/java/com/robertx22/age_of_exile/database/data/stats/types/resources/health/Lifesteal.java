package com.robertx22.age_of_exile.database.data.stats.types.resources.health;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResource;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class Lifesteal extends Stat {

    private Lifesteal() {
        this.statGroup = StatGroup.RESTORATION;
        this.statEffect = new Effect();
    }

    public static String GUID = "lifesteal";

    public static Lifesteal getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Percent of basic attack DMG added to health";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Lifesteal";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.AlmostLast.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            float healed = ((float) data.getAverageValue() * effect.data.getNumber() / 100);
            effect.addToRestore(new RestoreResource(RestoreResource.RestoreType.LEECH, ResourceType.HEALTH, healed));
            return effect;

        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.attackType.isAttack();
        }

    }

    private static class SingletonHolder {
        private static final Lifesteal INSTANCE = new Lifesteal();
    }
}
