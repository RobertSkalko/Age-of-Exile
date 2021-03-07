package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public class CasterSelector extends BaseTargetSelector implements ICTextTooltip {

    public CasterSelector() {
        super(Arrays.asList());
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, CalculatedSpellData spelldata) {
        MutableText text = new LiteralText(" to caster");
        return text;
    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        return Arrays.asList(caster);
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "self";
    }
}
