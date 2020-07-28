package com.robertx22.mine_and_slash.auto_comp;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.testing.Watch;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class DeterminePowerLevels {

    public static HashMap<BaseGearType, List<PowerLevel>> MAP = new HashMap<>();
    public static HashMap<BaseGearType, PowerLevel> STRONGEST = new HashMap<>();

    public static void setupHashMaps() {

        Watch watch = new Watch();

        Set<BaseGearType> types = new HashSet<>(SlashRegistry.GearTypes()
            .getList());

        types.forEach(x -> {
            MAP.put(x, new ArrayList<>());
        });

        Registry.ITEM
            .stream()
            .filter(x -> Registry.ITEM.getId(x) != null && !Ref.MODID.equals(Registry.ITEM.getId(x)
                .getNamespace()))
            .forEach(item -> {
                try {

                    types
                        .forEach(slot -> {
                            if (BaseGearType.isGearOfThisType(slot, item)) {

                                PowerLevel current = new PowerLevel(item, slot);

                                MAP.get(slot)
                                    .add(current);

                                PowerLevel strongest = STRONGEST.getOrDefault(slot, new PowerLevel(item, slot));

                                if (current.isStrongerThan(strongest)) {
                                    strongest = current;
                                }

                                STRONGEST.put(slot, strongest);

                            }
                        });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        watch.print("[Setting up auto compatibility config power levels] ");

    }
}
