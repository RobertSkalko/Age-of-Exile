package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;

public class UniqueGearPart extends BlueprintPart<UniqueGear, GearBlueprint> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {

        try {

            if (!blueprint.gearItemSlot.isGenerated()) {
                UniqueGear gear = ExileDB.UniqueGears()
                    .getFilterWrapped(x -> x.uniqueRarity.equals(blueprint.rarity.get()
                        .GUID()))
                    .random();
                blueprint.gearItemSlot.set(gear.getBaseGear());
                return gear;

            } else {
                return ExileDB.UniqueGears()
                    .getWrapped()
                    .of(x -> x.uniqueRarity.equals(blueprint.rarity.get()
                        .GUID()))
                    .of(x -> x.getBaseGear().gear_slot
                        .equals(blueprint.gearItemSlot.get().gear_slot))
                    .random();
            }

        } catch (Exception e) {
            e.printStackTrace();

            UniqueGear uniq = ExileDB.UniqueGears()
                .random();
            if (uniq != null) {
                blueprint.gearItemSlot.override(uniq.getBaseGear());
            }
            return uniq;
        }
    }

}
