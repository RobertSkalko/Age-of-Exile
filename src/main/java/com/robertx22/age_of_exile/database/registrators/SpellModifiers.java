package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.spells.components.DatapackSpells;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

import static com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier.addToSeriazables;
import static com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier.createSpecial;

public class SpellModifiers implements ISlashRegistryInit {

    public static SpellModifier THORN_BUSH_EFFECT = createSpecial("thorn_bush_effect", DatapackSpells.THORN_BUSH_ID, "Thorny Bush");

    @Override
    public void registerAll() {

        THORN_BUSH_EFFECT.addToSerializables();

        SlashRegistry.Spells()
            .getSerializable()
            .forEach(x -> {
                for (SpellModEnum value : SpellModEnum.values()) {
                    addToSeriazables(x.GUID(), value);
                }
            });

    }
}
