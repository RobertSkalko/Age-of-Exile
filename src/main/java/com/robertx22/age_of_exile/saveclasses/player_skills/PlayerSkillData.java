package com.robertx22.age_of_exile.saveclasses.player_skills;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class PlayerSkillData {

    @Store
    public int lvl = 1;
    @Store
    public int exp = 0;

}
