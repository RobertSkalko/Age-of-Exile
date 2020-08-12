package com.robertx22.age_of_exile.vanilla_mc.blocks.conditions;

import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class NoMobAroundCondition extends LootCrateCondition {

    int radius = 1;

    public NoMobAroundCondition(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean canOpenCrate(PlayerEntity player, BlockEntity box) {
        return EntityFinder.start(player, LivingEntity.class, box.getPos())
            .radius(radius)
            .addPredicate(x -> EntityTypeUtils.isMob(x))
            .build()
            .size() < 1;
    }

    @Override
    public MutableText tellCondition() {
        return new LiteralText("You cannot open this crate while mobs are around.");
    }
}
