package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ExtraMobRarityAttributes {

    static UUID MOVESPEED_ID = UUID.fromString("120E0DFB-87AE-4633-9556-521010E291A0");
    static UUID KNOCKBACK_RES_ID = UUID.fromString("220E0DFB-87AE-4633-9556-521010E291A0");

    static AttributeModifier getMoveSpeedMod(float multi) {
        return new AttributeModifier(
            MOVESPEED_ID,
            "Rarity move speed boost",
            multi * 0.02F,
            AttributeModifier.Operation.ADDITION);
    }

    static AttributeModifier getKnockbackResMod(float multi) {
        return new AttributeModifier(
            KNOCKBACK_RES_ID,
            "Rarity knockback res",
            multi * 0.15F,
            AttributeModifier.Operation.ADDITION);
    }

    public static void add(LivingEntity en, EntityData data) {

        MobRarity rar = ExileDB.MobRarities()
            .get(data.getRarity());

        if (en.getAttribute(Attributes.MOVEMENT_SPEED)
            .getModifier(MOVESPEED_ID) == null) {
            en.getAttribute(Attributes.MOVEMENT_SPEED)
                .addPermanentModifier(getMoveSpeedMod(rar.StatMultiplier()));
        }
        if (en.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
            .getModifier(KNOCKBACK_RES_ID) == null) {
            en.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                .addPermanentModifier(getKnockbackResMod(rar.StatMultiplier()));

        }

    }
}
