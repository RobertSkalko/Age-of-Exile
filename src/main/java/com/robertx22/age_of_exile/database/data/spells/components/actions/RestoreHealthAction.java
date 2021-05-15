package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class RestoreHealthAction extends SpellAction implements ICTextTooltip {

    public RestoreHealthAction() {
        super(Arrays.asList(VALUE_CALCULATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, EntitySavedSpellData savedData) {
        MutableText text = new LiteralText("");

        ValueCalculation calc = data.get(VALUE_CALCULATION);

        text.append("Restore ")
            .append(calc.getShortTooltip(savedData.lvl))
            .append(" Health");

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {
            if (!ctx.world.isClient) {
                ValueCalculation calc = data.get(VALUE_CALCULATION);

                int value = calc.getCalculatedValue(ctx.caster, ctx.calculatedSpellData.lvl);

                int total = 0;

                for (LivingEntity t : targets) {

                    RestoreResourceEvent restore = EventBuilder.ofRestore(ctx.caster, t, ResourceType.health, RestoreType.heal, value)
                        .setSpell(ctx.calculatedSpellData.getSpell())
                        .build();

                    restore.Activate();

                    total += restore.data.getNumber();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MapHolder create(ValueCalculation calc) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(VALUE_CALCULATION, calc);
        return dmg;
    }

    @Override
    public String GUID() {
        return "restore_health";
    }
}
