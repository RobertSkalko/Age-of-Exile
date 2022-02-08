package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class EffectCtx extends AutoHashClass implements IGUID {

    public EffectType type;
    public String resourcePath;
    public String id;
    public Elements element;
    public String locname;

    public ResourceLocation getEffectLocation() {
        return new ResourceLocation(SlashRef.MODID, resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourcePath, id);
    }

    public EffectCtx(String id, String locname, int num, Elements element, EffectType type) {
        this.id = id;
        this.resourcePath = type.name() + "/" + num;
        this.element = element;
        this.locname = locname;
        this.type = type;
    }

    @Override
    public String GUID() {
        return resourcePath;
    }
}
