package com.robertx22.age_of_exile.database.data.reforge;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;

public class Reforge implements IAutoGson<Reforge>, JsonExileRegistry<Reforge>, IAutoLocName {
    public static Reforge SERIALIZER = new Reforge();

    public int weight = 1000;
    public String id = "";
    public String rarity = "";
    public List<StatModifier> stats = new ArrayList<>();

    public transient String locname = "";

    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(rarity);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.REFORGE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Class<Reforge> getClassForSerialization() {
        return Reforge.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Reforges;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".reforge." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public static class Builder {

        Reforge object = new Reforge();

        public static Builder of(String id, String locname) {

            Builder b = new Builder();
            b.object.id = id;
            b.object.locname = locname;
            return b;
        }

        public Builder stat(StatModifier stat) {
            this.object.stats.add(stat);
            return this;
        }

        public Builder rarity(String rar) {
            this.object.rarity = rar;
            return this;
        }

        public void build() {
            this.object.addToSerializables();
        }

    }
}
