package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.util.Formatting;

public interface Rarity extends IWeighted, IAutoLocName, ISerializedRegistryEntry<Rarity> {

    String GUID();

    int Rank();

    default String Color() {
        return textFormatting().toString();
    }

    int Weight();

    Formatting textFormatting();

    @Override
    public default AutoLocGroup locNameGroup() {
        return AutoLocGroup.Rarities;
    }

}
