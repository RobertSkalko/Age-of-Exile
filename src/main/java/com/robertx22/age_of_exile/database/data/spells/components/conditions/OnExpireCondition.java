package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;

public class OnExpireCondition extends EffectCondition implements ICTextTooltip {

    public OnExpireCondition() {
        super(Arrays.asList());
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, EntitySavedSpellData savedData) {
        MutableText text = new LiteralText("On Expire ");
        return text;

    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        return ctx.activation == EntityActivation.ON_EXPIRE;
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "on_entity_expire";
    }
}

