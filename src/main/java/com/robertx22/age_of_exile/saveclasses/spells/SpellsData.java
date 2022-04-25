package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Storable
public class SpellsData {

    @Store
    public Set<String> spells = new HashSet<>();

    @Store
    public HashMap<Integer, String> hotbars = new HashMap<>();

    @Store
    public String school = "";

    public boolean hasSpell(Spell spell) {
        if (spells.contains(spell.GUID())) {
            return true;
        }
        if (spell.config.everyone_has) {
            return true;
        }

        return false;

    }

    public void learnSpell(Spell spell, SpellSchool school) {

        //spells.removeIf(x -> x.equals(spell.GUID()));
        spells.removeIf(x -> !ExileDB.Spells()
            .isRegistered(x));

        spells.add(spell.GUID());
        this.school = school.GUID();

    }

}
