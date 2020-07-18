package com.robertx22.mine_and_slash.database.data.affixes;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.requirements.Requirements;
import com.robertx22.mine_and_slash.database.data.requirements.bases.BaseRequirement;

import java.util.*;
import java.util.function.Function;

public class GenericAffixBuilder<T> {

    List<T> elements = new ArrayList<>();

    int weight = 1000;
    Requirements requirements;
    List<AffixTag> tags = new ArrayList<>();
    Affix.Type type;

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

    public GenericAffixBuilder<T> Req(BaseRequirement... reqs) {
        requirements = new Requirements(reqs);
        return this;
    }

    public GenericAffixBuilder<T> Weight(int weight) {
        weight = weight;
        return this;
    }

    public GenericAffixBuilder<T> Tags(AffixTag... tags) {
        this.tags = Arrays.asList(tags);
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

    public GenericAffixBuilder<T> Implicit() {
        type = Affix.Type.implicit;
        return this;
    }

    public void Build() {

        for (T element : elements) {

            Affix affix = new Affix();
            affix.guid = guid.apply(element);

            for (Map.Entry<Integer, Function<T, List<StatModifier>>> entry : this.modsPerTier.entrySet()) {

                int tier = entry.getKey();
                int tierweight = (tier + 1) * 100; // simple for now
                AffixTier affixTier = new AffixTier(modsPerTier.get(tier)
                    .apply(element), tierweight, tier);
                affix.tierMap.put(tier, affixTier);
            }

            affix.type = type;
            affix.weight = weight;
            affix.langName = nameMap.get(element);
            affix.tags = tags;
            affix.requirements = requirements;

            affix.addToSerializables();
        }

    }

}
