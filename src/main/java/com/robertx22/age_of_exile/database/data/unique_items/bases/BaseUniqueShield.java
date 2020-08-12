package com.robertx22.age_of_exile.database.data.unique_items.bases;

import net.minecraft.item.ShieldItem;

public final class BaseUniqueShield extends ShieldItem {

    public BaseUniqueShield() {
        super(new Settings().maxCount(1)
            .maxDamageIfAbsent(1000));

    }

}

