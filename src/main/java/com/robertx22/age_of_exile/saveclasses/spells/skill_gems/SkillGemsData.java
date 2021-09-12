package com.robertx22.age_of_exile.saveclasses.spells.skill_gems;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class SkillGemsData {

    @Store
    public HashMap<String, Integer> extra_lvls = new HashMap<>();
    @Store
    public HashMap<String, Integer> allocated_lvls = new HashMap<>();
    @Store
    public HashMap<Integer, String> hotbars = new HashMap<>();

    public int getLevelOf(String id) {
        return allocated_lvls.getOrDefault(id, 0) + extra_lvls.getOrDefault(id, 0);
    }

}
