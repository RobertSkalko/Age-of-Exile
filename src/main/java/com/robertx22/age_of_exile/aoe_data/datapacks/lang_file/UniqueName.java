package com.robertx22.age_of_exile.aoe_data.datapacks.lang_file;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

public class UniqueName implements IAutoLocName {

    String name;
    String id;
    String itemid;

    public UniqueName(UniqueGear uniq) {
        this.name = uniq.langName;
        this.id = uniq.GUID();
        this.itemid = uniq.itemID.toString();
    }

    @Override
    public AutoLocGroup locNameGroup() {

        return AutoLocGroup.Unique_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return "item." + itemid;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return "unique_item_name." + id;
    }
}
