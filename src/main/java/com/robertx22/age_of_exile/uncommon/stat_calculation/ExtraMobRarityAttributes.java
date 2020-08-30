package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.UUID;

public class ExtraMobRarityAttributes {

    static UUID MOVESPEED_ID = UUID.fromString("120E0DFB-87AE-4633-9556-521010E291A0");
    static UUID KNOCKBACK_RES_ID = UUID.fromString("220E0DFB-87AE-4633-9556-521010E291A0");

    static EntityAttributeModifier getMoveSpeedMod(float multi) {
        return new EntityAttributeModifier(
            MOVESPEED_ID,
            "Rarity move speed boost",
            multi * 0.02F,
            EntityAttributeModifier.Operation.ADDITION);
    }

    static EntityAttributeModifier getKnockbackResMod(float multi) {
        return new EntityAttributeModifier(
            KNOCKBACK_RES_ID,
            "Rarity knockback res",
            multi * 0.15F,
            EntityAttributeModifier.Operation.ADDITION);
    }

    public static void add(LivingEntity en, EntityCap.UnitData data) {

        MobRarity rar = Rarities.Mobs.get(data.getRarity());

        if (en.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
            .getModifier(MOVESPEED_ID) == null) {
            en.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                .addPersistentModifier(getMoveSpeedMod(rar.StatMultiplier()));
        }
        if (en.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
            .getModifier(KNOCKBACK_RES_ID) == null) {
            en.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                .addPersistentModifier(getKnockbackResMod(rar.StatMultiplier()));

        }

    }
}
