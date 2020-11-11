package com.robertx22.age_of_exile.saveclasses.player_skills;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class PlayerSkillsData {

    @Store
    private HashMap<PlayerSkillEnum, PlayerSkillData> map = new HashMap<>();

    public PlayerSkillData getDataFor(PlayerSkillEnum skill) {

        if (!map.containsKey(skill)) {
            map.put(skill, new PlayerSkillData());
        }

        return map.get(skill);
    }
}
