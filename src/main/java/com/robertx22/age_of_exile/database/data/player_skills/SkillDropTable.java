package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusRequirement;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

import java.util.ArrayList;
import java.util.List;

public class SkillDropTable {

    public static String FISH_TAG = "fish";
    public static String INK_TAG = "ink";

    public float loot_chance_per_action_exp = 0.5F;

    public String tag = "";

    public BonusRequirement req = BonusRequirement.NONE;

    public SkillItemTier tier = SkillItemTier.TIER4;

    public List<SkillDropReward> drop_rewards = new ArrayList<>();

}
