package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.SkillEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class BonusYield extends Stat implements IGenerated<Stat> {

    public BonusRequirement req;

    public BonusYield(BonusRequirement req) {
        this.req = req;

        this.base = 0;
        this.min = 0;
        this.scaling = StatScaling.SLOW;
        this.group = StatGroup.Misc;

        this.statEffect = new Effect();
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String GUID() {
        return "bonus_" + req.id + "_yield";
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return req.name + " Yield";
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        for (BonusRequirement r : BonusRequirement.values()) {
            if (r != BonusRequirement.NONE) {
                list.add(new BonusYield(r));
            }
        }
        return list;
    }

    private class Effect extends SkillEffect {

        @Override
        public SkillDropData activate(SkillDropData effect, StatData data, Stat stat) {
            effect.originalDrops.forEach(x -> {
                if (RandomUtils.roll(data.getAverageValue())) {
                    effect.extraDrops.add(x.copy());
                }
            });
            return effect;
        }

        @Override
        public boolean canActivate(SkillDropData effect, StatData data, Stat stat) {
            return req.isAllowed(effect.source.world, effect.source.getBlockPos());
        }

        @Override
        public int GetPriority() {
            return 0;
        }
    }
}
