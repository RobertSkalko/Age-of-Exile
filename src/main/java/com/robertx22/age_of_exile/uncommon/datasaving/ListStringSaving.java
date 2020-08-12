package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ListStringData;
import net.minecraft.nbt.CompoundTag;

public class ListStringSaving {
    private static final String LOC = Ref.MODID + ":list_string";

    public static ListStringData Load(CompoundTag nbt) {
        return LoadSave.Load(ListStringData.class, new ListStringData(), nbt, LOC);

    }

    public static void Save(CompoundTag nbt, ListStringData data) {

        if (nbt == null) {
            return;
        }

        if (data != null) {
            LoadSave.Save(data, nbt, LOC);
        }

    }

}
