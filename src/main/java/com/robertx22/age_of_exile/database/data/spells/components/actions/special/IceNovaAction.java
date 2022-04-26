package com.robertx22.age_of_exile.database.data.spells.components.actions.special;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mixin_ducks.FallingBlockAccessor;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.utilityclasses.BoxUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Arrays;
import java.util.Collection;

public class IceNovaAction extends SpellAction {
    public IceNovaAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        AxisAlignedBB box = BoxUtils.createBoxOfRadius(ctx.sourceEntity.blockPosition()
                .above(), 1)
            .inflate(5, 0, 5);

        BoxUtils.iterateBoxAsCircle(box, x -> {

            SoundUtils.playSound(ctx.sourceEntity, SoundRefs.ICE_BREAK);

            FallingBlockEntity fb = new FallingBlockEntity(ctx.world, x.getX(), x.getY(), x.getZ(), Blocks.ICE.defaultBlockState());

            FallingBlockAccessor acc = (FallingBlockAccessor) fb;
            acc.setDestroyedOnLanding(true);
            fb.time++;

            ctx.world.addFreshEntity(fb);

        });

    }

    @Override
    public String GUID() {
        return "ice_nova";
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

}
