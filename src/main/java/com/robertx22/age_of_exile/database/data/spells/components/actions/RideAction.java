package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RideAction extends SpellAction implements ICMainTooltip {

    public RideAction() {
        super(Arrays.asList());
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData) {
        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<MutableText> list = new ArrayList<>();
        return list;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        targets.forEach(x -> {
            if (!x.hasVehicle()) {
                x.startRiding(ctx.sourceEntity);
            }
        });

    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "ride";
    }

}

