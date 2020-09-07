package com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import net.minecraft.entity.LivingEntity;

import java.util.Objects;

public class EntitySavedSpellData {

    String caster_uuid;

    String spell_id;

    AttachedSpell attached;

    public static EntitySavedSpellData create(LivingEntity caster, Spell spell, AttachedSpell att) {
        Objects.requireNonNull(caster);

        EntitySavedSpellData data = new EntitySavedSpellData();
        data.spell_id = spell.GUID();
        data.attached = att;

        data.caster_uuid = caster.getUuid()
            .toString();

        return data;
    }

    public BaseSpell getSpell() {
        return SlashRegistry.Spells()
            .get(spell_id);
    }

}
