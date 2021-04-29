package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DamageTakenToMana extends Stat {

    private DamageTakenToMana() {
        this.statEffect = new Effect();
    }

    public static DamageTakenToMana getInstance() {
        return DamageTakenToMana.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "% of damage taken is restored to mana.";
    }

    @Override
    public String GUID() {
        return "dmg_taken_to_mana";
    }

    @Override
    public String locNameForLangFile() {
        return "Of Damage Taken to Mana";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.Last.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {

            float restore = effect.data.getNumber() * data.getAverageValue() / 100F; // todo dmg number

            if (restore > 0) {
                ResourcesData.Context mana = new ResourcesData.Context(effect.targetData, effect.target,
                    ResourceType.MANA,
                    restore,
                    ResourcesData.Use.RESTORE
                );
                effect.targetData.getResources()
                    .modify(mana);
            }

            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return true;
        }

    }

    private static class SingletonHolder {
        private static final DamageTakenToMana INSTANCE = new DamageTakenToMana();
    }
}
