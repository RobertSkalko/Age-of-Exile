package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class SummonBlockAction extends SpellAction {

    public SummonBlockAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.BLOCK));
    }

    static int SEARCH = 10;

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClient) {

            //HitResult ray = ctx.caster.rayTrace(5D, 0.0F, false);
            BlockPos pos = ctx.pos;

            boolean found = true;

            if (data.getOrDefault(MapField.FIND_NEAREST_SURFACE, true)) {

                found = false;

                int times = 0;

                while (!found && pos.getY() > 1 && SEARCH > times) {
                    times++;
                    if (ctx.world.isAir(pos) && !ctx.world.isAir(pos.down())) {
                        found = true;
                    } else {
                        pos = pos.down();
                    }
                }
                if (!found) {
                    pos = ctx.pos;
                    times = 0;
                    while (!found && pos.getY() < ctx.world.getHeight() && SEARCH > times) {
                        times++;
                        if (ctx.world.isAir(pos) && !ctx.world.isAir(pos.down())) {
                            found = true;
                        } else {
                            pos = pos.up();
                        }
                    }
                }
            }
            Block block = data.getBlock();
            Objects.requireNonNull(block);

            if (found) {
                StationaryFallingBlockEntity be = new StationaryFallingBlockEntity(ctx.world, pos, block.getDefaultState());
                be.getDataTracker()
                    .set(StationaryFallingBlockEntity.IS_FALLING, data.getOrDefault(MapField.IS_BLOCK_FALLING, false));
                SpellUtils.initSpellEntity(be, ctx.caster, ctx.calculatedSpellData, data);
                ctx.world.spawnEntity(be);
            }

        }
    }

    public MapHolder create(Block block, Double lifespan) {
        MapHolder c = new MapHolder();
        c.put(MapField.BLOCK, Registry.BLOCK.getId(block)
            .toString());
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "summon_block";
    }
}

