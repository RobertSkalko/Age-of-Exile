package com.robertx22.age_of_exile.database.data.affixes;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.base.IhasRequirements;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.requirements.Requirements;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Affix implements IWeighted, IGUID, IAutoLocName, IhasRequirements, IRarity,
    ISerializedRegistryEntry<Affix>, IAutoGson<Affix> {

    public enum Type {
        prefix,
        suffix,
        dungeon_prefix,
        dungeon_suffix,
        implicit;

        public boolean isPrefix() {
            return this == prefix;
        }

        public boolean isSuffix() {
            return this == suffix;
        }

        public Type getOpposite() {
            if (this.isPrefix()) {
                return suffix;
            }
            if (this.isSuffix()) {
                return prefix;
            }
            return null;
        }
    }

    public String guid;
    public String loc_name;
    public boolean only_one_per_item = true;
    public int weight = 1000;
    public Requirements requirements;
    public List<String> tags = new ArrayList<>();
    public Type type;

    public HashMap<Integer, AffixTier> tier_map = new HashMap<>();

    @Override
    public boolean isRegistryEntryValid() {
        if (guid == null || loc_name == null || tier_map.isEmpty() || requirements == null || type == null || weight < 0) {
            return false;
        }

        return true;
    }

    @Override
    public final String datapackFolder() {
        return type.name() + "/";
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.AFFIX;
    }

    public String GUID() {
        return guid;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".affix." + formattedGUID();
    }

    @Override
    public final AutoLocGroup locNameGroup() {
        return AutoLocGroup.Affixes;
    }

    @Override
    public final Requirements requirements() {
        return requirements;
    }

    @Override
    public int Weight() {
        return weight;
    }

    public List<StatModifier> getTierStats(int tier) {

        if (tier_map.containsKey(tier)) {
            return tier_map.get(tier).mods;
        }

        System.out.println("Tier number not found, returning default. Affix: " + GUID() + " tier: " + tier);

        return tier_map.values()
            .stream()
            .findFirst()
            .get().mods;

    }

    @Override
    public String locNameForLangFile() {
        return this.loc_name;
    }

    @Override
    public String getRarityRank() {
        return IRarity.MAGICAL_ID;
    }

    @Override
    public Class<Affix> getClassForSerialization() {
        return Affix.class;
    }
}
