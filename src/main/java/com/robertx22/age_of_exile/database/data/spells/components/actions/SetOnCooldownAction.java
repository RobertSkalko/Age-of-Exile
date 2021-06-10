package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class SetOnCooldownAction extends SpellAction {

    public SetOnCooldownAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        String id = data.get(MapField.COOLDOWN_ID);
        int ticks = data.get(MapField.COOLDOWN_TICKS)
            .intValue();

        targets.forEach(x -> {
            Load.Unit(x)
                .getCooldowns()
                .setOnCooldown(id, ticks);
        });

    }

    public MapHolder create(String id, Double ticks) {
        MapHolder c = new MapHolder();
        c.type = GUID();
        c.put(MapField.COOLDOWN_TICKS, ticks);
        c.put(MapField.COOLDOWN_ID, id);
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "set_on_cd";
    }

}

