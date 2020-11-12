package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class PlayerSkillsData implements IApplyableStats {

    @Store
    private HashMap<PlayerSkillEnum, PlayerSkillData> map = new HashMap<>();

    public PlayerSkillData getDataFor(PlayerSkillEnum skill) {

        if (!map.containsKey(skill)) {
            map.put(skill, new PlayerSkillData());
        }

        return map.get(skill);
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        map.entrySet()
            .forEach(x -> {
                PlayerSkill skill = SlashRegistry.PlayerSkills()
                    .get(x.getKey().id);

                skill.getClaimedStats(x.getValue()
                    .getLvl())
                    .forEach(s -> s.stats.forEach(e -> e.applyStats(data)));

            });
    }
}
