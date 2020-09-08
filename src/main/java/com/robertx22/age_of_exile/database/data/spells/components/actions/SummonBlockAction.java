package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class SummonBlockAction extends SpellAction {

    public SummonBlockAction() {
        super(Arrays.asList(MapField.BLOCK));
    }

    static int SEARCH = 10;

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClient) {

            HitResult ray = ctx.caster.rayTrace(5D, 0.0F, false);
            BlockPos pos = new BlockPos(ray.getPos());

            boolean found = false;

            int times = 0;

            while (!found && pos.getY() > 1 && SEARCH > times) {
                times++;
                if (ctx.world.isAir(pos) && !ctx.world.isAir(pos.down())) {
                    found = true;
                } else {
                    pos = pos.down();
                }
            }
            Block block = data.getBlock();
            Objects.requireNonNull(block);

            if (found) {
                StationaryFallingBlockEntity be = new StationaryFallingBlockEntity(ctx.world, pos, block.getDefaultState());
                SpellUtils.initSpellEntity(be, ctx.caster, ctx.calculatedSpellData, data);
                ctx.world.spawnEntity(be);
            }

        }
    }

    public MapHolder create(Block block) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        return dmg;
    }

    @Override
    public String GUID() {
        return "summon_block";
    }
}

