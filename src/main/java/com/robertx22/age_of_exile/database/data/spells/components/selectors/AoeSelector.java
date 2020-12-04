package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class AoeSelector extends BaseTargetSelector implements ICTextTooltip {

    public AoeSelector() {
        super(Arrays.asList(RADIUS, SELECTION_TYPE, ENTITY_PREDICATE));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        EntityFinder.EntityPredicate en = data.getEntityPredicate();
        EntityFinder.SelectionType select = data.getSelectionType();

        MutableText who = null;

        if (en == EntityFinder.EntityPredicate.ALL) {
            who = new LiteralText("everyone");
        } else if (en == EntityFinder.EntityPredicate.ENEMIES) {
            who = new LiteralText("enemies");
        } else if (en == EntityFinder.EntityPredicate.ALLIES) {
            who = new LiteralText("allies");
        }

        MutableText how = null;

        if (select == EntityFinder.SelectionType.IN_FRONT) {
            how = new LiteralText("in front");
        } else if (select == EntityFinder.SelectionType.RADIUS) {
            how = new LiteralText("in radius");
        }

        text.append(" to ")
            .append(who)
            .append(" ")
            .append(how);

        return text;

    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        EntityFinder.EntityPredicate predicate = data.getEntityPredicate();
        Double radius = data.get(RADIUS);

        radius *= ctx.calculatedSpellData.config.getMulti(SpellModEnum.AREA);

        Double chance = data.getOrDefault(SELECTION_CHANCE, 100D);

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
            .finder(EntityFinder.SelectionType.RADIUS)
            .searchFor(predicate)
            .height(data.getOrDefault(HEIGHT, radius))
            .radius(radius);

        return finder.build()
            .stream()
            .filter(x -> RandomUtils.roll(chance))
            .collect(Collectors.toList());
    }

    public MapHolder create(Double radius, EntityFinder.SelectionType type, EntityFinder.EntityPredicate pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(RADIUS, radius);
        d.put(SELECTION_TYPE, type.name());
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "aoe";
    }
}
