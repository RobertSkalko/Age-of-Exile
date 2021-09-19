package com.robertx22.age_of_exile.database.data.synergy;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.value_calc.LevelProvider;
import com.robertx22.age_of_exile.database.data.value_calc.LeveledValue;
import com.robertx22.age_of_exile.database.data.value_calc.MaxLevelProvider;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Synergy implements JsonExileRegistry<Synergy>, IAutoGson<Synergy>, IAutoLocName, MaxLevelProvider {

    public static Synergy SERIALIZER = new Synergy();

    public String id = "";
    public String spell_id = "";
    public transient String locname = "";
    public int maxlvl = 8;

    public List<StatModifier> stats = new ArrayList<>();

    public List<ExactStatData> getStats(LevelProvider provider) {
        return stats.stream()
            .map(x -> {
                LeveledValue val = new LeveledValue(x.min, x.max);
                return ExactStatData.of(val.getValue(provider), x.GetStat(), x.getModType(), provider.getCasterLevel());
            })
            .collect(Collectors.toList());
    }

    public final ResourceLocation getIconLoc() {
        return new ResourceLocation(Ref.MODID, "textures/gui/spells/synergies/" + id + ".png");
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".synergy." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SYNERGY;
    }

    @Override
    public Class<Synergy> getClassForSerialization() {
        return Synergy.class;
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
    public int getMaxLevel() {
        return maxlvl;
    }

    @Override
    public int getMaxLevelWithBonuses() {
        return getMaxLevel() + 4;
    }
}
