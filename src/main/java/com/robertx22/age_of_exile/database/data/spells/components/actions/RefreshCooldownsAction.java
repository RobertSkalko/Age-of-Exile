package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class RefreshCooldownsAction extends SpellAction {

    public RefreshCooldownsAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        int ticks = data.get(MapField.COOLDOWN_TICKS)
            .intValue();

        targets.forEach(x -> {
            Load.Unit(x)
                .getCooldowns()
                .tickSpellCooldowns(ticks);

        });

    }

    public MapHolder create(Double ticks) {
        MapHolder c = new MapHolder();
        c.type = GUID();
        c.put(MapField.COOLDOWN_TICKS, ticks);
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "refresh_cds";
    }

}
