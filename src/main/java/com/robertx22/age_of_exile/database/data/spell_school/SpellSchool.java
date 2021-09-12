package com.robertx22.age_of_exile.database.data.spell_school;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class SpellSchool implements JsonExileRegistry<SpellSchool>, IAutoGson<SpellSchool>, IAutoLocName {
    public static SpellSchool SERIALIZER = new SpellSchool();

    public String id = "";
    public transient String locname = "";

    public static int MAX_Y_ROWS = 6;
    public static int MAX_X_ROWS = 10;

    public HashMap<String, PointData> spells = new HashMap<>();

    // public HashMap<String, PointData> spells = new HashMap<>(); todo synergies

    public Identifier getIconLoc() {
        return Ref.guiId("spells/schools/" + id);
    }

    public Identifier getBackgroundLoc() {
        return Ref.guiId("spells/school_backgrounds/" + id);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SPELL_SCHOOL;
    }

    @Override
    public Class<SpellSchool> getClassForSerialization() {
        return SpellSchool.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return locname;
    }

    @Override
    public String locNameForLangFile() {
        return Ref.MODID + ".spell_school." + id;
    }
}
