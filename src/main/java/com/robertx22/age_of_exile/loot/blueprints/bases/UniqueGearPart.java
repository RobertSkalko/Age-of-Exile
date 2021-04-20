package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.Comparator;

public class UniqueGearPart extends BlueprintPart<UniqueGear, GearBlueprint> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {
        UniqueGear uniq;
        if (blueprint.info.isMapWorld) {
            uniq = RandomUtils.weightedRandom(blueprint.info.dungeon.uniques.getUniques());
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
            BaseGearType type = uniq.getPossibleGearTypes()
                .stream()
                .filter(x -> x.getLevelRange()
                    .isLevelInRange(blueprint.info.level))
                .max(Comparator.comparingInt(x -> x.getLevelRange()
                    .getMaxLevel()))
                .get();
            blueprint.gearItemSlot.set(type);
        }
        return uniq;
    }

}
