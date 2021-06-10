package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class SummonAtSightAction extends SpellAction implements ICMainTooltip {

    public SummonAtSightAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_ENTITY, MapField.LIFESPAN_TICKS, MapField.HEIGHT));
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData) {

        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<MutableText> list = new ArrayList<>();

        list.add(new LiteralText("Summon in front of caster"));

        list.addAll(spell.getTooltipForEntity(info, spell, holder.get(MapField.ENTITY_NAME), savedData));

        return list;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Optional<EntityType<?>> projectile = EntityType.get(data.get(MapField.PROJECTILE_ENTITY));

        Double distance = data.getOrDefault(MapField.DISTANCE, 10D);
        Double height = data.getOrDefault(MapField.HEIGHT, 10D);

        HitResult ray = ctx.caster.raycast(distance, 0.0F, false);
        Vec3d pos = ray.getPos();

        Entity en = projectile.get()
            .create(ctx.world);
        SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
        en.updatePosition(pos.x, pos.y + height, pos.z);

        ctx.caster.world.spawnEntity(en);

    }

    public MapHolder create(EntityType type, Double lifespan, Double height) {
        MapHolder c = new MapHolder();
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.GRAVITY, false);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.HEIGHT, height);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(type)
            .toString());
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "summon_at_sight";
    }

}
