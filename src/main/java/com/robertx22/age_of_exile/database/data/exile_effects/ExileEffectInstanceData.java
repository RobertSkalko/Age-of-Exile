package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class ExileEffectInstanceData {

    @Store
    public EntitySavedSpellData spellData;

    @Store
    public int stacks = 1;

    @Store
    public float str_multi = 1;

}
