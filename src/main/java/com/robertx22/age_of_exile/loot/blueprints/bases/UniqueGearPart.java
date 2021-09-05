package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UniqueGearPart extends BlueprintPart<UniqueGear, GearBlueprint> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {
        UniqueGear uniq;

        if (blueprint.info != null) {
            if (blueprint.info.isMapWorld && !RandomUtils.roll(ModConfig.get().Server.DUNGEON_RANDOM_ITEM_CHANCE)) {

                GearRarity rar = RandomUtils.weightedRandom(blueprint.info.dungeon.u.getUniques()
                    .stream()
                    .map(x -> x.getUniqueRarity())
                    .collect(Collectors.toSet()));

                List<UniqueGear> possible = blueprint.info.dungeon.u.getUniques()
                    .stream()
                    .filter(x -> x.getUniqueRarity()
                        .equals(rar))
                    .collect(Collectors.toList());

                uniq = RandomUtils.weightedRandom(possible);
            } else {
                FilterListWrap<UniqueGear> gen = ExileDB.UniqueGears()
                    .getWrapped()
                    .of(x -> blueprint.info.diff != null && blueprint.info.diff.rank >= x.getUniqueRarity().drops_after_tier)
                    .of(x -> !x.filters.cantDrop(blueprint.info))
                    .of(x -> x.getBaseGear()
                        .equals(blueprint.gearItemSlot.get()));

                uniq = gen.random();
            }
        } else {
            return ExileDB.UniqueGears()
                .getWrapped()
                .of(x -> x.getBaseGear().gear_slot
                    .equals(blueprint.gearItemSlot.get().gear_slot))
                .random();
        }
        if (uniq != null) {
            blueprint.gearItemSlot.override(uniq.getBaseGear());
        }
        return uniq;
    }

}
