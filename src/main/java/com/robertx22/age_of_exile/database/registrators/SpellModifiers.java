package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class SpellModifiers implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SlashRegistry.Spells()
            .getSerializable()
            .forEach(x -> {
                for (SpellModEnum value : SpellModEnum.values()) {
                    SpellModifier.addToSeriazables(x.GUID(), value);
                }
            });

    }
}
