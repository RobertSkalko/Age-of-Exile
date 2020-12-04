package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class HealToMagicShield extends Stat {
    public static String GUID = "heal_to_ms";

    private HealToMagicShield() {
        this.statGroup = StatGroup.RESTORATION;
        this.statEffect = new Effect();
    }

    public static HealToMagicShield getInstance() {
        return HealToMagicShield.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects all heal effects applied to you.";
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

    private static class Effect extends BaseStatEffect<ModifyResourceEffect> {

        private Effect() {
            super(ModifyResourceEffect.class);
        }

        @Override
        public int GetPriority() {
            return Priority.Last.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public ModifyResourceEffect activate(ModifyResourceEffect effect, StatData data, Stat stat) {

            float taken = effect.ctx.amount * data.getAverageValue() / 100F;

            ResourcesData.Context ms = new ResourcesData.Context(effect.ctx.targetData, effect.ctx.target,
                ResourceType.MAGIC_SHIELD, taken,
                ResourcesData.Use.RESTORE
            );
            effect.ctx.targetData.getResources()
                .modify(ms);

            effect.number -= taken;

            return effect;
        }

        @Override
        public boolean canActivate(ModifyResourceEffect effect, StatData data, Stat stat) {
            if (effect.ctx.use == ResourcesData.Use.RESTORE) {
                if (effect.ctx.amount > 0) {
                    if (effect.ctx.type == ResourceType.HEALTH) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public String locNameForLangFile() {
        return "of Heal effects to Magic Shield";
    }

    private static class SingletonHolder {
        private static final HealToMagicShield INSTANCE = new HealToMagicShield();
    }
}
