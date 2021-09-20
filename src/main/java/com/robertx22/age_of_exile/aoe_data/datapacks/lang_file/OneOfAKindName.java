package com.robertx22.age_of_exile.aoe_data.datapacks.lang_file;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

public class OneOfAKindName implements IAutoLocName {

    String id;
    String name;

    public OneOfAKindName(Spell spell) {
        this.id = spell.GUID() + "_mod";
        this.name = spell.locNameForLangFile() + " Modifier";
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.OneOfAKind;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".one_of_a_kind." + id;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return id;
    }
}
