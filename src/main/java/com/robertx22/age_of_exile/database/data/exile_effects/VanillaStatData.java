package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class VanillaStatData {

    float val;
    String uuid;
    String id;
    ModType type;

    public static VanillaStatData create(Attribute attri, float val, ModType type, UUID uuid) {
        VanillaStatData data = new VanillaStatData();
        data.id = Registry.ATTRIBUTE.getKey(attri)
            .toString();
        data.uuid = uuid.toString();
        data.type = type;
        data.val = val;
        return data;
    }

    public Attribute getAttribute() {
        return Registry.ATTRIBUTE.get(new ResourceLocation(id));
    }

    public void applyVanillaStats(LivingEntity en, int stacks) {

        AttributeModifier mod = new AttributeModifier(UUID.fromString(uuid), "", val * stacks, type.operation);
        Attribute attri = getAttribute();

        this.removeVanillaStats(en);

        if (en.getAttribute(attri) != null) {
            if (!en.getAttribute(attri)
                .hasModifier(mod)) {
                en.getAttribute(attri)
                    .addTransientModifier(mod);
            }
        }

    }

    public void removeVanillaStats(LivingEntity en) {
        AttributeModifier mod = new AttributeModifier(UUID.fromString(uuid), "", val, type.operation);
        Attribute attri = getAttribute();

        if (en.getAttribute(attri) != null) {
            if (en.getAttribute(attri)
                .hasModifier(mod)) {
                en.getAttribute(attri)
                    .removeModifier(mod);
            }
        }
    }
}
