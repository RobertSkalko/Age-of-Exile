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
    public static SpellModifier HOLY_FLOWER_CLEANSE = createSpecial("holy_flower_cleanse", DatapackSpells.HOLY_FLOWER_ID, "Holy Cleansing");
    public static SpellModifier THROW_FLAMES_BURN = createSpecial("throw_flames_burn", DatapackSpells.THROW_FLAMES_ID, "Burning Flames");
    public static SpellModifier ICE_FLOWER_MANA_RESTORE = createSpecial("flower_of_ice_mana_restore", DatapackSpells.THROW_FLAMES_ID, "Soothing Chill");
    public static SpellModifier HEART_OF_ICE_MAGIC_SHIELD = createSpecial("heart_of_ice_magic_shield", DatapackSpells.THROW_FLAMES_ID, "Inner Cool");
    public static SpellModifier MAGMA_FLOWER_HEAL = createSpecial("magma_flower_heal", DatapackSpells.MAGMA_FLOWER_ID, "Life Stealing Flames");

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
