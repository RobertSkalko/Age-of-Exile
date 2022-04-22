package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.Collection;

public class EffectCloudAction extends SpellAction {

    public EffectCloudAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClientSide) {
            AreaEffectCloudEntity en = EntityType.AREA_EFFECT_CLOUD.create(ctx.world);
            en.setPos(ctx.pos.getX(), ctx.pos.getY(), ctx.pos.getZ());
            en.setDuration(data.get(MapField.POTION_DURATION)
                .intValue());
            en.setFixedColor(data.get(MapField.COLOR)
                .intValue());
            ctx.world.addFreshEntity(en);

        }
    }

    public MapHolder create(TextFormatting color, int seconds) {
        MapHolder c = new MapHolder();
        c.put(MapField.POTION_DURATION, seconds * 20D);
        c.put(MapField.COLOR, (double) color.getColor());
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "effect_cloud";
    }
}
