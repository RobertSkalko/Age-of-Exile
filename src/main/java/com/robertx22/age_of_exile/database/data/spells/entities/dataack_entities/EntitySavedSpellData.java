package com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class EntitySavedSpellData {

    String spell_id;

    AttachedSpell attached;

    MapHolder map;

    public BaseSpell getSpell() {
        return SlashRegistry.Spells()
            .get(spell_id);
    }

}
