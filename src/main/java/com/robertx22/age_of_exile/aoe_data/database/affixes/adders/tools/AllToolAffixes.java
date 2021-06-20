package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusSkillExp;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.DoubleDropChance;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.TripleDropChance;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class AllToolAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("double_drop_chance_pre")
            .Named("Double Drops")
            .tier(1, new StatModifier(20, 20, DoubleDropChance.getInstance()))
            .tier(2, new StatModifier(15, 15, DoubleDropChance.getInstance()))
            .tier(3, new StatModifier(10, 10, DoubleDropChance.getInstance()))
            .includesTags(SlotTag.tool_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("triple_drop_chance_pre")
            .Named("Triple Drops")
            .tier(1, new StatModifier(20, 20, TripleDropChance.getInstance()))
            .tier(2, new StatModifier(15, 15, TripleDropChance.getInstance()))
            .tier(3, new StatModifier(10, 10, TripleDropChance.getInstance()))
            .includesTags(SlotTag.tool_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("mining_exp")
            .Named("Bonus Exp")
            .tier(1, new StatModifier(30, 30, new BonusSkillExp(PlayerSkillEnum.MINING)))
            .tier(2, new StatModifier(20, 20, new BonusSkillExp(PlayerSkillEnum.MINING)))
            .tier(3, new StatModifier(10, 10, new BonusSkillExp(PlayerSkillEnum.MINING)))
            .includesTags(SlotTag.pickaxe)
            .Suffix()
            .Build();

        AffixBuilder.Normal("farming_exp")
            .Named("Bonus Exp")
            .tier(1, new StatModifier(30, 30, new BonusSkillExp(PlayerSkillEnum.FARMING)))
            .tier(2, new StatModifier(20, 20, new BonusSkillExp(PlayerSkillEnum.FARMING)))
            .tier(3, new StatModifier(10, 10, new BonusSkillExp(PlayerSkillEnum.FARMING)))
            .includesTags(SlotTag.hoe)
            .Suffix()
            .Build();

        AffixBuilder.Normal("fishing_exp")
            .Named("Bonus Exp")
            .tier(1, new StatModifier(30, 30, new BonusSkillExp(PlayerSkillEnum.FISHING)))
            .tier(2, new StatModifier(20, 20, new BonusSkillExp(PlayerSkillEnum.FISHING)))
            .tier(3, new StatModifier(10, 10, new BonusSkillExp(PlayerSkillEnum.FISHING)))
            .includesTags(SlotTag.fishing_rod)
            .Suffix()
            .Build();

    }
}
