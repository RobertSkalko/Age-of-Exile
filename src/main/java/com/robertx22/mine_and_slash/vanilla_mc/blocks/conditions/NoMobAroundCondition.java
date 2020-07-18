package com.robertx22.mine_and_slash.vanilla_mc.blocks.conditions;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class NoMobAroundCondition extends LootCrateCondition {

    int radius = 1;

    public NoMobAroundCondition(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean canOpenCrate(PlayerEntity player, BlockEntity box) {
        return EntityFinder.start(player, LivingEntity.class, new Vec3d(box.getPos()))
                .radius(radius)
                .addPredicate(x -> EntityTypeUtils.isMob(x))
                .build()
                .size() < 1;
    }

    @Override
    public Text tellCondition() {
        return new LiteralText("You cannot open this crate while mobs are around.");
    }
}
