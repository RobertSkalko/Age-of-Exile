package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RarityPart extends BlueprintPart<Rarity> {

    RarityRegistryContainer container;

    public int minRarity = -1;
    public int maxRarity = 5;
    public float chanceForHigherRarity = 0;

    public void setupChances(LootInfo info) {
        this.chanceForHigherRarity = SlashRegistry.Tiers()
            .get(this.blueprint.tier.get() + "").chance_for_higher_drop_rarity;

        if (info.playerData != null) {
            chanceForHigherRarity += info.playerData.getUnit()
                .peekAtStat(MagicFind.getInstance())
                .getAverageValue();
        }
    }

    public RarityPart(ItemBlueprint blueprint) {
        super(blueprint);
        this.container = blueprint.getRarityContainer();
    }

    @Override
    protected Rarity generateIfNull() {

        List<Rarity> possible = (List<Rarity>) container.getAllRarities()
            .stream()
            .filter(x -> {
                int r = ((Rarity) x).Rank();
                return r >= minRarity && r <= maxRarity;
            })
            .collect(Collectors.toList());

        Rarity rar = RandomUtils.weightedRandom(possible);

        if (rar.Rank() < container.maxNonUnique()
            .Rank() && RandomUtils.roll(chanceForHigherRarity)) {
            rar = container.getHigherRarity(rar);
        }

        return rar;
    }

    public void setSpecificRarity(int rank) {
        this.set(container.get(rank));
    }

}


