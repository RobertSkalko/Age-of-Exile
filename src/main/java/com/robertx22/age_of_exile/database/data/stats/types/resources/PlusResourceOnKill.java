package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ConditionalRestoreResource;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class PlusResourceOnKill extends Stat {

    public static PlusResourceOnKill HEALTH = new PlusResourceOnKill(Health.getInstance(), new Effect(ResourceType.HEALTH));
    public static PlusResourceOnKill MANA = new PlusResourceOnKill(Mana.getInstance(), new Effect(ResourceType.MANA));
    public static PlusResourceOnKill MAGIC_SHIELD = new PlusResourceOnKill(MagicShield.getInstance(), new Effect(ResourceType.MAGIC_SHIELD));

    Stat statRestored;

    private PlusResourceOnKill(Stat statRestored, Effect effect) {
        this.statRestored = statRestored;
        this.statEffect = effect;
        this.statGroup = StatGroup.RESTORATION;
        this.scaling = StatScaling.NORMAL;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return new StatNameRegex() {
            @Override
            public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {
                return StatNameRegex.VALUE + " " + StatNameRegex.NAME;
            }
        };
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
        return "Restore " + statRestored.locNameForLangFile() + " Every time you kill a mob";
    }

    @Override
    public String locNameForLangFile() {
        return statRestored.locNameForLangFile() + " on Kill";
    }

    @Override
    public String GUID() {
        return statRestored.GUID() + "_on_kill";
    }

    private static class Effect extends BaseDamageEffect {

        ResourceType resource;

        private Effect(ResourceType resource) {
            this.resource = resource;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.addToRestore(new ConditionalRestoreResource(ConditionalRestoreResource.RestoreType.LEECH, resource, data.getAverageValue(), x -> x.target.isDead()));
            return effect;
        }

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

}
