package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.GenerateThreatEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.ThreatGenType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Arrays;
import java.util.Collection;

public class AggroAction extends SpellAction {

    public enum Type {
        AGGRO, DE_AGGRO
    }

    public AggroAction() {
        super(Arrays.asList(MapField.AGGRO_TYPE));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (ctx.caster instanceof PlayerEntity) {
            ValueCalculation calc = data.get(MapField.VALUE_CALCULATION);
            int num = calc.getCalculatedValue(ctx.caster, ctx.calculatedSpellData.lvl);

            Type aggro = data.getAggro();

            targets.forEach(x -> {

                if (x instanceof MobEntity) {
                    MobEntity mob = (MobEntity) x;

                    if (aggro == Type.AGGRO) {
                        GenerateThreatEvent event = new GenerateThreatEvent((PlayerEntity) ctx.caster, mob, ThreatGenType.spell, num);
                        event.Activate();

                    } else {
                        Load.Unit(mob)
                            .getThreat()
                            .addThreat((PlayerEntity) ctx.caster, mob, -num);
                    }
                }
            });
        }

    }

    public MapHolder create(ValueCalculation calc, Type type) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.AGGRO_TYPE, type.name());
        d.put(MapField.VALUE_CALCULATION, calc);
        return d;
    }

    @Override
    public String GUID() {
        return "aggro";
    }
}

