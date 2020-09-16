package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

import static com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier.addToSeriazables;
import static com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier.createSpecial;
import static com.robertx22.age_of_exile.database.data.spells.components.DatapackSpells.*;

public class SpellModifiers implements ISlashRegistryInit {

    public static SpellModifier THORN_BUSH_EFFECT = createSpecial("thorn_bush_effect", THORN_BUSH_ID, "Thorny Bush");
    public static SpellModifier HOLY_FLOWER_CLEANSE = createSpecial("holy_flower_cleanse", HOLY_FLOWER_ID, "Holy Cleansing");
    public static SpellModifier THROW_FLAMES_BURN = createSpecial("throw_flames_burn", THROW_FLAMES_ID, "Burning Flames");
    public static SpellModifier ICE_FLOWER_MANA_RESTORE = createSpecial("flower_of_ice_mana_restore", FLOWER_OF_ICE_ID, "Soothing Chill");
    public static SpellModifier HEART_OF_ICE_MAGIC_SHIELD = createSpecial("heart_of_ice_magic_shield", HEART_OF_ICE_ID, "Inner Cool");
    public static SpellModifier MAGMA_FLOWER_HEAL = createSpecial("magma_flower_heal", MAGMA_FLOWER_ID, "Life Stealing Flames");

    @Override
    public void registerAll() {

        THORN_BUSH_EFFECT.addToSerializables();
        HOLY_FLOWER_CLEANSE.addToSerializables();
        THROW_FLAMES_BURN.addToSerializables();
        ICE_FLOWER_MANA_RESTORE.addToSerializables();
        HEART_OF_ICE_MAGIC_SHIELD.addToSerializables();
        MAGMA_FLOWER_HEAL.addToSerializables();

        SlashRegistry.Spells()
            .getSerializable()
            .forEach(x -> {
                if (!x.isPassive()) {
                    for (SpellModEnum value : SpellModEnum.values()) {
                        addToSeriazables(x.GUID(), value);
                    }
                }
            });

    }
}
