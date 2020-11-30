package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.database.registry.Database;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class SpellGemData {

    @Store
    public String id = "";
    @Store
    public String rar = "";
    @Store
    public int stat_perc = 100;
    @Store
    public int lvl = 100;

    public SpellGem getSpellGem() {
        return Database.SpellGems()
            .get(id);
    }

}
