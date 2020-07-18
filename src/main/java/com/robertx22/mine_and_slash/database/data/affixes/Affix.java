package com.robertx22.mine_and_slash.database.data.affixes;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.base.IhasRequirements;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.requirements.Requirements;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Affix implements IWeighted, IGUID, IAutoLocName, IhasRequirements, IRarity,
    ISerializedRegistryEntry<Affix>, ISerializable<Affix> {

    public enum Type {
        prefix,
        suffix,
        implicit
    }

    String guid;
    String langName;
    int weight = 1000;
    Requirements requirements;
    public List<AffixTag> tags = new ArrayList<>();
    public Type type;

    public HashMap<Integer, AffixTier> tierMap = new HashMap<>();

    @Override
    public boolean isRegistryEntryValid() {
        if (guid == null || langName == null || tierMap.isEmpty() || requirements == null || type == null || weight < 0) {
            return false;
        }

        return true;
    }

    @Override
    public final String datapackFolder() {
        return type.name() + "/";
    }

    @Override
    public boolean isFromDatapack() {
        return true;
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

        if (tierMap.containsKey(tier)) {
            return tierMap.get(tier).mods;
        }

        System.out.println("Tier number not found, returning default. Affix: " + GUID() + " tier: " + tier);

        return tierMap.values()
            .stream()
            .findFirst()
            .get().mods;

    }

    @Override
    public String locNameForLangFile() {
        return this.langName;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(0);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        json.addProperty("type", type.name());
        json.add("requirements", requirements().toJson());

        JsonObject tiers = new JsonObject();

        for (Map.Entry<Integer, AffixTier> entry : tierMap.entrySet()) {
            tiers.add(entry.getKey() + "", entry.getValue()
                .toJson());
        }
        json.add("tiers", tiers);

        return json;
    }

    @Override
    public Affix fromJson(JsonObject json) {

        try {
            String guid = getGUIDFromJson(json);
            String langName = getLangNameStringFromJson(json);
            int weight = getWeightFromJson(json);

            Type type = Type.valueOf(json.get("type")
                .getAsString());

            Requirements req = Requirements.EMPTY.fromJson(json.getAsJsonObject("requirements"));

            Affix affix = new Affix();
            affix.weight = weight;
            affix.requirements = req;
            affix.guid = guid;
            affix.langName = langName;
            affix.type = type;

            JsonObject tiers = json.getAsJsonObject("tiers");

            for (int i = 0; i < 10; i++) {
                if (tiers.has(i + "")) {
                    AffixTier tier = AffixTier.EMPTY.fromJson(tiers.getAsJsonObject(i + ""));
                    affix.tierMap.put(i, tier);
                }
            }

            return affix;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
