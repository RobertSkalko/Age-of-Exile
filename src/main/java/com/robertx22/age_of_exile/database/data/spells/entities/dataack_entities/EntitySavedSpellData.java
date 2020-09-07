package com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class EntitySavedSpellData {

    String spell_id;

    AttachedSpell attached;

    public static EntitySavedSpellData create(Spell spell, AttachedSpell att) {
        EntitySavedSpellData data = new EntitySavedSpellData();
        data.spell_id = spell.GUID();
        data.attached = att;
        return data;
    }

    public BaseSpell getSpell() {
        return SlashRegistry.Spells()
            .get(spell_id);
    }

}
