package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.List;

public class GearRarityPart extends BlueprintPart<GearRarity, GearBlueprint> {

    RarityRegistryContainer<GearRarity> container;

    GearRarity specialRar = null;

    public List<GearRarity> possible = ExileDB.GearRarityGroups()
        .get(GearRarityGroups.DROPPABLE_RARITIES_ID)
        .getRarities();

    public float chanceForHigherRarity = 0;

    public GearRarityPart(GearBlueprint blueprint) {
        super(blueprint);
        this.container = blueprint.getRarityContainer();
    }

    public void setupChances(LootInfo info) {

        this.possible.removeIf(x -> !info.favorRank.drop_unique_gears && x.is_unique_item);

        if (info.playerData != null) {
            if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
                chanceForHigherRarity += info.playerData.getUnit()
                    .getCalculatedStat(MagicFind.getInstance())
                    .getValue();
            }
        }

        if (info.favorRank != null) {
            possible.removeIf(x -> info.favorRank.excludedRarities.stream()
                .anyMatch(e -> e.equals(x.GUID())));
        }

    }

    @Override
    protected GearRarity generateIfNull() {

        if (this.specialRar != null) {
            return specialRar;
        }

        GearRarity rar = RandomUtils.weightedRandom(possible);

        if (rar.hasHigherRarity()) {
            if (RandomUtils.roll(chanceForHigherRarity)) {
                if (rar.hasHigherRarity() && possible.contains(rar.getHigherRarity())) {
                    rar = rar.getHigherRarity();
                }
            }
        }

        return rar;
    }

}


