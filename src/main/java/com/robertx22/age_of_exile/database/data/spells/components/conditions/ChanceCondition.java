package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;

public class ChanceCondition extends EffectCondition implements ICTextTooltip {

    public ChanceCondition() {
        super(Arrays.asList(MapField.CHANCE));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        Double chance = data.get(MapField.CHANCE);

        text.append(chance + "% chance: ");

        return text;

    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        Double chance = data.get(MapField.CHANCE);
        return RandomUtils.roll(chance, ctx.world.random);
    }

    public MapHolder create(Double chance) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.CHANCE, chance);
        return d;
    }

    @Override
    public String GUID() {
        return "chance";
    }
}
