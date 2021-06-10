package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UniqueGearPart extends BlueprintPart<UniqueGear, GearBlueprint> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {
        UniqueGear uniq;
        if (blueprint.info.isMapWorld) {

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
            FilterListWrap<UniqueGear> gen = Database.UniqueGears()
                .getWrapped()
                .of(x -> x.getUniqueRarity().drops_after_tier < 0)
                .of(x -> !x.filters.cantDrop(blueprint.info))
                .of(x -> x.getPossibleGearTypes()
                    .contains(blueprint.gearItemSlot.get()));

            uniq = gen.random();
        }
        if (uniq != null) {
            blueprint.gearItemSlot.override(uniq.getGearTypeForLevel(blueprint.info.level));
        }
        return uniq;
    }

}
