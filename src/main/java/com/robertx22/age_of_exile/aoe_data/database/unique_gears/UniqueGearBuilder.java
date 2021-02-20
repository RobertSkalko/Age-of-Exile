package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilterData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UniqueGearBuilder {

    UniqueGear uniq = new UniqueGear();

    public static UniqueGearBuilder of(Item item, String id, String locname, String desc, List<DataGenKey<BaseGearType>> gearType) {

        UniqueGearBuilder b = new UniqueGearBuilder();
        b.uniq.langName = locname;
        b.uniq.langDesc = desc;
        b.uniq.guid = id;

        for (DataGenKey<BaseGearType> type : gearType) {
            b.uniq.serBaseGearType = Database.GearTypes()
                .getFromSerializables(type);
            b.uniq.gearType = type.GUID();
            b.uniq.gear_types.add(type.GUID());
        }

        b.uniq.itemID = Registry.ITEM.getId(item);

        return b;

    }

    public static UniqueGearBuilder of(Item item, String id, String locname, String desc, DataGenKey<BaseGearType>... gearType) {
        return of(item, id, locname, desc, Arrays.asList(gearType));
    }

    public static UniqueGearBuilder of(Item item, String id, String locname, String desc, Collection<DataGenKey<BaseGearType>> gearType) {
        List<DataGenKey<BaseGearType>> list = new ArrayList<>();
        list.addAll(gearType);
        return of(item, id, locname, desc, list);
    }

    public UniqueGearBuilder dropFilter(DropFilterData filter) {
        this.uniq.filters.list.add(filter);
        return this;
    }

    public UniqueGearBuilder stats(List<StatModifier> stats) {
        this.uniq.uniqueStats = stats;
        return this;
    }

    public UniqueGearBuilder devComment(String comment) {
        // OMAE WA MOU SHINDEIRU
        return this;
    }

    public UniqueGearBuilder setMI() {
        this.uniq.uniqueRarity = IRarity.MONSTER_UNIQUE_ID;
        return this;
    }

    public UniqueGear build() {
        assert !uniq.uniqueStats.isEmpty();

        uniq.addToSerializables();
        return uniq;
    }
}