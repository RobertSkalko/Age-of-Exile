package com.robertx22.age_of_exile.database.data.spells.entities;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EffectCloudEntity extends AreaEffectCloudEntity {

    public EffectCloudEntity(EntityType type, World world) {
        super(type, world);

    }

    // todo might not need this

    public void setColor(TextFormatting color) {
        this.setFixedColor(color.getColor());
    }
}
