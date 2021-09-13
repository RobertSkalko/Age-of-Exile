package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.spell_schools.SpellSchoolsAdder;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.PlusSchoolLevels;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class PlusSkillsInSchoolStats implements ExileRegistryInit {

    public static PlusSchoolLevels PLUS_OCEAN = new PlusSchoolLevels(of(SpellSchoolsAdder.WATER));
    public static PlusSchoolLevels PLUS_FIRE = new PlusSchoolLevels(of(SpellSchoolsAdder.FIRE));
    public static PlusSchoolLevels PLUS_NATURE = new PlusSchoolLevels(of(SpellSchoolsAdder.NATURE));
    public static PlusSchoolLevels PLUS_DIVINE = new PlusSchoolLevels(of(SpellSchoolsAdder.DIVINE));
    public static PlusSchoolLevels PLUS_HUNTING = new PlusSchoolLevels(of(SpellSchoolsAdder.HUNTING));

    static SpellSchool of(String id) {
        return ExileDB.SpellSchools()
            .get(id);
    }

    @Override
    public void registerAll() {

        PLUS_OCEAN.addToSerializables();
        PLUS_NATURE.addToSerializables();
        PLUS_FIRE.addToSerializables();
        PLUS_DIVINE.addToSerializables();
        PLUS_HUNTING.addToSerializables();

    }
}
