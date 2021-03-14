package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.SkillEffect;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class BonusSkillYield extends Stat implements IGenerated<Stat> {

    public PlayerSkillEnum skill;

    public BonusSkillYield(PlayerSkillEnum skill) {
        this.skill = skill;
        this.base_val = 0;
        this.min_val = 0;
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;

        this.statEffect = new Effect();
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String GUID() {
        return "bonus_" + skill.id + "_yield";
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
        return skill.word.translate() + " Yield";
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        for (PlayerSkillEnum r : PlayerSkillEnum.values()) {
            list.add(new BonusSkillYield(r));
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
            return effect.skill == skill;
        }

        @Override
        public int GetPriority() {
            return 0;
        }
    }

}
