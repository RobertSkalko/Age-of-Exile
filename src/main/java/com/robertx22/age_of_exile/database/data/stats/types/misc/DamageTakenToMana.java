package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

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
        return "Damage Taken to Mana";
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
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

            float restore = effect.data.getNumber() * data.getAverageValue() / 100F; // todo dmg number

            if (restore > 0) {
                RestoreResourceEvent mana = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.mana, RestoreType.heal, restore)
                    .build();
                mana.Activate();

            }

            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return true;
        }

    }

    private static class SingletonHolder {
        private static final DamageTakenToMana INSTANCE = new DamageTakenToMana();
    }
}
