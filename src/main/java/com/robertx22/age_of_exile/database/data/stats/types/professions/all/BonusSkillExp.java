package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class BonusSkillExp extends Stat implements IGenerated<Stat> {

    PlayerSkillEnum skill;

    public BonusSkillExp(PlayerSkillEnum skill) {
        this.skill = skill;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat.skill_xp";
    }

    @Override
    public String locDescForLangFile() {
        return "Increases xp gain of that profession/skill";
    }

    @Override
    public String GUID() {
        return "bonus_" + skill.id + "_exp";
    }

    @Override
    public String locNameForLangFile() {
        return "Bonus " + skill.word.translate() + " Exp";
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        PlayerSkillEnum.getAll()
            .forEach(x -> list.add(new BonusSkillExp(x)));
        return list;
    }

}

