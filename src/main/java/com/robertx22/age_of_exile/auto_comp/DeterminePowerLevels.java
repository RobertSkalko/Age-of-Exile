package com.robertx22.age_of_exile.auto_comp;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DeterminePowerLevels {

    public static HashMap<GearSlot, PowerLevel> STRONGEST = new HashMap<>();

    public static void setupHashMaps() {

        Watch watch = new Watch();

        Set<BaseGearType> types = new HashSet<>(SlashRegistry.GearTypes()
            .getList());

        Registry.ITEM
            .stream()
            .filter(x -> !Ref.MODID.equals(Registry.ITEM.getId(x)
                .getNamespace()))
            .forEach(item -> {
                try {

                    types
                        .forEach(slot -> {
                            if (BaseGearType.isGearOfThisType(slot, item)) {

                                PowerLevel current = new PowerLevel(item, slot);

                                PowerLevel strongest = STRONGEST.getOrDefault(slot, current);

                                if (current.isStrongerThan(strongest)) {
                                    strongest = current;
                                }

                                STRONGEST.put(slot.getGearSlot(), strongest);

                            }
                        });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        watch.print("[Setting up auto compatibility config power levels] ");

    }
}
