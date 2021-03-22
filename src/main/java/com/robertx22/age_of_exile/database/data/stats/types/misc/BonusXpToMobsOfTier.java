package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class BonusXpToMobsOfTier extends Stat implements IGenerated<Stat> {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        for (SkillItemTier x : SkillItemTier.values()) {
            list.add(new BonusXpToMobsOfTier(x));
        }
        return list;
    }

    private SkillItemTier tier;

    public BonusXpToMobsOfTier(SkillItemTier type) {
        this.tier = type;
        this.statGroup = StatGroup.Misc;
        this.max_val = 100;
    }

    @Override
    public String locDescForLangFile() {
        return "More exp gained for killing mobs of that tier.";
    }

    @Override
    public String GUID() {
        return "bonus_exp_for_t" + this.tier.getDisplayTierNumber() + "_mob";
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
        return Ref.MODID + ".stat_desc." + "ele_wep_damage";
    }

    @Override
    public String locNameForLangFile() {
        return "Tier " + tier.getDisplayTierNumber() + " Mob Exp";
    }

    public boolean worksOn(EntityCap.UnitData mob) {
        return tier.levelRange.isLevelInRange(mob.getLevel());

    }

}

