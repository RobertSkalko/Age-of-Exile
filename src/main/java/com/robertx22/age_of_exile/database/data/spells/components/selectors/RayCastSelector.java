package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class RayCastSelector extends BaseTargetSelector implements ICTextTooltip {

    public RayCastSelector() {
        super(Arrays.asList(DISTANCE, WIDTH, ENTITY_PREDICATE));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, CalculatedSpellData spelldata) {
        MutableText text = new LiteralText("");

        EntityFinder.EntityPredicate en = data.getEntityPredicate();

        MutableText who = null;

        if (en == EntityFinder.EntityPredicate.ALL) {
            who = new LiteralText("everyone");
        } else if (en == EntityFinder.EntityPredicate.ENEMIES) {
            who = new LiteralText("enemies");
        } else if (en == EntityFinder.EntityPredicate.ALLIES) {
            who = new LiteralText("allies");
        }

        MutableText how = null;

        how = new LiteralText("in view");

        text.append(" to ")
            .append(who)
            .append(" ")
            .append(how);

        return text;

    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        EntityFinder.EntityPredicate predicate = data.getEntityPredicate();
        float distance = data.get(DISTANCE)
            .floatValue();

        List<LivingEntity> list = LookUtils.getLivingEntityLookedAt(caster, distance, false);
        list = predicate.getMatchingEntities(list, new EntityFinder.Setup(caster, LivingEntity.class, caster.getPos()));
        return list;

    }

    public MapHolder create(Double distance, EntityFinder.EntityPredicate pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(DISTANCE, distance);
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "in_view";
    }
}
