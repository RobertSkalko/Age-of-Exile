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
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class SummonAtTargetFeet extends SpellAction implements ICMainTooltip {

    public SummonAtTargetFeet() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_ENTITY, MapField.LIFESPAN_TICKS, MapField.HEIGHT));
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder holder, EntitySavedSpellData savedData) {

        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<MutableText> list = new ArrayList<>();

        list.add(new LiteralText("Summon at target's feet."));

        list.addAll(spell.getTooltipForEntity(info, spell, holder.get(MapField.ENTITY_NAME), savedData));

        return list;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Optional<EntityType<?>> projectile = EntityType.get(data.get(MapField.PROJECTILE_ENTITY));

        Double height = data.getOrDefault(MapField.HEIGHT, 0D);

        targets.forEach(x -> {
            Vec3d pos = x.getPos();
            Entity en = projectile.get()
                .create(ctx.world);
            SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
            en.updatePosition(pos.x, pos.y + height, pos.z);
            ctx.caster.world.spawnEntity(en);
        });

    }

    public MapHolder create(Item item, EntityType type, Double lifespan) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, 1D);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, 0D);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getId(item)
            .toString());
        c.put(MapField.GRAVITY, false);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(type)
            .toString());
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "summon_at_target_feet";
    }

}
