package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class BonusSkillYield extends Stat implements IGenerated<Stat> {

    public PlayerSkillEnum skill;

    public BonusSkillYield(PlayerSkillEnum skill) {
        this.skill = skill;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    private BonusSkillYield() {
        this.base_val = 0;
        this.min_val = 0;
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;
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
}
