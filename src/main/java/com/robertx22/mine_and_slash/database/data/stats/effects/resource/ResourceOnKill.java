package com.robertx22.mine_and_slash.database.data.stats.effects.resource;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;

public abstract class ResourceOnKill extends BaseDamageEffect {

    public static ResourceOnKill HEALTH = new ResourceOnKill() {
        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.healthHealedOnKill += data.getAverageValue();
            return effect;
        }
    };
    public static ResourceOnKill MANA = new ResourceOnKill() {
        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.manaRestoredOnKill += data.getAverageValue();
            return effect;
        }
    };
    public static ResourceOnKill MAGIC_SHIELD = new ResourceOnKill() {
        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.magicShieldRestoredOnKill += data.getAverageValue();
            return effect;
        }
    };

    @Override
    public int GetPriority() {
        return Priority.Second.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return true;
    }

}
