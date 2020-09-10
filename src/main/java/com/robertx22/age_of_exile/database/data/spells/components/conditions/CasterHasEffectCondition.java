package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;

public class CasterHasEffectCondition extends EffectCondition implements ICTextTooltip {

    public CasterHasEffectCondition() {
        super(Arrays.asList(MapField.POTION_ID));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        StatusEffect potion = data.getPotion();

        text.append("If caster has ")
            .append(potion.getName())
            .append(" ");

        return text;

    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        StatusEffect potion = data.getPotion();
        return ctx.caster.hasStatusEffect(potion);
    }

    public MapHolder create(StatusEffect effect) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.POTION_ID, Registry.STATUS_EFFECT.getId(effect)
            .toString());
        return d;
    }

    @Override
    public String GUID() {
        return "caster_has_potion";
    }
}
