package com.robertx22.age_of_exile.database.data.spell_school;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpellSchool implements JsonExileRegistry<SpellSchool>, IAutoGson<SpellSchool>, IAutoLocName {
    public static SpellSchool SERIALIZER = new SpellSchool();

    public String id = "";
    public transient String locname = "";

    public static int MAX_Y_ROWS = 6;
    public static int MAX_X_ROWS = 10;

    public HashMap<String, PointData> spells = new HashMap<>();

    public List<Integer> lvl_reqs = Arrays.asList(1, 5, 10, 20, 30, 40, 50);

    // public HashMap<String, PointData> spells = new HashMap<>(); todo synergies

    public int getLevelNeededToAllocate(PointData point) {

        int req = lvl_reqs.get(point.y);

        return req;
    }

    public boolean isLevelEnoughForSpell(LivingEntity en, Spell spell) {
        return Load.Unit(en)
            .getLevel() >= getLevelNeededToAllocate(spells.get(spell.GUID()));
    }

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
