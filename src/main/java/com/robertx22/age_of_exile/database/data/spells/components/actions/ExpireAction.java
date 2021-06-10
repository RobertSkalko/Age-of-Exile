package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class ExpireAction extends SpellAction {

    public ExpireAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (ctx.sourceEntity != null) {

            if (ctx.sourceEntity instanceof LivingEntity == false) {

                if (ctx.sourceEntity instanceof StationaryFallingBlockEntity) {
                    StationaryFallingBlockEntity s = (StationaryFallingBlockEntity) ctx.sourceEntity;
                    s.scheduleRemoval();
                } else {

                    //ctx.sourceEntity.remove();
                }
            }
        }
    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "expire";
    }

}

