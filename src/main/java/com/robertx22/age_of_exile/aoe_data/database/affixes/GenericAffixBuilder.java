package com.robertx22.age_of_exile.aoe_data.database.affixes;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.affixes.AffixTag;
import com.robertx22.age_of_exile.database.data.affixes.AffixTier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.requirements.Requirements;
import com.robertx22.age_of_exile.database.data.requirements.TagRequirement;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericAffixBuilder<T> {

    List<T> elements = new ArrayList<>();

    int weight = 1000;
    List<String> tags = new ArrayList<>();
    Affix.Type type;

    Requirements requirements = new Requirements();

    TagRequirement tagRequirement = new TagRequirement();

    Function<T, String> guid;
    HashMap<Integer, Function<T, List<StatModifier>>> modsPerTier = new HashMap<>();

    HashMap<T, String> nameMap = new HashMap<>();

    public GenericAffixBuilder<T> guid(Function<T, String> guid) {
        this.guid = guid;
        return this;
    }

    public GenericAffixBuilder<T> add(T element, String name) {
        this.nameMap.put(element, name);
        this.elements.add(element);
        return this;
    }

    public GenericAffixBuilder<T> tier(int tier, Function<T, List<StatModifier>> mods) {
        this.modsPerTier.put(tier, mods);
        return this;
    }

    public GenericAffixBuilder<T> includesTags(BaseGearType.SlotTag... tags) {
        this.tagRequirement.included.addAll(Arrays.stream(tags)
            .map(x -> x.name())
            .collect(Collectors.toList()));
        return this;
    }

    public GenericAffixBuilder<T> excludesTags(BaseGearType.SlotTag... tags) {
        this.tagRequirement.excluded.addAll(Arrays.stream(tags)
            .map(x -> x.name())
            .collect(Collectors.toList()));
        return this;
    }

    public GenericAffixBuilder<T> Weight(int weight) {
        this.weight = weight;
        return this;
    }

    public GenericAffixBuilder<T> Tags(AffixTag... tags) {
        this.tags = Arrays.asList(tags)
            .stream()
            .map(x -> x.name())
            .collect(Collectors.toList());
        return this;
    }

    public GenericAffixBuilder<T> Prefix() {
        type = Affix.Type.prefix;
        return this;
    }

    public GenericAffixBuilder<T> Suffix() {
        type = Affix.Type.suffix;
        return this;
    }

    public GenericAffixBuilder<T> DungeonPrefix() {
        type = Affix.Type.dungeon_prefix;
        return this;
    }

    public GenericAffixBuilder<T> DungeonSuffix() {
        type = Affix.Type.dungeon_suffix;
        return this;
    }

    public GenericAffixBuilder<T> Implicit() {
        type = Affix.Type.implicit;
        return this;
    }

    public void Build() {

        for (T element : elements) {

            Affix affix = new Affix();
            affix.guid = guid.apply(element);
            affix.requirements = new Requirements(this.tagRequirement);

            for (Map.Entry<Integer, Function<T, List<StatModifier>>> entry : this.modsPerTier.entrySet()) {

                int tier = entry.getKey();
                int tierweight = (tier + 1) * 100; // simple for now
                AffixTier affixTier = new AffixTier(modsPerTier.get(tier)
                    .apply(element), tierweight, tier);
                affix.tier_map.put(tier, affixTier);
            }

            affix.type = type;
            affix.weight = weight;
            affix.loc_name = nameMap.get(element);
            affix.tags = tags;

            affix.addToSerializables();
        }

    }

}
