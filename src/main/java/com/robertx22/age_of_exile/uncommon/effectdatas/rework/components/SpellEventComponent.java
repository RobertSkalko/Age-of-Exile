package com.robertx22.age_of_exile.uncommon.effectdatas.rework.components;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;

public class SpellEventComponent extends EventComponent {

    public Spell spell;

    public SpellEventComponent(Spell spell) {
        this.spell = spell;
    }

    @Override
    public String GUID() {
        return EventComponents.SPELL_ID;
    }
}
