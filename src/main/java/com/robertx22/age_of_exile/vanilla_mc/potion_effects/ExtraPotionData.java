package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.UUID;

@Storable
public class ExtraPotionData {

    @Store // some potions will only do a thing x amount of times
    public int uses = 1;

    @Store
    public int timesUsed = 1;

    @Store
    private int initialDurationTicks = -1;

    @Store
    public String casterID = "";

    @Store
    private int stacks = 1;

    public int getStacks() {
        return stacks;
    }

    public LivingEntity getCaster(World world) {
        if (casterID.isEmpty()) {
            return null;
        }
        return Utilities.getLivingEntityByUUID(world, UUID.fromString(casterID));

    }

}
