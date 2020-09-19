package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class VanillaStatData {

    float val;
    String uuid;
    String id;
    ModType type;

    public static VanillaStatData create(EntityAttribute attri, float val, ModType type, UUID uuid) {
        VanillaStatData data = new VanillaStatData();
        data.id = Registry.ATTRIBUTE.getId(attri)
            .toString();
        data.uuid = uuid.toString();
        data.type = type;
        data.val = val;
        return data;
    }

    public EntityAttribute getAttribute() {
        return Registry.ATTRIBUTE.get(new Identifier(id));
    }

    public void apply(LivingEntity en) {

        EntityAttributeModifier mod = new EntityAttributeModifier(UUID.fromString(uuid), "", val, type.operation);
        EntityAttribute attri = getAttribute();

        if (!en.getAttributeInstance(attri)
            .hasModifier(mod)) {
            en.getAttributeInstance(attri)
                .addTemporaryModifier(mod);
        }

    }

    public void remove(LivingEntity en) {
        EntityAttributeModifier mod = new EntityAttributeModifier(UUID.fromString(uuid), "", val, type.operation);
        EntityAttribute attri = getAttribute();

        if (en.getAttributeInstance(attri)
            .hasModifier(mod)) {
            en.getAttributeInstance(attri)
                .removeModifier(mod);
        }
    }

}
