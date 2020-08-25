package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.UUID;

public class ExtraMobRarityAttributes {

    static UUID MOVESPEED_ID = UUID.fromString("120E0DFB-87AE-4633-9556-521010E291A0");
    static UUID KNOCKBACK_RES_ID = UUID.fromString("220E0DFB-87AE-4633-9556-521010E291A0");

    static EntityAttributeModifier getMoveSpeedMod(int rarity) {
        return new EntityAttributeModifier(
            MOVESPEED_ID,
            "Rarity move speed boost",
            rarity * 0.03F,
            EntityAttributeModifier.Operation.ADDITION);
    }

    static EntityAttributeModifier getKnockbackResMod(int rarity) {
        return new EntityAttributeModifier(
            KNOCKBACK_RES_ID,
            "Rarity knockback res",
            rarity * 0.2F,
            EntityAttributeModifier.Operation.ADDITION);
    }

    public static void add(LivingEntity en, EntityCap.UnitData data) {
        if (en.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
            .getModifier(MOVESPEED_ID) == null) {
            en.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                .addPersistentModifier(getMoveSpeedMod(data.getRarity()));
        }
        if (en.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
            .getModifier(KNOCKBACK_RES_ID) == null) {
            en.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                .addPersistentModifier(getKnockbackResMod(data.getRarity()));

        }

    }
}
