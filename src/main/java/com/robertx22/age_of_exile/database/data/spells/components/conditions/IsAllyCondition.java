package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;

public class IsAllyCondition extends EffectCondition implements ICTextTooltip {

    public IsAllyCondition() {
        super(Arrays.asList());
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, EntitySavedSpellData savedData) {
        MutableText text = new LiteralText("");
        text.append(" if target is ally ");
        return text;
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        if (ctx.target == null || ctx.caster == null) {
            return false;
        }
        return AllyOrEnemy.allies.is(ctx.target, ctx.caster);
    }

    public MapHolder create(Double chance) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.CHANCE, chance);
        return d;
    }

    @Override
    public String GUID() {
        return "is_target_ally";
    }
}

