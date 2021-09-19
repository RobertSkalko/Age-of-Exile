package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundNBT;

public class UnitNbt {
    private static final String LOC = "unit_object";

    public static Unit Load(CompoundNBT nbt) {

        if (nbt == null) {
            return null;
        }

        return LoadSave.Load(Unit.class, new Unit(), nbt, LOC);

    }

    public static void Save(CompoundNBT nbt, Unit gear) {

        if (nbt == null) {
            return;
        }

        if (gear != null) {
            LoadSave.Save(gear, nbt, LOC);
        }

    }
}
