package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.*;

@Storable
public class SpellsData {

    @Store
    public List<String> spells = new ArrayList<>();

    @Store
    public HashMap<Integer, String> hotbars = new HashMap<>();
    @Store
    public Set<String> schools = new HashSet<>();

    public void learnSpell(Spell spell, SpellSchool school) {

        spells.add(spell.GUID());
        schools.add(school.GUID());

    }

}
