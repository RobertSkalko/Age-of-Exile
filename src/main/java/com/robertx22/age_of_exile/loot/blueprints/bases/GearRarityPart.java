package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.List;

public class GearRarityPart extends BlueprintPart<GearRarity, GearBlueprint> {

    RarityRegistryContainer<GearRarity> container;

    GearRarity specialRar = null;

    public List<GearRarity> possible = Database.GearRarityGroups()
        .get(GearRarityGroups.NON_UNIQUE_ID)
        .getRarities();

    public float chanceForHigherRarity = 0;

    public GearRarityPart(GearBlueprint blueprint) {
        super(blueprint);
        this.container = blueprint.getRarityContainer();
    }

    public void setupChances(LootInfo info) {

        if (blueprint.gearItemSlot.isGenerated()) {
            this.possible = blueprint.gearItemSlot.get()
                .getRarityGroup()
                .getRarities();
        }

        List<GearRarity> specialRarities = Database.GearRarities()
            .getFiltered(x -> x.special_spawn_chance > 0);

        if (info.isMapWorld) { // TODO

            for (GearRarity rar : specialRarities) {

                float chance = rar.special_spawn_chance;

                if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
                    chance += rar.special_spawn_chest_bonus_chance;
                }
                if (info.world != null) {
                    chance *= Database.getDimensionConfig(info.world).unique_gear_drop_multi;
                }

                if (info.playerData != null) {
                    if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
                        chance *= info.playerData.getUnit()
                            .getCalculatedStat(TreasureQuality.getInstance())
                            .getMultiplier();
                    }
                }

                if (info.favorRank != null) {
                    if (!info.favorRank.drop_unique_gears) {
                        chance = 0;
                    }
                }

                if (RandomUtils.roll(chance)) {
                    this.specialRar = rar;
                    return;
                }

            }
        }

        this.chanceForHigherRarity = Database.Tiers()
            .get(this.blueprint.tier.get() + "").higher_rar_chance;

        if (info.playerData != null) {
            if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
                chanceForHigherRarity += info.playerData.getUnit()
                    .getCalculatedStat(TreasureQuality.getInstance())
                    .getAverageValue();
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


