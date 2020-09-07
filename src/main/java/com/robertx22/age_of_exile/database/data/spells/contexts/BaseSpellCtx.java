package com.robertx22.age_of_exile.database.data.spells.contexts;

import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;

public class BaseSpellCtx {

    public CalculatedSpellData calculatedSpellData;

    public BaseSpellCtx(CalculatedSpellData calculatedSpellData) {
        this.calculatedSpellData = calculatedSpellData;
    }
}
