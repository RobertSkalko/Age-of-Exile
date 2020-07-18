package com.robertx22.mine_and_slash.database.data.affixes;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.requirements.Requirements;
import com.robertx22.mine_and_slash.database.data.requirements.bases.BaseRequirement;

import java.util.*;

public class AffixBuilder {

    String guid;
    HashMap<Integer, List<StatModifier>> modsPerTier = new HashMap<>();
    String langName;

    int weight = 1000;
    Requirements requirements;
    public List<AffixTag> tags = new ArrayList<>();
    public Affix.Type type;

    private AffixBuilder(String id) {
        this.guid = id;
    }

    public static AffixBuilder Normal(String id) {
        return new AffixBuilder(id);
    }

    public AffixBuilder Named(String name) {
        langName = name;
        return this;
    }

    public AffixBuilder Req(BaseRequirement... reqs) {
        requirements = new Requirements(reqs);
        return this;
    }

    public AffixBuilder Weight(int weight) {
        weight = weight;
        return this;
    }

    public AffixBuilder Tags(AffixTag... tags) {
        this.tags = Arrays.asList(tags);
        return this;
    }

    public AffixBuilder tier(int tier, StatModifier... stats) {

        if (modsPerTier.containsKey(tier)) {
            try {
                throw new Exception(this.guid + " already has tier " + tier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.modsPerTier.put(tier, Arrays.asList(stats));
        return this;
    }

    public AffixBuilder Prefix() {
        type = Affix.Type.prefix;
        return this;
    }

    public AffixBuilder Suffix() {
        type = Affix.Type.suffix;
        return this;
    }

    public AffixBuilder Implicit() {
        type = Affix.Type.implicit;
        return this;
    }

    public void Build() {

        Affix affix = new Affix();
        affix.guid = guid;

        for (Map.Entry<Integer, List<StatModifier>> entry : this.modsPerTier.entrySet()) {

            int tier = entry.getKey();
            int tierweight = (tier + 1) * 100; // simple for now
            AffixTier affixTier = new AffixTier(modsPerTier.get(tier), tierweight, tier);
            affix.tierMap.put(tier, affixTier);
        }

        affix.type = type;
        affix.weight = weight;
        affix.langName = langName;
        affix.tags = tags;
        affix.requirements = requirements;

        affix.addToSerializables();

    }

}
